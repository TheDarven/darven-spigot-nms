package fr.thedarven.nmstry.utils.scoreboard;

import fr.thedarven.nmstry.Main;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheDarven
 */
public class ScoreboardManager {

    private final static HashMap<UUID, ScoreboardPlayer> scoreboardPlayerHashMap = new HashMap<>();
    private final Main main;

    public ScoreboardManager(Main main) {
        this.main = main;
    }

    public ScoreboardPlayer getScoreboard(Player player) {
        if (scoreboardPlayerHashMap.containsKey(player.getUniqueId())) {
            return scoreboardPlayerHashMap.get(player.getUniqueId());
        }
        return addScoreboard(player);
    }

    public ScoreboardPlayer addScoreboard(Player player) {
        ScoreboardPlayer scoreboardPlayer = new ScoreboardPlayer(player);
        scoreboardPlayerHashMap.put(player.getUniqueId(), scoreboardPlayer);
        return scoreboardPlayer;
    }

    public static void removeScoreboard(Player player) {
        ScoreboardPlayer scoreboardPlayer = scoreboardPlayerHashMap.get(player.getUniqueId());
        if (Objects.nonNull(scoreboardPlayer)) {
            scoreboardPlayer.removeScoreboard(player);
        }
        scoreboardPlayerHashMap.remove(player.getUniqueId());
    }

}
