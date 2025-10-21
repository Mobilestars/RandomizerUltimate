package de.scholle.randomizerUltimate;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class RandomizerProfile implements Cloneable {

    private final String playerName;
    private final File file;
    private final FileConfiguration config;
    private final Map<Material, Material> drops = new HashMap<>();

    public RandomizerProfile(String playerName, File file, FileConfiguration config) {
        this.playerName = playerName;
        this.file = file;
        this.config = config;
        loadDrops();
    }

    private void loadDrops() {
        if (config.contains("drops")) {
            for (String key : config.getConfigurationSection("drops").getKeys(false)) {
                drops.put(Material.valueOf(key), Material.valueOf(config.getString("drops." + key)));
            }
        }
    }

    public ItemStack getDrop(Material original) {
        Material replacement = drops.getOrDefault(original, original);
        return new ItemStack(replacement);
    }

    public File getFile() { return file; }
    public FileConfiguration getConfig() { return config; }

    public RandomizerProfile clone(String newPlayerName) {
        File newFile = new File(file.getParentFile(), newPlayerName + ".yml");
        RandomizerProfile clone = new RandomizerProfile(newPlayerName, newFile, config);
        clone.drops.putAll(this.drops);
        return clone;
    }
}
