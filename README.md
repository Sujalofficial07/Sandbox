# Hypixel SkyBlock - Offline Recreation

A complete, faithful recreation of Hypixel SkyBlock as a **100% offline, single-player Minecraft mod** for Fabric 1.20.1.

## 🎮 Overview

This project recreates the entire Hypixel SkyBlock experience in offline single-player, including:

- ✅ **Complete Stats System** - Health, Defense, Strength, Crit Chance/Damage, Mana, Intelligence, and more
- ✅ **12 Skills** - Combat, Mining, Farming, Foraging, Fishing, Enchanting, Alchemy, Taming, Carpentry, Runecrafting, Social, Dungeoneering
- ✅ **77 Collections** - Track items gathered, unlock recipes and rewards
- ✅ **Custom Items** - Rarity system, reforging, enchantments, abilities, set bonuses
- ✅ **Minions** - Automated resource gathering with offline production
- ✅ **Pets** - Leveling system with stat bonuses and perks
- ✅ **Economy** - Coins, bank, interest, NPC shops, Bazaar simulation
- ✅ **Islands** - Private islands, Hub zones, dimension teleportation
- ✅ **Combat** - Hypixel damage formulas, defense calculations, critical hits
- ✅ **HUD/UI** - Custom scoreboard, action bar, menus, tooltips

## 🛠️ Technical Details

- **Minecraft Version**: 1.20.1
- **Mod Loader**: Fabric
- **Java Version**: 17
- **Mappings**: Yarn
- **Architecture**: Modular multi-mod system

## 📦 Module Structure

The project is split into multiple interconnected mods:

| Module | Description |
|--------|-------------|
| `skyblock-core` | Foundation - profiles, stats, zones, events, HUD |
| `skyblock-items` | Item system, rarities, reforging, abilities |
| `skyblock-skills` | All 12 skills with XP tracking and rewards |
| `skyblock-collections` | Collection tracking and milestone rewards |
| `skyblock-combat` | Damage formulas, defense, critical hits |
| `skyblock-economy` | Coins, bank, shops, Bazaar |
| `skyblock-islands` | Island generation, teleportation, permissions |
| `skyblock-minions` | Minion placement, behavior, offline production |
| `skyblock-pets` | Pet system, leveling, perks |
| `skyblock-ui` | All GUI menus and interfaces |

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Minecraft 1.20.1 (Java Edition)
- Fabric Loader 0.15.11+

### Building from Source

1. Clone the repository:
```bash
git clone https://github.com/yourusername/hypixel-skyblock-offline.git
cd hypixel-skyblock-offline
```

2. Build all modules:
```bash
./gradlew build
```

3. Locate the compiled JARs in each module's `build/libs/` directory

### Installation

1. Install [Fabric Loader](https://fabricmc.net/use/) for Minecraft 1.20.1
2. Download [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api)
3. Place all SkyBlock mod JARs in your `.minecraft/mods` folder
4. Launch Minecraft with the Fabric profile

## 📖 Documentation

### Player Data Storage

All player data is stored in JSON format at:
```
world/skyblock-data/profiles/{player-uuid}.json
```

### Configuration

Module configs are located in:
```
config/skyblock-core.json
config/skyblock-items.json
config/skyblock-skills.json
...
```

### API Usage

Other mods can interact with SkyBlock through the provided APIs:
```java
// Get player profile
Optional<SkyBlockProfile> profile = SkyBlockProfileAPI.getProfile(player);

// Get player stats
Optional<SkyBlockStats> stats = SkyBlockStatsAPI.getStats(player);

// Create custom item
SkyBlockItemAPI.createItem("custom_sword", Material.DIAMOND_SWORD)
    .rarity("LEGENDARY")
    .stats(Map.of(StatType.STRENGTH, 100.0))
    .build();

// Register event listener
SkyBlockEventAPI.registerListener(PlayerProfileLoadEvent.class, event -> {
    // Handle profile load
});
```

## 🎯 Roadmap

### Phase 1 - Foundation ✅
- [x] Core module (profiles, stats, zones, HUD)
- [x] Data persistence
- [x] Event system
- [x] World protection

### Phase 2 - Items & Skills (In Progress)
- [ ] Item system with rarities
- [ ] Reforging system
- [ ] 12 skill implementations
- [ ] Collection tracking

### Phase 3 - Combat
- [ ] Damage formulas
- [ ] Defense calculations
- [ ] Floating damage numbers
- [ ] Item abilities

### Phase 4 - Economy & Islands
- [ ] Coin system
- [ ] Bank with interest
- [ ] NPC shops
- [ ] Bazaar simulation
- [ ] Island generation

### Phase 5 - Advanced Systems
- [ ] Minion system
- [ ] Offline production
- [ ] Pet system
- [ ] Accessory bag

### Phase 6 - Polish
- [ ] All GUI menus
- [ ] Quest system
- [ ] Fairy souls
- [ ] Slayer bosses

## 🤝 Contributing

Contributions are welcome! Please follow these guidelines:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Standards

- Java 17 syntax
- Follow existing code style
- No TODOs or placeholders in PRs
- All public APIs must be documented
- Include tests for new features

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## ⚠️ Disclaimer

This is a fan-made recreation for educational purposes. This project is not affiliated with, endorsed by, or connected to Hypixel Inc. or Mojang Studios.

Hypixel SkyBlock is a trademark of Hypixel Inc.  
Minecraft is a trademark of Mojang Studios.

## 🙏 Acknowledgments

- Hypixel for creating the original SkyBlock game mode
- Fabric team for the excellent modding framework
- The Minecraft modding community

## 📞 Contact

- Issues: [GitHub Issues](https://github.com/yourusername/hypixel-skyblock-offline/issues)
- Discussions: [GitHub Discussions](https://github.com/yourusername/hypixel-skyblock-offline/discussions)

---

**Note**: This is an active development project. Features are being added continuously. Check the [Roadmap](#-roadmap) for current progress.
```

---

## `LICENSE`
```
MIT License

Copyright (c) 2024 Sujal

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
