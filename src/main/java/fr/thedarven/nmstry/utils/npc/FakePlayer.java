package fr.thedarven.nmstry.utils.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import fr.thedarven.nmstry.utils.Reflection;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * Creates and displays an NPC
 *
 * @author TheDarven
 */
public class FakePlayer {

    private final String name;
    private String listName;
    private EntityPlayer entityPlayer;
    private final String textures;
    private final String signature;

    public FakePlayer(String name, String listName, String textures, String signature, World world, Location location) {
        this.listName = listName;
        this.name = name;
        this.textures = textures;
        this.signature = signature;
        createEntity(location, world);
    }

    public FakePlayer(String name, String textures, String signature, Location location, World world) {
        this.name = name;
        this.textures = textures;
        this.signature = signature;
        createEntity(location, world);
    }

    /**
     * Creates the NPC in the world
     *
     * @param location
     * @param world
     */
    private void createEntity(Location location, World world) {
        MinecraftServer minecraftServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer worldServer = ((CraftWorld) world).getHandle();

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), ChatColor.translateAlternateColorCodes('&', this.name));
        gameProfile.getProperties().put("textures", new Property("textures", this.textures, this.signature));

        this.entityPlayer = new EntityPlayer(minecraftServer, worldServer, gameProfile, new PlayerInteractManager(worldServer));
        this.entityPlayer.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        if (Objects.nonNull(this.listName)) {
            this.entityPlayer.listName = new ChatComponentText(this.listName);
        }
    }

    /**
     * Displays the NPC for all connected players
     */
    public void sendAll() {
        Bukkit.getOnlinePlayers().forEach(this::send);
    }

    /**
     * Displays the NPC for a player
     *
     * @param player
     */
    public void send(Player player) {
        Reflection.sendPackets(player, new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer));
        Reflection.sendPackets(player, new PacketPlayOutNamedEntitySpawn(entityPlayer));
    }

}
