import os
import re
import shutil
import subprocess
import unittest
from pathlib import Path


REQUIRED_EFFECTS = {
    **{f"HERO_{effect_id}": effect_id for effect_id in range(12941, 12945)},
    **{f"HERO_{effect_id}": effect_id for effect_id in range(12961, 12971)},
    "HERO_12981": 12981,
    **{f"HERO_{effect_id}": effect_id for effect_id in range(12991, 12998)},
}

LEGACY_COMPATIBILITY_EFFECTS = {
    "EFF_2211": 2211,
    "EFF_11089": 11089,
    "EFF_12773": 12773,
    "EFF_12811": 12811,
}


class HeroProtocolArtifactTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.repo = Path(__file__).resolve().parents[3]
        cls.protocol_jar = Path(
            os.environ.get("GAMEPROTOCOL_JAR", cls.repo / "lib" / "gameprotocol.jar")
        )

    def test_runtime_protocol_jar_is_present(self):
        self.assertTrue(
            self.protocol_jar.is_file(),
            "lib/gameprotocol.jar is required by the GameServer runtime manifest",
        )

    def test_required_effect_ids_are_in_runtime_enum(self):
        javap = shutil.which("javap")
        self.assertIsNotNone(javap, "a JDK with javap is required for protocol verification")
        self.assertTrue(self.protocol_jar.is_file(), "runtime protocol JAR is missing")

        result = subprocess.run(
            [
                javap,
                "-classpath",
                str(self.protocol_jar),
                "-c",
                "com.hawk.game.protocol.Const$EffType",
            ],
            check=True,
            capture_output=True,
            text=True,
        )

        for name, effect_id in REQUIRED_EFFECTS.items():
            pattern = rf"String {name}.*?sipush\s+{effect_id}.*?Field {name}:"
            self.assertRegex(result.stdout, re.compile(pattern, re.DOTALL), name)

    def test_legacy_effect_ids_remain_in_runtime_enum(self):
        javap = shutil.which("javap")
        self.assertIsNotNone(javap, "a JDK with javap is required for protocol verification")
        self.assertTrue(self.protocol_jar.is_file(), "runtime protocol JAR is missing")

        result = subprocess.run(
            [
                javap,
                "-classpath",
                str(self.protocol_jar),
                "-c",
                "com.hawk.game.protocol.Const$EffType",
            ],
            check=True,
            capture_output=True,
            text=True,
        )

        for name, effect_id in LEGACY_COMPATIBILITY_EFFECTS.items():
            pattern = rf"String {name}.*?sipush\s+{effect_id}.*?Field {name}:"
            self.assertRegex(result.stdout, re.compile(pattern, re.DOTALL), name)


if __name__ == "__main__":
    unittest.main(verbosity=2)
