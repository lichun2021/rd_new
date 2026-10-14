import re
import unittest
import xml.etree.ElementTree as ET
from pathlib import Path


HEROES = {1125: "墨明", 2002: "谭雅"}
DRESS_IDS = (
    2381, 2382, 2383, 2384, 2385,  # 城堡装扮
    1158, 1159,                    # 铭牌
    2502, 2503,                    # 签名
    1094, 1095, 1096, 1097,        # 称号
    534,                           # 挂件
    652, 653, 654, 655, 656,       # 护卫
    357, 359, 360, 361,            # 行军
)
HOMELAND_BUILD_TYPES = tuple(range(1028, 1055))
EXPECTED_HERO_ROWS = {
    "hero.xml": 1,
    "hero_collect.xml": 1,
    "hero_star_level.xml": 21,
    "hero_skill.xml": 5,
    "hero_soul_stage.xml": 6,
    "hero_soul_skill.xml": 6,
    "hero_soul_level.xml": 300,
}


def rows(xml_root: Path, name: str):
    return ET.parse(xml_root / name).getroot().findall("data")


def effect_ids(value: str):
    for item in re.split(r"[|,]", value or ""):
        if item:
            yield int(item.split("_", 1)[0])


def item_ids(value: str):
    return {int(m) for m in re.findall(r"30000_(\d+)_", value or "")}


class Client1111ConfigClosureTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.repo = Path(__file__).resolve().parents[3]
        cls.xml_root = cls.repo / "xml"
        cls.items = {int(r.attrib["id"]) for r in rows(cls.xml_root, "item.xml")}
        cls.effects = {int(r.attrib["id"]) for r in rows(cls.xml_root, "effectid.xml")}

    def test_new_heroes_have_complete_rows(self):
        for name, expected in EXPECTED_HERO_ROWS.items():
            for hero_id in HEROES:
                count = 0
                for row in rows(self.xml_root, name):
                    attrs = row.attrib
                    if name == "hero_skill.xml":
                        owner = int(attrs["skillId"]) // 100
                    elif name.startswith("hero_soul_"):
                        owner = int(attrs["hero"])
                    else:
                        owner = int(attrs["heroId"])
                    count += owner == hero_id
                self.assertEqual(expected, count, f"{name} hero {hero_id}")

    def test_new_hero_items_and_effects_resolve(self):
        for row in rows(self.xml_root, "hero.xml"):
            if int(row.attrib["heroId"]) in HEROES:
                for field in ("unlockPieces", "unlockCard"):
                    self.assertTrue(item_ids(row.attrib[field]) <= self.items, row.attrib[field])
        referenced = set()
        for row in rows(self.xml_root, "hero_skill.xml"):
            if int(row.attrib["skillId"]) // 100 in HEROES:
                referenced.update(effect_ids(row.attrib.get("effectList")))
                referenced.update(effect_ids(row.attrib.get("proficiencyEffect")))
        for row in rows(self.xml_root, "hero_soul_skill.xml"):
            if int(row.attrib["hero"]) in HEROES:
                referenced.update(effect_ids(row.attrib.get("attr")))
        self.assertEqual(set(), referenced - self.effects)

    def test_dresses_have_model_tool_and_coupon(self):
        models = {int(r.attrib["dressId"]) for r in rows(self.xml_root, "world_dress_model.xml")}
        tools = rows(self.xml_root, "world_dress.xml")
        for dress_id in DRESS_IDS:
            self.assertIn(dress_id, models, f"world_dress_model {dress_id}")
            own = [t for t in tools if int(t.attrib["dressId"]) == dress_id]
            self.assertTrue(own, f"world_dress {dress_id}")
            for tool in own:
                self.assertIn(int(tool.attrib["item"]), self.items, f"coupon item for {tool.attrib['id']}")

    def test_dress_sets_reference_existing_dresses(self):
        models = {int(r.attrib["dressId"]) for r in rows(self.xml_root, "world_dress_model.xml")}
        for row in rows(self.xml_root, "world_dress_group.xml"):
            ids = {int(x) for x in row.attrib["dressIds"].split(",")}
            self.assertEqual(set(), ids - models, f"group {row.attrib['groupId']}")

    def test_luxury_dress_and_commander_image(self):
        highend = {int(r.attrib["id"]): r.attrib for r in rows(self.xml_root, "world_highend_dress.xml")}
        skills = {int(r.attrib["id"]) for r in rows(self.xml_root, "skill.xml")}
        self.assertEqual("2382", highend[1002]["dressId"])
        for key in ("luxuryPassiveSkills", "luxuryActiveSkills"):
            self.assertIn(int(highend[1002][key]), skills)
        shows = {int(r.attrib["id"]): r.attrib for r in rows(self.xml_root, "player_show.xml")}
        self.assertEqual("2382000", shows[701022]["luxuryDressID"])
        tools = {int(r.attrib["id"]) for r in rows(self.xml_root, "world_dress.xml")}
        self.assertIn(2382000, tools)

    def test_homeland_buildings_resolve(self):
        types = {int(r.attrib["buildType"]) for r in rows(self.xml_root, "homeland_build_type.xml")}
        builds = rows(self.xml_root, "homeland_build.xml")
        for build_type in HOMELAND_BUILD_TYPES:
            self.assertIn(build_type, types)
            own = [b for b in builds if int(b.attrib["buildType"]) == build_type]
            self.assertEqual(12, len(own), f"homeland_build type {build_type}")
            for build in own:
                for field in ("buildCost", "buildSplit", "buildRecovery"):
                    self.assertTrue(item_ids(build.attrib.get(field)) <= self.items, f"{build.attrib['buildId']} {field}")
                self.assertTrue(set(effect_ids(build.attrib.get("attr"))) <= self.effects, build.attrib["buildId"])
        for row in rows(self.xml_root, "homeland_recovery_shop.xml"):
            self.assertTrue(item_ids(row.attrib["gainItem"]) <= self.items, row.attrib["id"])


if __name__ == "__main__":
    unittest.main(verbosity=2)
