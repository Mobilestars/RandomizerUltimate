package de.scholle.randomizerUltimate;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class RandomizerListener implements Listener {

    private final RandomizerManager manager;

    public RandomizerListener(RandomizerManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        RandomizerProfile profile = manager.getProfile(event.getPlayer().getUniqueId());
        if (profile != null) {
            Material original = event.getBlock().getType();
            ItemStack drop = profile.getDrop(original);
            event.setDropItems(false);
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), drop);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            RandomizerProfile profile = manager.getProfile(event.getEntity().getKiller().getUniqueId());
            if (profile != null) {
                event.getDrops().replaceAll(item -> profile.getDrop(item.getType()));
            }
        }
    }
}
