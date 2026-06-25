# FactionsFork

A fork of [MassiveFactions 2.13.6](https://www.massivecraft.com/factions) (Brazilian edition by RUSHyoutuber) for PaperSpigot 1.8.8, adding native **faction tag** support.

## What's different from the original

| Feature | Original | This fork |
|---|---|---|
| Faction creation | `/f criar <name>` | `/f criar <TAG> <name>` |
| Chat format | `[#FactionName]` | `[#TAG]` |
| Change tag | ✗ | `/f tag <newTag>` |
| Tag stored in | external plugin needed | `Faction` entity (auto-persisted via Gson) |

## Tag rules

- 2–4 alphanumeric characters
- Unique across all factions
- Stored uppercase
- Leader-only to change after creation

## Commands

| Command | Description |
|---|---|
| `/f criar <TAG> <name>` | Create a new faction with a tag |
| `/f tag <newTag>` | Change your faction's tag (leader only) |

## Requirements

- PaperSpigot 1.8.8
- [MassiveCore](https://www.massivecraft.com/massivecore) (same version as the original Factions jar)

## Building

```bash
javac -encoding UTF-8 --release 8 -cp "libs/*" -d out $(find src -name "*.java")
jar cf Factions.jar -C out . && jar uf Factions.jar plugin.yml
```

Place `libs/` with `MassiveCore.jar`, `MassiveFactions.jar`, and `spigot-1.8.8.jar` before building.

## License

MIT — see [LICENSE](LICENSE).

## Credits

Original plugin by [MassiveCraft](https://www.massivecraft.com/) and the Brazilian fork by RUSHyoutuber. This repository contains decompiled and modified source code. The original authors retain their respective rights over the original work.

This fork was developed with the help of [Claude](https://claude.ai) (Anthropic), which assisted with decompilation analysis, all source modifications, compilation error fixes, and build/deploy automation.
