package de.scholle.randomizerUltimate;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class RandomizerManager {

    private final RandomizerUltimate plugin;
    private final Map<UUID, RandomizerProfile> activeProfiles = new HashMap<>();

    public RandomizerManager(RandomizerUltimate plugin) {
        this.plugin = plugin;
    }

    public void startRandomizer(Player player) {
        UUID uuid = player.getUniqueId();
        if (!activeProfiles.containsKey(uuid)) {
            RandomizerProfile profile = loadOrCreateProfile(player.getName());
            activeProfiles.put(uuid, profile);
            plugin.getLogger().info("Randomizer gestartet für " + player.getName());
        }
    }

    public void startNewRandomizer(Player player) {
        UUID uuid = player.getUniqueId();
        RandomizerProfile profile = createNewProfile(player.getName());
        activeProfiles.put(uuid, profile);
        saveProfile(profile);
    }

    private RandomizerProfile createNewProfile(String playerName) {
        File file = new File(plugin.getDataFolder(), playerName + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        Map<String, String> drops = new HashMap<>();
        Material[] allMaterials = Material.values();
        Random random = new Random();

        for (Material original : allMaterials) {
            Material randomMaterial = allMaterials[random.nextInt(allMaterials.length)];
            drops.put(original.name(), randomMaterial.name());
        }

        cfg.createSection("drops", drops);

        try {
            file.getParentFile().mkdirs();
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new RandomizerProfile(playerName, file, cfg);
    }

    public void stopRandomizer(Player player) {
        UUID uuid = player.getUniqueId();
        if (activeProfiles.containsKey(uuid)) {
            saveProfile(activeProfiles.get(uuid));
            activeProfiles.remove(uuid);
            plugin.getLogger().info("Randomizer gestoppt für " + player.getName());
        }
    }

    public void copyRandomizer(Player from, Player to) {
        RandomizerProfile original = activeProfiles.get(from.getUniqueId());
        if (original != null) {
            RandomizerProfile copy = original.clone(to.getName());
            activeProfiles.put(to.getUniqueId(), copy);
            saveProfile(copy);
            plugin.getLogger().info("Randomizer von " + from.getName() + " zu " + to.getName() + " kopiert.");
        }
    }

    public void reloadProfile(Player player) {
        UUID uuid = player.getUniqueId();
        RandomizerProfile profile = loadOrCreateProfile(player.getName());
        activeProfiles.put(uuid, profile);
    }

    public RandomizerProfile getProfile(UUID uuid) {
        return activeProfiles.get(uuid);
    }

    public void saveAll() {
        for (RandomizerProfile profile : activeProfiles.values()) {
            saveProfile(profile);
        }
    }

    private RandomizerProfile loadOrCreateProfile(String playerName) {
        File file = new File(plugin.getDataFolder(), playerName + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        boolean needsSave = false;

        if (!file.exists() || !cfg.contains("drops")) {
            Map<String, String> drops = new HashMap<>();
            Material[] allMaterials = Material.values();
            Random random = new Random();

            for (Material original : allMaterials) {
                Material randomMaterial = allMaterials[random.nextInt(allMaterials.length)];
                drops.put(original.name(), randomMaterial.name());
            }
            cfg.createSection("drops", drops);
            needsSave = true;
        }

        if (needsSave) {
            try {
                cfg.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new RandomizerProfile(playerName, file, cfg);
    }

    private void saveProfile(RandomizerProfile profile) {
        try {
            profile.getConfig().save(profile.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
