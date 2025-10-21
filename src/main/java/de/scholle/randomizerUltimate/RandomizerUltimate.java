package de.scholle.randomizerUltimate;

import org.bukkit.plugin.java.JavaPlugin;

public class RandomizerUltimate extends JavaPlugin {

    private static RandomizerUltimate instance;
    private RandomizerManager manager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        manager = new RandomizerManager(this);
        getCommand("randomizer").setExecutor(new RandomizerCommand(manager));
        getServer().getPluginManager().registerEvents(new RandomizerListener(manager), this);
        getLogger().info("RandomizerUltimate enabled!");
    }

    @Override
    public void onDisable() {
        manager.saveAll();
        getLogger().info("RandomizerUltimate disabled!");
    }

    public static RandomizerUltimate getInstance() {
        return instance;
    }
}
