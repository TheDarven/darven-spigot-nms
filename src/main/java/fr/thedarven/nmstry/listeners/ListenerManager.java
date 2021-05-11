package fr.thedarven.nmstry.listeners;

import fr.thedarven.nmstry.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ListenerManager {

    private Main main;

    public ListenerManager(Main main) {
        this.main = main;
    }

    public void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new JoinListener(this.main), this.main);
    }

}
