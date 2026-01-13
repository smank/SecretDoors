# SecretDoors

A Spigot/Paper plugin for Minecraft that lets you create hidden passages disguised as normal blocks. Place a door behind bookshelves, stone walls, or any solid block - then right-click to reveal the secret entrance.

[![GitHub release](https://img.shields.io/github/v/release/smank/SecretDoors)](https://github.com/smank/SecretDoors/releases)
[![Minecraft Version](https://img.shields.io/badge/Minecraft-1.21.1-green)](https://papermc.io/)

## How It Works

Secret doors work by hiding a real Minecraft door behind "concealing blocks." When activated, the concealing blocks temporarily disappear and the door opens, creating a passage. When closed, everything is restored to its original state.

### Creating a Secret Door

1. **Place a door** - Any wooden door works (Oak, Spruce, Birch, etc.)
2. **Build concealing blocks in front** - Place solid blocks directly in front of the closed door (both the top and bottom block positions)
3. **Activate it** - Right-click either the concealing blocks or the door itself

```
Side view (door facing east):

[Stone][Door ][ Air ]     ->     [ Air ][Door ][ Air ]
[Stone][Door ][ Air ]     ->     [ Air ][Door ][ Air ]
        (closed)                       (open)
```

### Creating a Secret Trapdoor

1. **Place a trapdoor** - Must be attached to the upper half of a block (flip it so it sits on top)
2. **Place a concealing block above it** - One solid block directly on top
3. **Activate it** - Right-click the concealing block or the trapdoor

```
Side view:

[Stone]     ->     [ Air ]
[Trap ]     ->     [Trap ] (open, flipped up)
```

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

The config file is generated at `plugins/SecretDoors/config.yml` on first run.

```yaml
# Check permissions before allowing door use/creation
use-permissions: true

# Allow redstone signals to activate secret doors
enable-redstone: true

# Enable secret trapdoor functionality
enable-trapdoors: true

# Automatically close doors after a delay
enable-timers: false
close-time-seconds: 5

# Save and restore attached blocks (torches, signs, banners, etc.)
preserve-attachments: true

# Use whitelist mode instead of blacklist mode
enable-whitelist: false

# Blocks that CANNOT be used as concealing blocks (when enable-whitelist is false)
blacklist:
  - CHEST
  - TRAPPED_CHEST
  - ENDER_CHEST
  - BARREL
  - FURNACE
  - BLAST_FURNACE
  - SMOKER
  - CRAFTING_TABLE
  - DISPENSER
  # ... pressure plates, redstone components, doors

# Blocks that CAN be used as concealing blocks (when enable-whitelist is true)
whitelist:
  - STONE
  - COBBLESTONE
  - OAK_PLANKS
```

Use `/sd reload` after editing the config to apply changes.

## Building from Source

```bash
git clone https://github.com/smank/SecretDoors.git
cd SecretDoors
./gradlew build
```

The compiled JAR will be at `build/libs/SecretDoors-x.x.x.jar`

## Credits

**Current Maintainer:**
- [smank](https://github.com/smank) - Minecraft 1.21 update, attachment preservation, bug fixes

**Original Authors:**
- MrChick - Original concept
- dill01 - Early contributions
- Snnappie - Major rewrite
- Trainerlord - Previous maintainer

## License

This project uses a blessing-style license. See the source file headers for the full text.

## Links

- [GitHub Issues](https://github.com/smank/SecretDoors/issues) - Report bugs or request features
- [Releases](https://github.com/smank/SecretDoors/releases) - Download the latest version
