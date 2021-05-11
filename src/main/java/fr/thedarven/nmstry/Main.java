package fr.thedarven.nmstry;

import fr.thedarven.nmstry.listeners.ListenerManager;
import fr.thedarven.nmstry.listeners.TestCommand;
import fr.thedarven.nmstry.utils.npc.FakePlayer;
import fr.thedarven.nmstry.utils.profile.CustomGamePlayer;
import fr.thedarven.nmstry.utils.scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    public List<FakePlayer> npc = new ArrayList<>();
    public CustomGamePlayer customGamePlayer;

    private ScoreboardManager scoreboardManager;

    @Override
    public void onEnable() {
        super.onEnable();

        this.scoreboardManager = new ScoreboardManager(this);

        this.getCommand("test").setExecutor(new TestCommand(this));

        ListenerManager listenerManager = new ListenerManager(this);
        listenerManager.registerListeners();

        String textures = "ewogICJ0aW1lc3RhbXAiIDogMTYxNjM2NDgwNTc2OCwKICAicHJvZmlsZUlkIiA6ICI5MTMxOThiM2RkNmI0N2Y5YjVlYjZlYTJhZWEwMDQ0ZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJUaGVEYXJ2ZW4iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTExNTI3NWQwNDRkNTZhMzczNjQ2ZGM1YmI0YzUxYmNkMjA4Y2RmNmI1YWZiNjU4NGYwZTA1MTA4MmRmMzRmMCIKICAgIH0KICB9Cn0=";
        String signature = "a2y0GwE+MnAkM8kEIZtdZ8KFWzQ12EmAJa1oC61lymmLZGGc3Mq6E3bwW//4Yk1Tfl2nkV/Q3Eahvc3xonpoBWm7GHBwqaYdcqnNXd72cQhFcrkwZ12M+JQmjmMZNVu7DO/0t/TIBcVXvClHUnnMzKjPK/Ju7U4WBO6YxfQvHyc97p08LfDx+WMj8wQ81XAtRYsFykLiSK0KegULZ4CRXRG4VEVJbjW1AexVTdG9vbpVgUjmilWOEBFjtKQNOWK0oGzvJTlvxg/Pm2bA2aij7iQ6ODTh5eOFoXfZKrxMxm/dWIYYu473PHiHS9h3cRYq66rVEsXTvYLOSVpSoGeQK9yYn8m++WdsjWeANFmzy3UyFS8ER6gk3D8Hg8B7zx5Dn3fWqxdbyZ6GFc594nCEgm8OJ2BNfLK3jEeosXsazZbeX2Q4hgl21hXkhvcLpT+3Zc8RL8rn10UeQxbdUo0nm/NzQpz2kbSIHnqhpLWvqS44GYxxHu7ofV1JfIvjNHcsZp//U3FltfqGCrIm2VSFAosjEozNMVoQ1bT8SQNoitLSO7+9v8mIXYVGieYgSQ6I2/oTaW7gnuP1jAObhaVkr1voGUWq6o/KE5ObwMUc794DKS5F7s/1CMmsGHAfabkxcaOGa8Pc7oYM1Brk21TTETBzLOpdpWtZXBHcYdzJ4EE=";
        npc.add(new FakePlayer("&6Le &cnom", textures, signature, new Location(Bukkit.getWorld("world"), 0, 100, 0), Bukkit.getWorld("world")));
    }

    public ScoreboardManager getScoreboardManager() {
        return this.scoreboardManager;
    }
}
