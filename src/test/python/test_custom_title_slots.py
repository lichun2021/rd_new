from pathlib import Path
import unittest
import xml.etree.ElementTree as ET


SERVER_ROOT = Path(__file__).resolve().parents[3]
TITLE_IDS = tuple(range(1057, 1067))
MODEL_TYPES = tuple(range(344, 354))
ITEM_IDS = tuple(range(13057000, 13067000, 1000))
GAIN_ATTRIBUTE = "201_20000,4101_20000"


class CustomTitleSlotsTest(unittest.TestCase):
    def test_server_title_items_activate_ten_permanent_static_titles(self):
        models = {
            int(row.attrib["dressId"]): row.attrib
            for row in ET.parse(SERVER_ROOT / "xml" / "world_dress_model.xml").getroot().findall("data")
        }
        tools = {
            int(row.attrib["id"]): row.attrib
            for row in ET.parse(SERVER_ROOT / "xml" / "world_dress.xml").getroot().findall("data")
        }
        items = {
            int(row.attrib["id"]): row.attrib
            for row in ET.parse(SERVER_ROOT / "xml" / "item.xml").getroot().findall("data")
        }

        for slot, (dress_id, model_type, item_id) in enumerate(
            zip(TITLE_IDS, MODEL_TYPES, ITEM_IDS), start=1
        ):
            with self.subTest(slot=slot):
                model = models[dress_id]
                self.assertEqual("7", model["dressType"])
                self.assertEqual(str(model_type), model["modelType"])
                self.assertEqual(GAIN_ATTRIBUTE, model["gainAttribute"])

                tool_id = dress_id * 1000
                tool = tools[tool_id]
                self.assertEqual(str(dress_id), tool["dressId"])
                self.assertEqual("0", tool["continueTime"])
                self.assertEqual(str(item_id), tool["item"])

                item = items[item_id]
                self.assertEqual("141", item["itemType"])
                self.assertEqual(str(tool_id), item["dressId"])
                self.assertEqual("1", item["use"])


if __name__ == "__main__":
    unittest.main()
