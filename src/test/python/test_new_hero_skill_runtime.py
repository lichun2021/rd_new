import os
import pathlib
import shutil
import subprocess
import tempfile
import textwrap
import unittest


ROOT = pathlib.Path(__file__).resolve().parents[3]
SKILL_DIR = ROOT / "src/main/java/com/hawk/game/player/hero/skill"
EFFECT_DIR = ROOT / "src/main/java/com/hawk/game/battle/effect/impl"

# 1.4.111 客户端的两个新英雄(墨明 1125 / 谭雅 2002) 战技与魂技作用号
HERO1125_CHECKERS = {
    "Checker13008.java": ("Type.REDUCE_HURT_PCT", "EffType.HERO_12731"),  # 值 13008
    "Checker13009.java": ("Type.HURT_PCT", "EffType.HERO_12741"),          # 值 13009
    "Checker13010.java": ("Type.REDUCE_HURT_PCT", "EffType.HERO_12742"),   # 值 13010
    "Checker13011.java": ("Type.HURT_PCT", "EffType.HERO_12743"),          # 值 13011
}
HERO2002_CHECKERS = {
    "Checker12911.java": ("Type.REDUCE_HURT_PCT", "EffType.HERO_12911"),  # 战技熟练度
    "Checker12902.java": ("Type.ATK, Type.DEF, Type.HP", "EffType.HERO_12902"),  # 战术指挥: 单兵种
    "Checker12905.java": ("Type.ATK, Type.DEF, Type.HP", "EffType.HERO_12905"),  # 功勋授予: 战力/100万
}

REGISTRATION_HARNESS = textwrap.dedent(
    """
    import java.util.Collection;

    import com.hawk.game.battle.effect.BattleTupleType;
    import com.hawk.game.battle.effect.CheckerFactory;
    import com.hawk.game.battle.effect.IChecker;
    import com.hawk.game.player.hero.skill.HeroSkillFactory;
    import com.hawk.game.player.hero.skill.IHeroSkill;
    import com.hawk.game.protocol.Const.EffType;

    public class NewHeroSkillHarness {
        private static int failures;

        private static void check(boolean condition, String message) {
            if (!condition) {
                failures++;
                System.out.println("FAIL " + message);
            }
        }

        private static void expectChecker(BattleTupleType.Type tuple, EffType eff, String what) {
            boolean found = false;
            for (IChecker checker : CheckerFactory.getInstance().checkersMap(tuple)) {
                if (checker.effType() == eff) {
                    found = true;
                    break;
                }
            }
            check(found, "checker not registered: " + what + " (" + tuple + ", " + eff + ")");
        }

        private static void expectSkill(HeroSkillFactory factory, int skillId, String expected) {
            IHeroSkill skill = factory.createEmptySkill(skillId);
            check(skill != null && expected.equals(skill.getClass().getSimpleName()),
                    "skill " + skillId + " resolved to " + (skill == null ? "null" : skill.getClass().getSimpleName())
                            + ", expected " + expected);
        }

        public static void main(String[] args) throws Exception {
            new org.hawk.config.HawkConfigStorage(
                    (Class<? extends org.hawk.config.HawkConfigBase>) Class.forName("com.hawk.game.GsConfig"));
            org.hawk.config.HawkConfigManager.getInstance().setConfigRoot("");

            CheckerFactory.getInstance().init();
            HeroSkillFactory heroSkills = HeroSkillFactory.getInstance();

            expectSkill(heroSkills, 112501, "Skill1125");
            expectSkill(heroSkills, 112505, "Skill1125");
            expectSkill(heroSkills, 200201, "Skill2002");
            expectSkill(heroSkills, 200205, "Skill2002");
            expectSkill(heroSkills, 112001, "Skill1120");
            expectSkill(heroSkills, 112201, "Skill1122");

            expectChecker(BattleTupleType.Type.REDUCE_HURT_PCT, EffType.HERO_12731, "墨明战技减伤 13008");
            expectChecker(BattleTupleType.Type.HURT_PCT, EffType.HERO_12741, "墨明战技增伤 13009");
            expectChecker(BattleTupleType.Type.REDUCE_HURT_PCT, EffType.HERO_12742, "墨明军魂减伤 13010");
            expectChecker(BattleTupleType.Type.HURT_PCT, EffType.HERO_12743, "墨明军魂增伤 13011");
            expectChecker(BattleTupleType.Type.REDUCE_HURT_PCT, EffType.HERO_12911, "谭雅战技减伤 12911");
            expectChecker(BattleTupleType.Type.ATK, EffType.HERO_12902, "谭雅战术指挥 12902");
            expectChecker(BattleTupleType.Type.ATK, EffType.HERO_12905, "谭雅功勋授予 12905");
            expectChecker(BattleTupleType.Type.REDUCE_HURT_PCT, EffType.HERO_12841, "旧英雄 1120 未被影响");
            expectChecker(BattleTupleType.Type.REDUCE_HURT_PCT, EffType.HERO_12981, "旧英雄 1122 未被影响");

            System.out.println(failures == 0 ? "RESULT OK" : "RESULT FAIL " + failures);
            System.exit(failures == 0 ? 0 : 1);
        }
    }
    """
)


class NewHeroSkillRuntimeTest(unittest.TestCase):
    """墨明/谭雅 战技代码落地校验：类存在、枚举引用正确、启动扫描能注册上。"""

    def test_skill_classes_declare_expected_skill_ids(self):
        cases = {
            "Skill1125.java": ("112501, 112502, 112503, 112504, 112505", "HERO_12771"),
            "Skill2002.java": ("200201, 200202, 200203, 200204, 200205", "HERO_12924"),
        }
        for name, (skill_ids, soul_effect) in cases.items():
            path = SKILL_DIR / name
            self.assertTrue(path.is_file(), f"{name} 未实现")
            source = path.read_text(encoding="utf-8")
            self.assertIn(skill_ids, source)
            self.assertIn("getSoulEffVal(EffType." + soul_effect + ")", source, name)

    def test_checkers_exist_with_expected_tuple(self):
        for group, path in ((HERO1125_CHECKERS, EFFECT_DIR / "hero1125"), (HERO2002_CHECKERS, EFFECT_DIR / "hero2002")):
            for name, (tuple_type, eff_type) in group.items():
                checker = path / name
                self.assertTrue(checker.is_file(), f"{name} 未实现")
                source = checker.read_text(encoding="utf-8")
                self.assertIn(tuple_type, source, name)
                self.assertIn("effType = " + eff_type, source, name)

    def test_effects_not_yet_implemented_are_absent(self):
        """仍未实现的作用号：触发条件/衰减系数在客户端文案里没有给出，不臆造。

        谭雅：12901 猎杀指令(需每5回合追加攻击的战斗钩子)、12903/12904 集结增减伤(配置系数为0)、
              12906/12907 意志强化(文案只说"每回合衰减、第10回合后消失"，未给衰减系数)
              以及依赖它们的魂技 12921~12923
        墨明：13005~13007、13014、13022 —— 客户端无 HeroSkillDesWithValue1125，只有短标签
        """
        unimplemented = ("Checker12901", "Checker12903", "Checker12904", "Checker12906", "Checker12907",
                         "Checker12921", "Checker12922", "Checker12923",
                         "Checker13005", "Checker13006", "Checker13007", "Checker13014", "Checker13022")
        existing = {path.stem for path in EFFECT_DIR.rglob("Checker*.java")}
        self.assertEqual(set(), set(unimplemented) & existing)

    def test_tanya_effect_mapping_matches_client_design_text(self):
        """谭雅 skillId 的 effectList 顺序必须与客户端文案 HeroSkillDesWithValue200201 的 {0}~{7} 一致。

        文案取值顺序：{0}芯片超能攻击 1980、{1}猎杀指令 12901、{2}战术指挥 12902、
        {3}/{4}集结增减伤 12903/12904、{5}功勋授予 12905、{6}/{7}意志强化 12906/12907
        """
        config = (ROOT / "xml/hero_skill.xml").read_text(encoding="utf-8")
        row = next(line for line in config.splitlines() if 'skillId="200201"' in line)
        effect_list = row.split('effectList="', 1)[1].split('"', 1)[0]
        self.assertEqual("1980_0_0.5|12901_0_0.5|12902_0_0.5|12903_0_0|12904_0_0|12905_0_0.5|12906_0_0.5|12907_0_0.03",
                         effect_list)

    def test_registration_is_closed(self):
        classpath = self._classpath()
        if classpath is None:
            self.skipTest("需要已编译的 GameServer（build/classes/java/main）与 gameprotocol.jar")

        sources = [
            SKILL_DIR / "Skill1125.java",
            SKILL_DIR / "Skill2002.java",
        ]
        sources += sorted((EFFECT_DIR / "hero1125").glob("*.java"))
        sources += sorted((EFFECT_DIR / "hero2002").glob("*.java"))

        with tempfile.TemporaryDirectory() as tmp:
            tmp_path = pathlib.Path(tmp)
            harness = tmp_path / "NewHeroSkillHarness.java"
            harness.write_text(REGISTRATION_HARNESS, encoding="utf-8")
            subprocess.run(
                ["javac", "-nowarn", "-encoding", "UTF-8", "-proc:none",
                 "-cp", classpath, "-d", str(tmp_path)] + [str(s) for s in sources] + [str(harness)],
                check=True, capture_output=True, text=True, encoding="utf-8", errors="replace",
            )
            result = subprocess.run(
                ["java", "-cp", str(tmp_path) + os.pathsep + classpath, "NewHeroSkillHarness"],
                capture_output=True, text=True, encoding="utf-8", errors="replace", cwd=str(ROOT),
            )
            output = "\n".join(line for line in (result.stdout + result.stderr).splitlines()
                               if "RESULT" in line or "FAIL" in line)
            self.assertEqual(0, result.returncode, "新英雄战技注册校验失败:\n" + output)
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
    unittest.main(verbosity=2)
