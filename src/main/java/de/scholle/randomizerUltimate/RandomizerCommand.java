package de.scholle.randomizerUltimate;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomizerCommand implements CommandExecutor, TabCompleter {

    private final RandomizerManager manager;

    public RandomizerCommand(RandomizerManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§cVerwendung: /randomizer <player> [stop|copy <player>|reload]");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§cSpieler nicht gefunden!");
            return true;
        }

        if (args.length == 1) {
            manager.startRandomizer(target);
            sender.sendMessage("§aRandomizer gestartet für " + target.getName());
            return true;
        }

        switch (args[1].toLowerCase()) {
            case "start":
                manager.startNewRandomizer(target);
                sender.sendMessage("§aNeuer Randomizer für " + target.getName() + " erstellt.");
                return true;
            case "stop":
                manager.stopRandomizer(target);
                sender.sendMessage("§cRandomizer gestoppt für " + target.getName());
                return true;
            case "copy":
                if (args.length != 3) {
                    sender.sendMessage("§cVerwendung: /randomizer <player> copy <player>");
                    return true;
                }
                Player to = Bukkit.getPlayer(args[2]);
                if (to == null) {
                    sender.sendMessage("§cZielspieler nicht gefunden!");
                    return true;
                }
                manager.copyRandomizer(target, to);
                sender.sendMessage("§aRandomizer von " + target.getName() + " zu " + to.getName() + " kopiert.");
                return true;
            case "reload":
                manager.reloadProfile(target);
                sender.sendMessage("§aRandomizer für " + target.getName() + " neu geladen.");
                return true;
            default:
                sender.sendMessage("§cVerwendung: /randomizer <player> [start|stop|copy <player>|reload]");
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                    completions.add(player.getName());
                }
            }
        } else if (args.length == 2) {
            List<String> subs = List.of("stop", "copy", "reload");
            for (String s : subs) {
                if (s.startsWith(args[1].toLowerCase())) completions.add(s);
            }
        } else if (args.length == 3 && args[1].equalsIgnoreCase("copy")) { // Zielspieler
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().toLowerCase().startsWith(args[2].toLowerCase())) {
                    completions.add(player.getName());
                }
            }
        }
        Collections.sort(completions);
        return completions;
    }
}
