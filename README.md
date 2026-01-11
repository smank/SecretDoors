# SecretDoors

A Minecraft plugin that lets you create hidden doors disguised as regular blocks.

[![GitHub release](https://img.shields.io/github/v/release/smank/SecretDoors)](https://github.com/smank/SecretDoors/releases)
[![Minecraft Version](https://img.shields.io/badge/Minecraft-1.21.1-green)](https://papermc.io/)

## How It Works

1. Place a door (any type) behind blocks like bookshelves, stone, or wood
2. Right-click the blocks to open the secret passage
3. Right-click again to close it - the blocks reappear hiding the door

![Secret Door Demo](https://i.imgur.com/example.gif) <!-- TODO: Add actual demo gif -->

## Features

- Works with all door types (Oak, Spruce, Birch, Jungle, Acacia, Dark Oak, Mangrove, Cherry, Bamboo, Crimson, Warped, Copper variants)
- Works with all trapdoor types (including Iron)
- Preserves attached items (signs with text, torches) when opening/closing
- Redstone powered activation
- Auto-close timer option
- Permission support
- Configurable block whitelist/blacklist

## Installation

1. Download the latest JAR from [Releases](https://github.com/smank/SecretDoors/releases)
2. Place in your server's `plugins/` folder
3. Restart the server

**Requirements:** Paper/Spigot 1.21.1+, Java 21+

## Commands

| Command | Description | Permission |
|---------|-------------|------------|
| `/secretdoors reload` | Reload config | `secretdoors.reload` |
| `/sd reload` | Alias for above | `secretdoors.reload` |

## Permissions

| Permission | Description | Default |
|------------|-------------|---------|
| `secretdoors.*` | All permissions | OP |
| `secretdoors.use` | Open/close secret doors | OP |
| `secretdoors.create` | Create new secret doors | OP |
| `secretdoors.reload` | Reload configuration | OP |

## Configuration

```yaml
# Enable permission checking
use-permissions: true

# Allow redstone to open/close doors
enable-redstone: true

# Enable trapdoor support
enable-trapdoors: true

# Auto-close doors after a delay
enable-timers: false
close-time-seconds: 5

# Preserve attached blocks (signs, torches) when door opens/closes
preserve-attachments: true

# Use whitelist instead of blacklist
enable-whitelist: false

# Blocks that CANNOT be used as secret door covers
blacklist:
  - CHEST
  - FURNACE
  # ... etc

# If enable-whitelist is true, ONLY these blocks can be used
whitelist:
  - STONE
  - COBBLESTONE
  - OAK_PLANKS
```

## Building from Source

```bash
git clone https://github.com/smank/SecretDoors.git
cd SecretDoors
./gradlew build
```

Output: `build/libs/SecretDoors-x.x.x.jar`

## Credits

**Current Maintainer:**
- smank - MC 1.21 update, bug fixes, modernization

**Original Authors:**
- MrChick - Original concept
- dill01 - Contributions
- Snnappie - Major rewrite
- Trainerlord - Previous maintainer

## License

This project maintains the original authors' blessing-style license. See source files for details.

## Links

- [GitHub Issues](https://github.com/smank/SecretDoors/issues) - Bug reports & feature requests
- [SpigotMC](#) - Coming soon
- [Modrinth](#) - Coming soon
