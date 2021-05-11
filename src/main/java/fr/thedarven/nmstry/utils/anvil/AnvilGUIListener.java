package fr.thedarven.nmstry.utils.anvil;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class AnvilGUIListener implements Listener {

    private final Map<Player, AnvilGUI> menus;

    public AnvilGUIListener(AnvilGUI menu) {
        this.menus = new HashMap<>();
        this.menus.put(menu.getPlayer(), menu);
    }

    public void removeMenu(AnvilGUI menu) {
        this.menus.remove(menu.getPlayer());
    }

    public void addMenu(AnvilGUI menu) {
        this.menus.put(menu.getPlayer(), menu);
    }

    public Optional<AnvilGUI> getByInventory(Inventory inventory) {
        return this.menus.values()
                .stream()
                .filter(anvilGUI -> Objects.equals(anvilGUI.getInventory(), inventory))
                .findFirst();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Optional<AnvilGUI> oMenu = getByInventory(event.getInventory());
        if (!oMenu.isPresent()) {
            return;
        }

        AnvilGUI menu = oMenu.get();
        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        int slot = event.getRawSlot();

        if (slot != AnvilGUI.SLOT_OUTPUT || Objects.isNull(item) || item.getType() == Material.AIR) {
            return;
        }

        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();

            if (meta.hasDisplayName()) {
                menu.setItemName(ChatColor.stripColor(meta.getDisplayName().trim()));
            }
        }

        if (Objects.nonNull(menu.getAnvilClickHandler())){
            if (menu.getAnvilClickHandler().onClick(menu, menu.getItemName())){
                player.closeInventory();
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        AnvilGUI menu = this.menus.get(event.getPlayer());
        if (Objects.isNull(menu)) {
            return;
        }

        menu.onClose();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        AnvilGUI menu = this.menus.get(event.getPlayer());
        if (Objects.isNull(menu)) {
            return;
        }

        menu.onClose();
    }

    @EventHandler
    public void onServerStop(PluginDisableEvent onDisable) {
        List<AnvilGUI> copyMenus = new ArrayList<>(this.menus.values());
        copyMenus.forEach(AnvilGUI::close);
    }

}
