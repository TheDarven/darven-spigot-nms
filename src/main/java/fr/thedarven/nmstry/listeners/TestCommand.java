package fr.thedarven.nmstry.listeners;

import fr.thedarven.nmstry.Main;
import fr.thedarven.nmstry.utils.anvil.AnvilGUI;
import fr.thedarven.nmstry.utils.skull.Skull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TestCommand implements CommandExecutor {

    private final Main main;

    public TestCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (Objects.nonNull(this.main.customGamePlayer)) {
            this.main.customGamePlayer.setSkin(
                    "ewogICJ0aW1lc3RhbXAiIDogMTYxOTk3MDQ0NjM0MywKICAicHJvZmlsZUlkIiA6ICJiYmJhNWNjMTYwYTY0ODYxOTFlOWYwOWRhZjRjY2YzNiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBTmVyZFVuaWNvcm4iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzEwNmVhZWRkNWJlNzIxNjQ2ZTQwNjQ3MWM1ZDU1Mzk5MWEyZjA5M2U3NzY3ZDQ0MTQxZWFhNjc4MmNmMDgzZiIKICAgIH0KICB9Cn0=",
                    "Zzah6SARh6MGEyM45O23HdDo21ljW8KmwSahknsKhpbX2un0tfev1hbxPLKaGoy3AZao1OYoPsHBj9kqXY2u9LysmLoGJeSUXB6JzyG8EmM6VT8PL/v3SIVaSk1GNpmr8LGTf5DujfLVrCXs3vr5IfxFAPjpMWmMz0RGkl1AfIQV4kKDcJuQJ9cliQVSB8nVfAquXjTz8xI/aO2xnp6ekz8vtWGaPzlTkqBIyJF412DfNDydhQrHW1ESXpd1BgZBupMpILjzbKOIyM22RqSz6o/7OXS/8TcG2EvtBSWfaZ0eR0FPQI/0YUyP/msz5g7jq7iKOhl0GJD6HG25j2M8tm4iueuvtQMauQ/QpR8B4YUpB5wmiC4GIKtzFH3tG6isn9zU/rj0MovFXSz5hAYolBsSHVSXnoFs5BxGm/ivaK0gTos0rTliGkgvZbhiqQq8wbFPyFheIJF+TPLtH+uM4Zgs28a30dxnSJL3TbWSMBc5ppqxVl5Pgj+nkRwPCLsPDiyvhWF+ehU7sg2Ll1hRBInI0zxqPEVJEcVtkekGd2NrcKVx5avGV9pytC/m2tbxutXTtly4PPTjf7dT6y8aVoYCTVQ7ojOEaM5dV0ZzX1snQ/8eTrW6NI4u3p+gLUyGKWxqj9yc2T/vWKmZTuS5gL/JmIfaJXEmX5iejlcR6O0=",
                    true, true);
        }

        if (!(commandSender instanceof Player)) {
            return false;
        }

        Player player = (Player) commandSender;

        player.getWorld().dropItem(player.getLocation(), Skull.getPlayerHead(player.getName()));

        /* this.main.getScoreboardManager().getScoreboard(player);
        player.getWorld().dropItem(player.getLocation(), Skull.getPlayerHead("MHF_Melon"));
        player.getWorld().dropItem(player.getLocation(), Skull.getCustomSkull(
                "http://textures.minecraft.net/texture/5115275d044d56a373646dc5bb4c51bcd208cdf6b5afb6584f0e051082df34f0"
        ));

        StringBuilder result = new StringBuilder();
        if (strings.length > 0) {
            for (String word: strings) {
                result.append(word).append(" ");
            }
            Title.sendActionBar((Player) commandSender, result.toString());
            return true;
        }

        new AnvilGUI(this.main, player, (menu, text) -> {
            player.sendMessage(text);

            return false;
        }).setInputName("NOM").open(); */

        return true;
    }

}
