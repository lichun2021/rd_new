# Heroes 1121-1123 Protocol Dependency

The `rd_new` repository contains the GameServer module only. Its Gradle build expects the generated protocol module at `../Protocol/Protobuf/Java`, but that module is not part of this repository.

The new configuration requires these `EffType` values in the deployed `gameprotocol.jar`:

- `HERO_12941`-`HERO_12944`
- `HERO_12961`-`HERO_12970`
- `HERO_12981`
- `HERO_12991`-`HERO_12997`

The corresponding trusted declarations exist in `rd_server/Protocol/Const.proto` and its generated `Const.java`. Deployment must build or supply that matching protocol artifact; do not commit a generated binary JAR to `rd_new`.

Heroes 1121 and 1123 are staff-officer configurations (`marchUsed="0"`) and do not require battle runtime classes. Hero 1122 is a march hero, but no trustworthy `Skill1122`/checker/state implementation was found in the available source history or exports. Its battle runtime remains an explicit release gate and must not be synthesized from text descriptions alone.
