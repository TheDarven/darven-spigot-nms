package fr.thedarven.nmstry.utils.f3;

import fr.thedarven.nmstry.utils.Reflection;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityStatus;
import org.bukkit.entity.Player;

/**
 * Allows to hide and show the coordinates on the F3 panel of a player
 *
 * @author TheDarven
 */
public class DisableF3 {

    private static final byte DISABLE_BYTE = 22;
    private static final byte ENABLE_BYTE = 23;

    /**
     * Hides the coordinates on player's F3 panel
     *
     * @param player
     */
    public static void disableF3(Player player) {
        PacketPlayOutEntityStatus statusPacket = new PacketPlayOutEntityStatus(Reflection.getEntityPlayer(player), DISABLE_BYTE);
        Reflection.sendPackets(player, statusPacket);
    }

    /**
     * Shows the coordinates on player's F3 panel
     *
     * @param player
     */
    public static void enableF3(Player player) {
        PacketPlayOutEntityStatus statusPacket = new PacketPlayOutEntityStatus(Reflection.getEntityPlayer(player), ENABLE_BYTE);
        Reflection.sendPackets(player, statusPacket);
    }

}
