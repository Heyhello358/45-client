# 45 Client

**Custom Minecraft Java Utility Client**

Modern PvP-inspired aesthetic with neon green accents (`#39FF00`) on a dark minimalistic interface.

## Features

- **Click GUI** (Right Shift) with category navigation, module search, settings, smooth animations
- **Modules** across Combat, Movement, Render, Player, World, Misc
- **Custom HUD** elements: FPS, CPS, Coordinates, Keystrokes, Armor, Potions, Time, Ping, etc.
- **Config system** that saves between launches
- **Modular architecture** – easy to extend

## Requirements

- Minecraft **1.21.11**
- Fabric Loader **≥ 0.20.1**
- Fabric API
- Java **21**

## Building

```bash
./gradlew build
```

The output jar will be in `build/libs/45-client-1.0.0.jar`.

## Installation

1. Install Fabric for Minecraft 1.21.11
2. Install Fabric API
3. Place the 45 Client jar into your `.minecraft/mods` folder
4. Launch the game
5. Press **Right Shift** to open the Click GUI

## Color Palette

| Role              | Hex       |
|-------------------|-----------|
| Primary green     | `#39FF00` |
| Secondary green   | `#24CC00` |
| Background        | `#080808` |
| Panel background  | `#111111` |
| Text              | `#FFFFFF` |
| Secondary text    | `#888888` |

## Disclaimer

This client is intended for single-player or servers where the owner has explicitly authorized its use.  
Do not use features that disrupt other players or bypass anti-cheat systems.

## License

MIT
