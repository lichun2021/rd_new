import re
import subprocess
import tempfile
import unittest
import xml.etree.ElementTree as ET
from pathlib import Path


HERO_IDS = tuple(range(1115, 1126))
SOUL_HERO_IDS = (1116, 1118, 1120, 1122, 1125)
REQUIRED_EFFECT_IDS = {
    1121,
    1122,
    1123,
    *range(12881, 12885),
    *range(12941, 12945),
    *range(12961, 12971),
    12981,
    *range(12991, 12998),
}
EXPECTED_COUNTS = {
    "hero.xml": {hero_id: 1 for hero_id in HERO_IDS},
    "item.xml": {hero_id: 2 for hero_id in HERO_IDS},
    "hero_collect.xml": {hero_id: 1 for hero_id in HERO_IDS},
    "hero_star_level.xml": {hero_id: 21 for hero_id in HERO_IDS},
    "hero_skill.xml": {
        1115: 9,
        1116: 5,
        1117: 9,
        1118: 5,
        1119: 9,
        1120: 5,
        1121: 9,
        1122: 5,
        1123: 9,
        1124: 9,
        1125: 5,
    },
    "hero_soul_level.xml": {
        hero_id: 300 if hero_id in SOUL_HERO_IDS else 0
        for hero_id in HERO_IDS
    },
    "hero_soul_skill.xml": {
        hero_id: 6 if hero_id in SOUL_HERO_IDS else 0
        for hero_id in HERO_IDS
    },
    "hero_soul_stage.xml": {
        hero_id: 6 if hero_id in SOUL_HERO_IDS else 0
        for hero_id in HERO_IDS
    },
}


def rows(xml_root: Path, name: str):
    return ET.parse(xml_root / name).getroot().findall("data")


def hero_id_for(name: str, row: ET.Element):
    if name in ("hero.xml", "hero_collect.xml"):
        return int(row.attrib["heroId"])
    if name == "item.xml":
        item_id = int(row.attrib["id"])
        if 1001115 <= item_id <= 1001125:
            return item_id - 1000000
        if 1101115 <= item_id <= 1101125:
            return item_id - 1100000
        return None
    if name == "hero_star_level.xml":
        return int(row.attrib["heroId"])
    if name == "hero_skill.xml":
        return int(row.attrib["skillId"]) // 100
    if name.startswith("hero_soul_"):
        return int(row.attrib["hero"])
    raise AssertionError(f"unsupported table: {name}")


def effect_ids(value: str):
    for item in re.split(r"[|,]", value):
        if item:
            yield int(item.split("_", 1)[0])


class NewHeroConfigClosureTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.repo = Path(__file__).resolve().parents[3]
        cls.xml_root = cls.repo / "xml"

    def test_expected_rows_exist_once(self):
        for name, expected in EXPECTED_COUNTS.items():
            actual = {hero_id: 0 for hero_id in HERO_IDS}
            for row in rows(self.xml_root, name):
                hero_id = hero_id_for(name, row)
                if hero_id in actual:
                    actual[hero_id] += 1
            self.assertEqual(expected, actual, name)

    def test_new_skill_effect_references_are_defined(self):
        referenced = set()
        for row in rows(self.xml_root, "hero_skill.xml"):
            if int(row.attrib["skillId"]) // 100 not in HERO_IDS:
                continue
            referenced.update(effect_ids(row.attrib.get("effectList", "")))
            referenced.update(effect_ids(row.attrib.get("proficiencyEffect", "")))
        for row in rows(self.xml_root, "hero_soul_skill.xml"):
            if int(row.attrib["hero"]) in HERO_IDS:
                referenced.update(effect_ids(row.attrib.get("attr", "")))
        defined = {int(row.attrib["id"]) for row in rows(self.xml_root, "effectid.xml")}
        self.assertEqual(set(), referenced - defined)

    def test_required_effect_ids_exist_once(self):
        actual = {effect_id: 0 for effect_id in REQUIRED_EFFECT_IDS}
        for row in rows(self.xml_root, "effectid.xml"):
            effect_id = int(row.attrib["id"])
            if effect_id in actual:
                actual[effect_id] += 1
        self.assertEqual({effect_id: 1 for effect_id in REQUIRED_EFFECT_IDS}, actual)

    def test_new_collect_references_resolve_to_heroes(self):
        defined = {int(row.attrib["heroId"]) for row in rows(self.xml_root, "hero.xml")}
        missing = {}
        for row in rows(self.xml_root, "hero_collect.xml"):
            hero_id = int(row.attrib["heroId"])
            if hero_id not in HERO_IDS:
                continue
            referenced = {
                int(item.split("_", 1)[0])
                for item in re.split(r"[|,]", row.attrib.get("refHeroIds", ""))
                if item
            }
            unresolved = sorted(referenced - defined)
            if unresolved:
                missing[hero_id] = unresolved
        self.assertEqual({}, missing)

    def test_online_identity_mapping_is_preserved(self):
        heroes = {
            int(row.attrib["heroId"]): row.attrib
            for row in rows(self.xml_root, "hero.xml")
        }
        self.assertEqual("艾伦", heroes[1121]["heroname"])
        self.assertEqual("莱克莎", heroes[1122]["heroname"])
        self.assertEqual("雪拉", heroes[1123]["heroname"])
        self.assertEqual("莱因", heroes[1124]["heroname"])
        self.assertEqual(("1", "112101,112102,112103,112104,112105",
                          "1_1_112111|2_1_112112|3_1_112113|4_1_112114"),
                         (heroes[1121].get("staffOfficer"), heroes[1121]["passiveSkill"],
                          heroes[1121]["maxSkillBlanks"]))
        self.assertEqual((None, "112201,112202,112203,112204,112205",
                          "1_20|2_60|3_100|4_120"),
                         (heroes[1122].get("staffOfficer"), heroes[1122]["passiveSkill"],
                          heroes[1122]["maxSkillBlanks"]))
        self.assertEqual(("1", "112301,112302,112303,112304,112305",
                          "1_1_112311|2_1_112312|3_1_112313|4_1_112314"),
                         (heroes[1123].get("staffOfficer"), heroes[1123]["passiveSkill"],
                          heroes[1123]["maxSkillBlanks"]))

        skills = {
            int(row.attrib["skillId"]): row.attrib
            for row in rows(self.xml_root, "hero_skill.xml")
        }
        self.assertIn("12941_0_0.195", skills[112101]["effectList"])
        self.assertIn("12961_0_0.01", skills[112201]["effectList"])
        self.assertIn("12995_0_0.19", skills[112301]["effectList"])
        self.assertIn("12881_0_0.195", skills[112401]["effectList"])

    def test_online_identity_rise_data_is_preserved(self):
        rise_skills = {
            int(row.attrib["heroId"]): row.attrib
            for row in rows(self.xml_root, "hero_rise_skill.xml")
            if row.attrib.get("level") == "0" and int(row.attrib["heroId"]) in (1121, 1122, 1123, 1124)
        }
        self.assertEqual(("3", "11088_5000_5000"),
                         (rise_skills[1121]["type"], rise_skills[1121]["techEffect"]))
        self.assertEqual(("2", "11121_0_2|11122_5_5"),
                         (rise_skills[1122]["type"], rise_skills[1122]["techEffect"]))
        self.assertEqual(("2", "11089_4000_4000"),
                         (rise_skills[1123]["type"], rise_skills[1123]["techEffect"]))
        self.assertEqual(("1", "11088_5000_5000"),
                         (rise_skills[1124]["type"], rise_skills[1124]["techEffect"]))

    def test_online_identity_sql_migration_stays_disabled(self):
        migration = (self.repo / "sql/update/20261015_hero_1121_1124_align.sql").read_text(
            encoding="utf-8"
        )
        self.assertNotIn("UPDATE `hero`", migration)
        self.assertNotIn("UPDATE `item`", migration)

    def test_legacy_skill_slot_migration_is_enabled(self):
        player_hero = (
            self.repo / "src/main/java/com/hawk/game/player/hero/PlayerHero.java"
        ).read_text(encoding="utf-8")
        self.assertIn("LegacyHeroSkillMigration", player_hero)
        self.assertIn("migrateLegacySkillSlot", player_hero)
        self.assertTrue(
            (self.repo / "src/main/java/com/hawk/game/player/hero/LegacyHeroSkillMigration.java").exists()
        )

    def test_legacy_skill_slot_migration_behavior(self):
        production = self.repo / "src/main/java/com/hawk/game/player/hero/LegacyHeroSkillMigration.java"
        test = self.repo / "src/test/java/com/hawk/game/player/hero/LegacyHeroSkillMigrationTest.java"
        with tempfile.TemporaryDirectory() as output:
            subprocess.run(
                ["javac", "-encoding", "UTF-8", "-d", output, str(production), str(test)],
                check=True,
            )
            subprocess.run(
                ["java", "-cp", output,
                 "com.hawk.game.player.hero.LegacyHeroSkillMigrationTest"],
                check=True,
            )


if __name__ == "__main__":
    unittest.main(verbosity=2)
