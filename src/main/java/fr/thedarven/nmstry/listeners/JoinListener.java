package fr.thedarven.nmstry.listeners;

import fr.thedarven.nmstry.Main;
import fr.thedarven.nmstry.utils.f3.DisableF3;
import fr.thedarven.nmstry.utils.npc.FakePlayer;
import fr.thedarven.nmstry.utils.profile.CustomGamePlayer;
import fr.thedarven.nmstry.utils.title.TabMessage;
import fr.thedarven.nmstry.utils.title.Title;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class JoinListener implements Listener {

    private Main main;

    public JoinListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        System.out.println(event.getPlayer().getName() + " est arrivé !");
        Player player = event.getPlayer();
        CraftPlayer craftPlayer = (CraftPlayer) player;

        PlayerConnection playerConnection = craftPlayer.getHandle().playerConnection;

        for (FakePlayer fakePlayer: this.main.npc) {
            fakePlayer.send(player);
            /* playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer));
            playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(entityPlayer)); */
            // playerConnection.sendPacket(new PacketPlayOutEntityHeadRotation(entityPlayer, (byte) (entityPlayer.yaw * 256 / 360)));
        }

        new TabMessage("\n§cHeader\n", "\n§bFooter\n").sendTabTitle(player);

        DisableF3.disableF3(player);
        DisableF3.enableF3(player);

        new Title("§cVotre ping", "§d" + craftPlayer.getHandle().ping, 20).sendTitle(player);

        if (Objects.isNull(this.main.customGamePlayer)) {
            this.main.customGamePlayer = new CustomGamePlayer(player.getUniqueId());
        }
    }

}
