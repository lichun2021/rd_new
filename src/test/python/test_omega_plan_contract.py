import os
from pathlib import Path
import re
import subprocess
import tempfile
import textwrap
import unittest
import xml.etree.ElementTree as ET


ROOT = Path(__file__).resolve().parents[3]

OMEGA_RUNTIME_HARNESS = textwrap.dedent(
    """
    import org.hawk.config.HawkConfigBase;
    import org.hawk.config.HawkConfigManager;
    import org.hawk.config.HawkConfigStorage;

    import com.hawk.game.battle.effect.BattleTupleType;
    import com.hawk.game.battle.effect.CheckerFactory;
    import com.hawk.game.battle.effect.IChecker;
    import com.hawk.game.config.DressCfg;
    import com.hawk.game.config.DressToolCfg;
    import com.hawk.game.config.EffectCfg;
    import com.hawk.game.config.ItemCfg;
    import com.hawk.game.config.ManhattanOmegaLevelCfg;
    import com.hawk.game.config.ManhattanOmegaSkillCfg;
    import com.hawk.game.config.ManhattanSWSkillCfg;
    import com.hawk.game.protocol.Const.EffType;

    public class OmegaRuntimeHarness {
        private static int failures;

        private static void check(boolean condition, String message) {
            if (!condition) {
                failures++;
                System.out.println("FAIL " + message);
            }
        }

        private static void loadConfig(Class<? extends HawkConfigBase> clazz) throws Exception {
            new HawkConfigStorage(clazz);
        }

        private static void expectChecker(BattleTupleType.Type tuple, EffType eff) {
            boolean found = false;
            for (IChecker checker : CheckerFactory.getInstance().checkersMap(tuple)) {
                if (checker.effType() == eff) {
                    found = true;
                    break;
                }
            }
            check(found, "checker not registered: " + tuple + ", " + eff);
        }

        public static void main(String[] args) throws Exception {
            new HawkConfigStorage((Class<? extends HawkConfigBase>) Class.forName("com.hawk.game.GsConfig"));
            HawkConfigManager.getInstance().setConfigRoot("");

            loadConfig(ManhattanOmegaLevelCfg.class);
            loadConfig(ManhattanOmegaSkillCfg.class);
            loadConfig(ManhattanSWSkillCfg.class);
            loadConfig(com.hawk.game.config.ManhattanSWStageCfg.class);
            loadConfig(com.hawk.game.config.ManhattanBaseStageCfg.class);
            loadConfig(EffectCfg.class);
            loadConfig(ItemCfg.class);
            loadConfig(DressCfg.class);
            loadConfig(DressToolCfg.class);

            check(ManhattanOmegaLevelCfg.getConfig(1, 0) != null, "pos 1 level 0 missing");
            check(ManhattanOmegaLevelCfg.getConfig(3, 150) != null, "pos 3 level 150 missing");
            check(ManhattanOmegaSkillCfg.getSkills(1).size() == 5, "pos 1 skills size != 5");
            check(ManhattanOmegaSkillCfg.getSkills(1).get(0).getAttrMap().containsKey(2222), "101 should map to 2222");
            check(ManhattanOmegaSkillCfg.getSkills(2).get(0).getAttrMap().containsKey(2223), "201 should map to 2223");
            check(ManhattanOmegaSkillCfg.getSkills(3).get(0).getAttrMap().containsKey(2221), "301 should map to 2221");
            check(ManhattanSWSkillCfg.getConfig(1001, 30, false).getLevel() == 5, "1001 stage 30 normal skill level != 5");
            check(ManhattanSWSkillCfg.getConfig(1001, 30, true).getLevel() == 6, "1001 stage 30 omega skill level != 6");

            // Regression check: ManhattanSW.create() before ManhattanBase.create() must not call getParent()/LocalCache
            com.hawk.game.entity.ManhattanEntity swEntity = new com.hawk.game.entity.ManhattanEntity();
            swEntity.setPlayerId("test_player_omega");
            swEntity.setBase(0);
            swEntity.setSwId(1001);
            swEntity.setStage(30);
            swEntity.setDeployed(1);
            com.hawk.game.player.manhattan.ManhattanSW.create(swEntity);
            check(swEntity.getManhattanObj().getUnlockedSkillCfg().getLevel() == 5, "SW read before Base should start at level 5");

            com.hawk.game.entity.ManhattanEntity baseEntity = new com.hawk.game.entity.ManhattanEntity();
            baseEntity.setPlayerId("test_player_omega");
            baseEntity.setBase(1);
            baseEntity.setStage(60);
            java.util.Map<Integer, Integer> omegaMap = new java.util.HashMap<>();
            omegaMap.put(1, 150);
            omegaMap.put(2, 150);
            omegaMap.put(3, 150);
            baseEntity.setOmegaPosLevel(com.hawk.serialize.string.SerializeHelper.mapToString(omegaMap));
            com.hawk.game.player.manhattan.ManhattanBase.create(baseEntity);
            check(swEntity.getManhattanObj().getUnlockedSkillCfg().getLevel() == 6, "SW after Base(150/150/150) should resolve to level 6");
            check(!swEntity.getManhattanObj().getEffValMap().isEmpty(), "SW effValMap should refresh after Base is read");

            CheckerFactory.getInstance().init();
            expectChecker(BattleTupleType.Type.SOLDIER_SKILL, EffType.EFF_2221);
            expectChecker(BattleTupleType.Type.SOLDIER_SKILL, EffType.EFF_2222);
            expectChecker(BattleTupleType.Type.SOLDIER_SKILL, EffType.EFF_2223);

            System.out.println(failures == 0 ? "RESULT OK" : "RESULT FAIL " + failures);
            System.exit(failures == 0 ? 0 : 1);
        }
    }
    """
)


class OmegaPlanContractTest(unittest.TestCase):
    def test_server_tables_are_complete_and_valid(self):
        level_path = ROOT / "xml" / "manhattan_omega_level.xml"
        skill_path = ROOT / "xml" / "manhattan_omega_skill.xml"
        effect_path = ROOT / "xml" / "effectid.xml"
        item_path = ROOT / "xml" / "item.xml"
        self.assertTrue(level_path.is_file())
        self.assertTrue(skill_path.is_file())

        levels = ET.parse(level_path).getroot().findall("data")
        self.assertEqual(453, len(levels))
        by_pos = {}
        for row in levels:
            by_pos.setdefault(int(row.attrib["posId"]), []).append(int(row.attrib["level"]))
        self.assertEqual({1, 2, 3}, set(by_pos))
        for values in by_pos.values():
            self.assertEqual(list(range(151)), sorted(values))

        skills = ET.parse(skill_path).getroot().findall("data")
        self.assertEqual(16, len(skills))
        skill_map = {int(row.attrib["id"]): row.attrib for row in skills}
        self.assertEqual(
            {101, 102, 103, 104, 105, 201, 202, 203, 204, 205,
             301, 302, 303, 304, 305, 901},
            set(skill_map),
        )
        # Ensure all unlockLevel values parse as int (including 901)
        for row in skills:
            self.assertIsInstance(int(row.attrib["unlockLevel"]), int)
        # Ensure 101~105 -> 2222, 201~205 -> 2223, 301~305 -> 2221
        for sid in range(101, 106):
            self.assertTrue(skill_map[sid]["attr"].startswith("2222_"))
        for sid in range(201, 206):
            self.assertTrue(skill_map[sid]["attr"].startswith("2223_"))
        for sid in range(301, 306):
            self.assertTrue(skill_map[sid]["attr"].startswith("2221_"))

        effect_ids = {int(r.attrib["id"]) for r in ET.parse(effect_path).getroot().findall("data")}
        self.assertTrue({2221, 2222, 2223} <= effect_ids)

        item_ids = {int(r.attrib["id"]) for r in ET.parse(item_path).getroot().findall("data")}
        self.assertIn(9990043, item_ids)

    def test_protocol_contract(self):
        proto_root = ROOT.parents[2] / "Code_Proto" / "1.4.109"
        if not proto_root.is_dir():
            proto_root = ROOT / "Code_Proto" / "1.4.109"
        manhattan = (proto_root / "Manhattan.proto").read_text(encoding="utf-8")
        hp = (proto_root / "HP.proto").read_text(encoding="utf-8")
        status = (proto_root / "Status.proto").read_text(encoding="utf-8")
        const = (proto_root / "Const.proto").read_text(encoding="utf-8")
        for token in ("omegaUnlocked", "omegaPosLevels", "ManhattanOmegaLevelUpReq"):
            self.assertIn(token, manhattan)
        self.assertRegex(hp, r"MANHATTAN_OMEGA_LEVEL_UP_C\s*=\s*203009")
        for token in ("MANHATTAN_OMEGA_NOT_EXIST", "MANHATTAN_OMEGA_MAX_LEVEL", "MANHATTAN_OMEGA_COND_LIMIT"):
            self.assertIn(token, status)
        for eff in (2221, 2222, 2223):
            self.assertRegex(const, rf"EFF_{eff}\s*=\s*{eff}")

    def test_runtime_and_persistence_contract(self):
        entity = (ROOT / "src/main/java/com/hawk/game/entity/ManhattanEntity.java").read_text(encoding="utf-8")
        module = (ROOT / "src/main/java/com/hawk/game/player/manhattan/PlayerManhattanModule.java").read_text(encoding="utf-8")
        base = (ROOT / "src/main/java/com/hawk/game/player/manhattan/ManhattanBase.java").read_text(encoding="utf-8")
        sw_skill = (ROOT / "src/main/java/com/hawk/game/config/ManhattanSWSkillCfg.java").read_text(encoding="utf-8")
        self.assertIn("omegaPosLevel", entity)
        self.assertIn("MANHATTAN_OMEGA_LEVEL_UP_C_VALUE", module)
        self.assertIn("omegaLevelUpgrade", base)
        self.assertIn("unlockOmega", sw_skill)
        self.assertIn("unlockOmegaStage", sw_skill)

    def test_battle_effects_have_checkers_and_soldier_round_hooks(self):
        checker_root = ROOT / "src/main/java/com/hawk/game/battle/effect/impl/manhattan"
        soldier_text = (ROOT / "src/main/java/com/hawk/game/battle/BattleSoldier.java").read_text(encoding="utf-8")
        for eff in (2221, 2222, 2223):
            text = (checker_root / f"Checker{eff}.java").read_text(encoding="utf-8")
            self.assertIn(f"EffType.EFF_{eff}", text)
            self.assertIn("Type.SOLDIER_SKILL", text)
            self.assertNotIn("getBattleRound()", text)
            self.assertIn(f"EffType.EFF_{eff}", soldier_text)

    def test_database_schema_has_omega_column(self):
        schema = (ROOT / "sql/game_new.sql").read_text(encoding="utf-8", errors="replace")
        migration = ROOT / "sql/update/20261013_manhattan_omega.sql"
        self.assertRegex(schema, r"`omegaPosLevel`\s+varchar")
        self.assertTrue(migration.is_file())
        self.assertIn("omegaPosLevel", migration.read_text(encoding="utf-8"))

    def test_jvm_config_load_and_checker_registration(self):
        classpath = self._classpath()
        if classpath is None:
            self.skipTest("需要已编译的 GameServer（build/classes/java/main）与 gameprotocol.jar")

        sources = [
            ROOT / "src/main/java/com/hawk/game/config/ManhattanOmegaLevelCfg.java",
            ROOT / "src/main/java/com/hawk/game/config/ManhattanOmegaSkillCfg.java",
            ROOT / "src/main/java/com/hawk/game/config/ManhattanSWSkillCfg.java",
            ROOT / "src/main/java/com/hawk/game/entity/ManhattanEntity.java",
            ROOT / "src/main/java/com/hawk/game/player/manhattan/ManhattanBase.java",
            ROOT / "src/main/java/com/hawk/game/player/manhattan/ManhattanSW.java",
            ROOT / "src/main/java/com/hawk/game/battle/effect/impl/manhattan/Checker2221.java",
            ROOT / "src/main/java/com/hawk/game/battle/effect/impl/manhattan/Checker2222.java",
            ROOT / "src/main/java/com/hawk/game/battle/effect/impl/manhattan/Checker2223.java",
        ]

        with tempfile.TemporaryDirectory() as tmp:
            tmp_path = Path(tmp)
            harness = tmp_path / "OmegaRuntimeHarness.java"
            harness.write_text(OMEGA_RUNTIME_HARNESS, encoding="utf-8")
            subprocess.run(
                ["javac", "-nowarn", "-encoding", "UTF-8", "-proc:none",
                 "-cp", classpath, "-d", str(tmp_path)] + [str(s) for s in sources] + [str(harness)],
                check=True, capture_output=True, text=True, encoding="utf-8", errors="replace",
            )
            result = subprocess.run(
                ["java", "-cp", str(tmp_path) + os.pathsep + classpath, "OmegaRuntimeHarness"],
                capture_output=True, text=True, encoding="utf-8", errors="replace", cwd=str(ROOT),
            )
            output = (result.stdout + "\n" + result.stderr).strip()
            self.assertEqual(0, result.returncode, "欧米伽配置与检查器加载失败:\n" + output)
            self.assertIn("RESULT OK", result.stdout)

    @staticmethod
    def _classpath():
        parts = []
        compiled = ROOT / "build/classes/java/main"
        protocol = ROOT / "lib/gameprotocol.jar"
        if not compiled.is_dir() or not protocol.is_file():
            return None
        parts.append(str(compiled))
        parts.append(str(protocol))
        for pattern in ("../HawkEngine/lib/*.jar", "../Publish/lib/gamelib.jar",
                        "../Publish/lib/gameactivity.jar", "../Publish/lib/gamelog.jar",
                        "lib/*.jar"):
            parts.extend(str(p) for p in sorted(ROOT.glob(pattern)))
        return os.pathsep.join(parts)


if __name__ == "__main__":
    unittest.main()
