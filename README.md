# SecretDoors

The classic plugin, updated for 1.21.

A Spigot/Paper plugin for Minecraft that lets you create hidden passages disguised as normal blocks. Place a door behind bookshelves, stone walls, or any solid block - then right-click to reveal the secret entrance.

[![GitHub release](https://img.shields.io/github/v/release/smank/SecretDoors)](https://github.com/smank/SecretDoors/releases)
[![Minecraft Version](https://img.shields.io/badge/Minecraft-1.21-green)](https://papermc.io/)

## How It Works

Secret doors work by hiding a real Minecraft door behind "concealing blocks." When activated, the concealing blocks temporarily disappear and the door opens, creating a passage. When closed, everything is restored to its original state.

#### Secret Door

1. Place any wooden or copper door
2. Build solid blocks directly in front of the closed door (both top and bottom)
3. Right-click the blocks or door to open/close

https://github.com/user-attachments/assets/f481add3-39f2-4af4-a258-4ab5c0dfcdd1

#### Secret Trapdoor

1. Place a trapdoor on the upper half of a block
2. Place a solid block directly on top
3. Right-click to open/close

https://github.com/user-attachments/assets/aa0caa38-b7d0-443d-9a59-e5c10e4df32a

### What Blocks Can Be Used?

Almost any solid block can be a concealing block. By default, the plugin blacklists blocks that have their own interactions (chests, furnaces, pressure plates, etc.) to prevent conflicts.

You can customize this in the config with either:
- **Blacklist mode** (default): All blocks work except those listed
- **Whitelist mode**: Only blocks you specify will work

## Installation

1. Download `SecretDoors-2.0.0.jar` from [Releases](https://github.com/smank/SecretDoors/releases)
2. Place the JAR in your server's `plugins/` folder
3. Start or restart your server
4. (Optional) Edit `plugins/SecretDoors/config.yml` to customize settings

**Requirements:**
- Spigot or Paper 1.21.1+
- Java 21+

## Features

- **All door types supported** - Oak, Spruce, Birch, Jungle, Acacia, Dark Oak, Mangrove, Cherry, Bamboo, Crimson, Warped, and all Copper variants
- **Trapdoor support** - All trapdoor types including Iron
- **Attachment preservation** - Torches, signs, banners, skulls, and other items attached to concealing blocks are saved when the door opens and restored when it closes
- **Sign text preserved** - Signs keep their text through open/close cycles
- **Redstone activation** - Power the door or concealing blocks with redstone to open/close
- **Auto-close timer** - Optionally have doors close automatically after a set time
- **Permission system** - Control who can use and create secret doors
- **Whitelist/Blacklist** - Configure exactly which blocks can be used as concealing blocks

## Commands

| Command | Alias | Description |
|---------|-------|-------------|
| `/secretdoors reload` | `/sd reload` | Reload the configuration file |

## Permissions

| Permission | Description | Default |
|------------|-------------|---------|
| `secretdoors.*` | All SecretDoors permissions | OP |
| `secretdoors.use` | Use (open/close) secret doors | OP |
| `secretdoors.create` | Create new secret doors | OP |
| `secretdoors.reload` | Use the reload command | OP |

Set `use-permissions: false` in config to disable permission checking entirely.

## Configuration

The config file is generated at `plugins/SecretDoors/config.yml` on first run. Use `/sd reload` after editing to apply changes.

### Basic Options

| Option | Default | Description |
|--------|---------|-------------|
| `use-permissions` | `true` | Require permissions to use/create secret doors |
| `enable-redstone` | `true` | Allow redstone signals to open/close secret doors |
| `enable-trapdoors` | `true` | Enable secret trapdoor functionality |
| `enable-timers` | `false` | Automatically close doors after a delay |
| `close-time-seconds` | `5` | Seconds before auto-close (if timers enabled) |
| `preserve-attachments` | `true` | Save and restore torches, signs, banners, etc. |

### Block Restrictions (Whitelist/Blacklist)

You can control which blocks are allowed to be used as concealing blocks.

**Blacklist mode** (default): All blocks work except those listed in `blacklist`. This is useful for blocking blocks that have their own right-click action (like chests opening their inventory).

**Whitelist mode**: Only blocks listed in `whitelist` can be used. Enable with `enable-whitelist: true`. This is useful if you want to restrict secret doors to specific blocks like bookshelves or stone.

```yaml
# Default: blacklist mode
enable-whitelist: false

# Blocks that CANNOT be concealing blocks (blacklist mode)
blacklist:
  - CHEST
  - TRAPPED_CHEST
  - FURNACE
  # Add any block you want to exclude

# Blocks that CAN be concealing blocks (whitelist mode only)
whitelist:
  - BOOKSHELF
  - STONE
  - OAK_PLANKS
  # Add any block you want to allow
```

Block names use [Spigot Material names](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html) (e.g., `OAK_PLANKS`, `STONE_BRICKS`, `BOOKSHELF`).

## Building from Source

```bash
git clone https://github.com/smank/SecretDoors.git
cd SecretDoors
./gradlew build
```

The compiled JAR will be at `build/libs/SecretDoors-x.x.x.jar`

## Credits

Forked from [shazean/SecretDoors](https://github.com/shazean/SecretDoors).

**Current Maintainer:**
- [smank](https://github.com/smank) - Minecraft 1.21 update, attachment preservation, bug fixes

**Original Authors:**
- MrChick - Original concept
- dill01 - Early contributions
- Snnappie - Major rewrite
- Trainerlord - Previous maintainer

## License

GPL v3. See [LICENSE](LICENSE) for details.

## Links

- [GitHub Issues](https://github.com/smank/SecretDoors/issues) - Report bugs or request features
- [Releases](https://github.com/smank/SecretDoors/releases) - Download the latest version
