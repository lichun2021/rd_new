import os
import pathlib
import subprocess
import tempfile
import textwrap
import unittest


ROOT = pathlib.Path(__file__).resolve().parents[3]
SKILL_DIR = ROOT / "src/main/java/com/hawk/game/player/hero/skill"
EFFECT_DIR = ROOT / "src/main/java/com/hawk/game/battle/effect/impl"

# 1.4.111 客户端的全部新英雄及补齐英雄(1121 莱因 / 1122 艾伦 / 1124 雪拉 / 1125 墨明 / 2002 谭雅) 作用号
HERO1121_CHECKERS = {
    "Checker12881.java": ("Type.ATK, Type.DEF, Type.HP", "EffType.HERO_12881"),
    "Checker12882.java": ("Type.SOLDIER_SKILL", "EffType.HERO_12882"),
    "Checker12883.java": ("Type.REDUCE_HURT_PCT", "EffType.HERO_12883"),
    "Checker12884.java": ("Type.REDUCE_HURT_PCT", "EffType.HERO_12884"),
}
HERO1122_STAFF_CHECKERS = {
    "Checker12941.java": ("Type.ATK, Type.DEF, Type.HP", "EffType.HERO_12941"),
    "Checker12942.java": ("Type.ATK, Type.DEF, Type.HP", "EffType.HERO_12942"),
    "Checker12943.java": ("Type.REDUCE_HURT_PCT", "EffType.HERO_12943"),
    "Checker12944.java": ("Type.REDUCE_HURT_PCT", "EffType.HERO_12944"),
}
HERO1124_CHECKERS = {
    "Checker12995.java": ("Type.ATK, Type.DEF, Type.HP", "EffType.HERO_12995"),
    "Checker12996.java": ("Type.REDUCE_HURT_PCT", "EffType.HERO_12996"),
    "Checker12997.java": ("Type.SOLDIER_SKILL", "EffType.HERO_12997"),
}
HERO1125_CHECKERS = {
    "Checker13005.java": ("Type.HURT_PCT", "EffType.HERO_12728"),          # 值 13005
    "Checker13006.java": ("Type.SOLDIER_SKILL", "EffType.HERO_12729"),     # 值 13006
    "Checker13007.java": ("Type.REDUCE_HURT_PCT", "EffType.HERO_12730"),   # 值 13007
    "Checker13008.java": ("Type.REDUCE_HURT_PCT", "EffType.HERO_12731"),   # 值 13008
    "Checker13009.java": ("Type.HURT_PCT", "EffType.HERO_12741"),          # 值 13009
    "Checker13010.java": ("Type.REDUCE_HURT_PCT", "EffType.HERO_12742"),   # 值 13010
    "Checker13011.java": ("Type.HURT_PCT", "EffType.HERO_12743"),          # 值 13011
    "Checker13014.java": ("Type.SOLDIER_SKILL", "EffType.HERO_12746"),     # 值 13014
    "Checker13022.java": ("Type.SOLDIER_SKILL", "EffType.HERO_13022"),     # 值 13022
}
HERO2002_CHECKERS = {
    "Checker12901.java": ("Type.SOLDIER_SKILL", "EffType.HERO_12901"),           # 猎杀指令
    "Checker12902.java": ("Type.ATK, Type.DEF, Type.HP", "EffType.HERO_12902"),  # 战术指挥: 单兵种
    "Checker12903.java": ("Type.HURT_PCT", "EffType.HERO_12903"),                # 战术指挥: 集结增伤
    "Checker12904.java": ("Type.REDUCE_HURT_PCT", "EffType.HERO_12904"),         # 战术指挥: 集结减伤
    "Checker12905.java": ("Type.ATK, Type.DEF, Type.HP", "EffType.HERO_12905"),  # 功勋授予: 战力/100万
    "Checker12906.java": ("Type.ATK, Type.DEF, Type.HP", "EffType.HERO_12906"),  # 意志强化: 攻防血衰减
    "Checker12907.java": ("Type.REDUCE_HURT_PCT", "EffType.HERO_12907"),         # 意志强化: 减伤衰减
    "Checker12911.java": ("Type.REDUCE_HURT_PCT", "EffType.HERO_12911"),         # 战技熟练度
    "Checker12921.java": ("Type.SOLDIER_SKILL", "EffType.HERO_12921"),           # 魂技: 定点爆破
    "Checker12922.java": ("Type.ATK, Type.DEF, Type.HP", "EffType.HERO_12922"),  # 魂技: 特工小队
    "Checker12923.java": ("Type.REDUCE_HURT_PCT", "EffType.HERO_12923"),         # 魂技: 特工网络
}

REGISTRATION_HARNESS = textwrap.dedent(
    """
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

            expectSkill(heroSkills, 112101, "Skill1121");
            expectSkill(heroSkills, 112105, "Skill1121");
            expectSkill(heroSkills, 112201, "Skill1122");
            expectSkill(heroSkills, 112205, "Skill1122");
            expectSkill(heroSkills, 112301, "Skill1123");
            expectSkill(heroSkills, 112305, "Skill1123");
            expectSkill(heroSkills, 112401, "Skill1124");
            expectSkill(heroSkills, 112405, "Skill1124");
            expectSkill(heroSkills, 112501, "Skill1125");
            expectSkill(heroSkills, 112505, "Skill1125");
            expectSkill(heroSkills, 200201, "Skill2002");
            expectSkill(heroSkills, 200205, "Skill2002");
            expectSkill(heroSkills, 112001, "Skill1120");

            // 1121 莱因
            expectChecker(BattleTupleType.Type.ATK, EffType.HERO_12881, "莱因 12881");
            expectChecker(BattleTupleType.Type.SOLDIER_SKILL, EffType.HERO_12882, "莱因 12882");
            expectChecker(BattleTupleType.Type.REDUCE_HURT_PCT, EffType.HERO_12883, "莱因 12883");
            expectChecker(BattleTupleType.Type.REDUCE_HURT_PCT, EffType.HERO_12884, "莱因 12884");

            // 1122 艾伦
            expectChecker(BattleTupleType.Type.ATK, EffType.HERO_12941, "艾伦 12941");
            expectChecker(BattleTupleType.Type.ATK, EffType.HERO_12942, "艾伦 12942");
            expectChecker(BattleTupleType.Type.REDUCE_HURT_PCT, EffType.HERO_12943, "艾伦 12943");
            expectChecker(BattleTupleType.Type.REDUCE_HURT_PCT, EffType.HERO_12944, "艾伦 12944");

            // 1123 莱克莎
            expectChecker(BattleTupleType.Type.REDUCE_HURT_PCT, EffType.HERO_12981, "莱克莎 12981");

            // 1124 雪拉
            expectChecker(BattleTupleType.Type.ATK, EffType.HERO_12995, "雪拉 12995");
            expectChecker(BattleTupleType.Type.REDUCE_HURT_PCT, EffType.HERO_12996, "雪拉 12996");
            expectChecker(BattleTupleType.Type.SOLDIER_SKILL, EffType.HERO_12997, "雪拉 12997");

            // 1125 墨明
            expectChecker(BattleTupleType.Type.HURT_PCT, EffType.HERO_12728, "墨明 13005");
            expectChecker(BattleTupleType.Type.SOLDIER_SKILL, EffType.HERO_12729, "墨明 13006");
            expectChecker(BattleTupleType.Type.REDUCE_HURT_PCT, EffType.HERO_12730, "墨明 13007");
            expectChecker(BattleTupleType.Type.REDUCE_HURT_PCT, EffType.HERO_12731, "墨明战技减伤 13008");
            expectChecker(BattleTupleType.Type.HURT_PCT, EffType.HERO_12741, "墨明战技增伤 13009");
            expectChecker(BattleTupleType.Type.REDUCE_HURT_PCT, EffType.HERO_12742, "墨明军魂减伤 13010");
            expectChecker(BattleTupleType.Type.HURT_PCT, EffType.HERO_12743, "墨明军魂增伤 13011");
            expectChecker(BattleTupleType.Type.SOLDIER_SKILL, EffType.HERO_12746, "墨明 13014");
            expectChecker(BattleTupleType.Type.SOLDIER_SKILL, EffType.HERO_13022, "墨明 13022");

            // 2002 谭雅
            expectChecker(BattleTupleType.Type.SOLDIER_SKILL, EffType.HERO_12901, "谭雅猎杀指令 12901");
            expectChecker(BattleTupleType.Type.ATK, EffType.HERO_12902, "谭雅战术指挥 12902");
            expectChecker(BattleTupleType.Type.HURT_PCT, EffType.HERO_12903, "谭雅战术指挥集结增伤 12903");
            expectChecker(BattleTupleType.Type.REDUCE_HURT_PCT, EffType.HERO_12904, "谭雅战术指挥集结减伤 12904");
            expectChecker(BattleTupleType.Type.ATK, EffType.HERO_12905, "谭雅功勋授予 12905");
            expectChecker(BattleTupleType.Type.ATK, EffType.HERO_12906, "谭雅意志强化攻防血 12906");
            expectChecker(BattleTupleType.Type.REDUCE_HURT_PCT, EffType.HERO_12907, "谭雅意志强化减伤 12907");
            expectChecker(BattleTupleType.Type.REDUCE_HURT_PCT, EffType.HERO_12911, "谭雅战技减伤 12911");
            expectChecker(BattleTupleType.Type.SOLDIER_SKILL, EffType.HERO_12921, "谭雅魂技定点爆破 12921");
            expectChecker(BattleTupleType.Type.ATK, EffType.HERO_12922, "谭雅魂技特工小队 12922");
            expectChecker(BattleTupleType.Type.REDUCE_HURT_PCT, EffType.HERO_12923, "谭雅魂技特工网络 12923");

            expectChecker(BattleTupleType.Type.REDUCE_HURT_PCT, EffType.HERO_12841, "旧英雄 1120 未被影响");

            System.out.println(failures == 0 ? "RESULT OK" : "RESULT FAIL " + failures);
            System.exit(failures == 0 ? 0 : 1);
        }
    }
    """
)


class NewHeroSkillRuntimeTest(unittest.TestCase):
    """1121~1125 / 2002 全部英雄技能与作用号代码落地校验：类存在、枚举引用正确、启动扫描全量注册。"""

    def test_skill_classes_declare_expected_skill_ids(self):
        cases = {
            "Skill1123.java": ("112301, 112302, 112303, 112304, 112305", "HERO_12994"),
            "Skill1125.java": ("112501, 112502, 112503, 112504, 112505", "HERO_12771"),
            "Skill2002.java": ("200201, 200202, 200203, 200204, 200205", "HERO_12924"),
        }
        for name, (skill_ids, soul_effect) in cases.items():
            path = SKILL_DIR / name
            self.assertTrue(path.is_file(), f"{name} 未实现")
            source = path.read_text(encoding="utf-8")
            self.assertIn(skill_ids, source)
            self.assertIn("getSoulEffVal(EffType." + soul_effect + ")", source, name)

        for staff_skill, skill_ids in (
            ("Skill1121.java", "112101, 112102, 112103, 112104, 112105"),
            ("Skill1122.java", "112201, 112202, 112203, 112204, 112205"),
            ("Skill1124.java", "112401, 112402, 112403, 112404, 112405"),
        ):
            path = SKILL_DIR / staff_skill
            self.assertTrue(path.is_file(), f"{staff_skill} 未实现")
            self.assertIn(skill_ids, path.read_text(encoding="utf-8"))

    def test_checkers_exist_with_expected_tuple(self):
        groups = (
            (HERO1121_CHECKERS, EFFECT_DIR / "hero1121"),
            (HERO1122_STAFF_CHECKERS, EFFECT_DIR / "hero1122"),
            (HERO1124_CHECKERS, EFFECT_DIR / "hero1124"),
            (HERO1125_CHECKERS, EFFECT_DIR / "hero1125"),
            (HERO2002_CHECKERS, EFFECT_DIR / "hero2002"),
        )
        for group, path in groups:
            for name, (tuple_type, eff_type) in group.items():
                checker = path / name
                self.assertTrue(checker.is_file(), f"{name} 未实现")
                source = checker.read_text(encoding="utf-8")
                self.assertIn(tuple_type, source, name)
                self.assertIn("effType = " + eff_type, source, name)

    def test_all_previously_missing_checkers_are_now_implemented(self):
        """原先缺失的 13 个 Checker 已全部补齐。"""
        required = {
            "Checker12901", "Checker12903", "Checker12904", "Checker12906", "Checker12907",
            "Checker12921", "Checker12922", "Checker12923",
            "Checker13005", "Checker13006", "Checker13007", "Checker13014", "Checker13022",
            "Checker12881", "Checker12882", "Checker12883", "Checker12884",
            "Checker12941", "Checker12942", "Checker12943", "Checker12944",
            "Checker12995", "Checker12996", "Checker12997",
        }
        existing = {path.stem for path in EFFECT_DIR.rglob("Checker*.java")}
        self.assertEqual(set(), required - existing)

    def test_tanya_effect_mapping_matches_client_design_text(self):
        """谭雅 skillId 的 effectList 顺序必须与客户端文案 HeroSkillDesWithValue200201 的 {0}~{7} 一致。"""
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
            SKILL_DIR / "Skill1121.java",
            SKILL_DIR / "Skill1122.java",
            SKILL_DIR / "Skill1123.java",
            SKILL_DIR / "Skill1124.java",
            SKILL_DIR / "Skill1125.java",
            SKILL_DIR / "Skill2002.java",
        ]
        for folder in ("hero1121", "hero1122", "hero1124", "hero1125", "hero2002"):
            sources += sorted((EFFECT_DIR / folder).glob("*.java"))

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
