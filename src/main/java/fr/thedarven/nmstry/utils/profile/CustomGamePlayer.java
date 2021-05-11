package fr.thedarven.nmstry.utils.profile;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import fr.thedarven.nmstry.utils.Reflection;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author TheDarven
 */
public class CustomGamePlayer {

    private final UUID uuid;
    private String defaultValue;
    private String defaultSignature;

    public CustomGamePlayer(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Retrieves the current Player
     *
     * @return
     */
    private Optional<Player> getCurrentPlayer() {
        return Optional.ofNullable(Bukkit.getPlayer(this.uuid));
    }

    /**
     * Changes the skin of the Player
     *
     * @param value
     * @param signature
     * @param displayForOthers Displays the new skin for all connected players if it is <b>true</b>
     * @param displayForSelf Displays the new skin on the targeted player's side if it is <b>true</b>
     */
    public void setSkin(String value, String signature, boolean displayForOthers, boolean displayForSelf) {
        getCurrentPlayer().ifPresent(currentPlayer -> {
            EntityPlayer entityPlayer = Reflection.getEntityPlayer(currentPlayer);
            GameProfile profile = entityPlayer.getProfile();
            PropertyMap propertyMap = profile.getProperties();
            Collection<Property> properties = propertyMap.get("textures");
            Property property = properties.iterator().next();
            if (Objects.isNull(defaultValue) && Objects.isNull(defaultSignature)) {
                this.defaultValue = property.getValue();
                this.defaultSignature = property.getSignature();
            }
            propertyMap.remove("textures", property);
            propertyMap.put("textures", new Property("textures", value, signature));

            if (displayForOthers) {
                reloadPlayer(currentPlayer);
            }
            if (displayForSelf) {
                reloadPlayerSelf(currentPlayer);
            }
        });
    }

    /**
     * Resets the skin to default skin of the Player
     *
     * @param displayForOthers Displays the new skin for all connected players if it is <b>true</b>
     * @param displayForSelf Displays the new skin on the targeted player's side if it is <b>true</b>
     */
    public void resetSkin(boolean displayForOthers, boolean displayForSelf) {
        if (Objects.nonNull(this.defaultValue) && Objects.nonNull(this.defaultSignature)) {
            setSkin(this.defaultValue, this.defaultSignature, displayForOthers, displayForSelf);
        }
    }

    /**
     * Reloads the current player for all connected players
     *
     * @param currentPlayer
     */
    private void reloadPlayer(Player currentPlayer) {
        for (Player target: Bukkit.getOnlinePlayers()) {
            target.hidePlayer(currentPlayer);
            target.showPlayer(currentPlayer);
        }
    }

    /**
     * Reloads the current player for itself
     *
     * @param currentPlayer
     */
    private void reloadPlayerSelf(Player currentPlayer) {
        EntityPlayer entityPlayer = Reflection.getEntityPlayer(currentPlayer);

        PacketPlayOutPlayerInfo removePlayerPacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer);
        PacketPlayOutEntityDestroy removeEntityPacket = new PacketPlayOutEntityDestroy(currentPlayer.getEntityId());
        PacketPlayOutNamedEntitySpawn addNamePacket = new PacketPlayOutNamedEntitySpawn(entityPlayer);
        PacketPlayOutPlayerInfo addPlayerPacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer);
        PacketPlayOutRespawn respawnPacket = new PacketPlayOutRespawn(entityPlayer.dimension, entityPlayer.getWorld().getDifficulty(), entityPlayer.getWorld().getWorldData().getType(), entityPlayer.playerInteractManager.getGameMode());
        Location location = currentPlayer.getLocation();
        Vector velocity = currentPlayer.getVelocity();

        Reflection.sendPackets(currentPlayer, removePlayerPacket, removeEntityPacket, addNamePacket, addPlayerPacket, respawnPacket);
        currentPlayer.updateInventory();
        currentPlayer.teleport(location);
        currentPlayer.setVelocity(velocity);
    }

}
