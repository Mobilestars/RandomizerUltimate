# RandomizerUltimate

**Version:** 1.0.0  
**Authors:** Mobilestars  
**Minecraft Version:** 1.18+  
**Download:** [Modrinth](https://modrinth.com/plugin/randomizerultimate)

**RandomizerUltimate** is a Minecraft plugin for Spigot/Paper that randomizes drops **per player**. Each player has their own `.yml` file with fully randomized block and mob drops. Players can have unique drops, and files can be edited manually or copied to other players.

---

## Features

- Per-player randomizer
- Randomizes **all Minecraft blocks and items**, including barriers and command blocks
- Player files `<playername>.yml` are generated automatically
- `/randomizer <player> start` → creates a new randomizer and overwrites the current player file
- `/randomizer <player> stop` → stops the randomizer and saves the file
- `/randomizer <player> copy <target>` → copies the drops from one player to another
- `/randomizer <player> reload` → reloads the current player file
- Tab completion for all commands and player names

---

## Installation

1. Compatible with **Minecraft 1.18+** and Spigot/Paper servers
2. Place the `.jar` file in the `plugins/` folder
3. Start the server → the plugin will create the `plugins/RandomizerUltimate/` folder automatically
4. Optionally edit `config.yml` for global settings like messages and prefix

---

## Commands

| Command | Description |
|---------|-------------|
| `/randomizer <player>` | Start the randomizer for a player |
| `/randomizer <player> start` | Creates a new randomizer and overwrites the player's current drops |
| `/randomizer <player> stop` | Stops the randomizer and saves the player's file |
| `/randomizer <player> copy <target>` | Copies the drops from one player to another |
| `/randomizer <player> reload` | Reloads the player's `.yml` file |

---

## Player File (`<playername>.yml`)

Each player has their own file located in the plugin folder. Example format:

<details>
  <summary><playername>.yml</summary>
```yaml
drops:
  STONE: DIAMOND
  DIRT: COAL
  ZOMBIE_HEAD: GOLD_INGOT
  COMMAND_BLOCK: BARRIER
```
</details>

Can be manually edited
Changes take effect after **/randomizer <player> reload** is used

<details>
<summary>config.yml</summary>

```yaml
settings:
  default-enabled: false
  prefix: "&6[Randomizer]"

messages:
  started: "&aRandomizer started for %player%"
  stopped: "&cRandomizer stopped for %player%"
  copied: "&eRandomizer copied from %from% to %to%"
  player-not-found: "&cPlayer not found!"
  usage: "&cUsage: /randomizer <player> [start|stop|copy <player>|reload]"
```
</details>

<details>
<summary>usage</summary>
<strong>default-enabled:</strong> Automatically start the randomizer when a player joins<br>
<strong>prefix:</strong> Chat message prefix<br>
<strong>messages:</strong> Customize messages using placeholders %player%, %from%, %to%
</details>
