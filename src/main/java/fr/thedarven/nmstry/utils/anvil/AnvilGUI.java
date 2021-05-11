package fr.thedarven.nmstry.utils.anvil;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class AnvilGUI {

    private static final int NB_ITEMS = 3;
    public static final int SLOT_INPUT_LEFT = 0;
    public static final int SLOT_INPUT_RIGHT = 1;
    public static final int SLOT_OUTPUT = 2;

    private static AnvilGUIListener listener;

    private String itemName;
    private Player player;
    private Plugin plugin;
    private AnvilClickHandler anvilClickHandler;
    private Material material;

    private ItemStack[] items;
    private Inventory inventory;

    public AnvilGUI(Plugin plugin, Player player, Material material, AnvilClickHandler anvilClickHandler) {
        this(plugin, player, anvilClickHandler);
        this.material = material;
    }

    public AnvilGUI(Plugin plugin, Player player, AnvilClickHandler anvilClickHandler) {
        this.player = player;
        this.plugin = plugin;
        this.material = Material.PAPER;
        this.anvilClickHandler = anvilClickHandler;
        this.items = new ItemStack[NB_ITEMS];
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = Objects.isNull(itemName) ? "" : itemName;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public AnvilClickHandler getAnvilClickHandler() {
        return anvilClickHandler;
    }

    public void addToListener() {
        if (Objects.isNull(listener)) {
            listener = new AnvilGUIListener(this);
            Bukkit.getPluginManager().registerEvents(listener, this.plugin);
        } else {
            listener.addMenu(this);
        }
    }

    public void removeToListener() {
        listener.removeMenu(this);
    }


    public AnvilGUI setInputName(String name) {
        setItem(SLOT_INPUT_LEFT, new ItemStack(this.material), name);
        return this;
    }

    public AnvilGUI open() {
        if (Objects.isNull(this.items[SLOT_INPUT_LEFT])) {
            setInputName("-");
        }
        addToListener();
        this.inventory = AnvilNMS.open(this);
        return this;
    }

    public void close() {
        if (Objects.nonNull(this.player)) {
            this.player.closeInventory();
        }
    }

    public void onClose() {
        if (Objects.nonNull(this.inventory)) {
            this.inventory.clear();
        }
        destroy();
    }

    public void destroy() {
        removeToListener();
        this.itemName = null;
        this.player = null;
        this.anvilClickHandler = null;
        this.items = null;
        this.inventory = null;
        this.plugin = null;
        this.material = null;
    }

    public void setItem(int slot, ItemStack item, String itemName) {
        if (slot < 0 || slot >= NB_ITEMS) {
            return;
        }

        if (Objects.nonNull(itemName)) {
            ItemMeta itemMeta = item.getItemMeta();
            if (Objects.nonNull(itemMeta)) {
                itemMeta.setDisplayName(itemName);
                item.setItemMeta(itemMeta);
            }
        }

        this.items[slot] = item;
        if (slot != SLOT_OUTPUT) {
            this.items[SLOT_OUTPUT] = item;
        }

        if (Objects.nonNull(this.inventory)) {
            this.inventory.setItem(slot, item);

            if (slot != SLOT_OUTPUT){
                this.inventory.setItem(SLOT_OUTPUT, item);
            }
        }
    }
}
