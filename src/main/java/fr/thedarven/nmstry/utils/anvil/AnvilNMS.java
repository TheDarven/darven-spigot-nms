package fr.thedarven.nmstry.utils.anvil;

import fr.thedarven.nmstry.utils.Reflection;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenWindow;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class AnvilNMS {

    private static final ChatMessage PACKET_MESSAGE = new ChatMessage(Blocks.ANVIL.a() + ".name");

    public static Inventory open(AnvilGUI menu) {
        EntityPlayer entityPlayer = Reflection.getEntityPlayer(menu.getPlayer());

        AnvilCustom customAnvil = new AnvilCustom(entityPlayer, menu);
        Inventory inventory = customAnvil.getBukkitView().getTopInventory();

        ItemStack[] items = menu.getItems();
        int nbItems = items.length;
        for (int slot = 0; slot < nbItems; slot++) {
            if (Objects.nonNull(items[slot])) {
                inventory.setItem(slot, items[slot]);
            }
        }

        customAnvil.setItemName(menu.getItemName());

        int windowId = entityPlayer.nextContainerCounter();

        Reflection.sendPackets(menu.getPlayer(), new PacketPlayOutOpenWindow(windowId, "minecraft:anvil", PACKET_MESSAGE, 0));
        // Delete ?
        entityPlayer.activeContainer = customAnvil;
        entityPlayer.activeContainer.windowId = windowId;
        entityPlayer.activeContainer.addSlotListener(entityPlayer);

        return inventory;
    }

}
