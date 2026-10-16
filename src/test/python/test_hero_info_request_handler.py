import re
import unittest
from pathlib import Path


class HeroInfoRequestHandlerTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        repo = Path(__file__).resolve().parents[3]
        cls.source = (
            repo
            / "src"
            / "main"
            / "java"
            / "com"
            / "hawk"
            / "game"
            / "module"
            / "PlayerHeroModule.java"
        ).read_text(encoding="utf-8")

    def handler_source(self):
        match = re.search(
            r"private void onHeroInfoRequest\(HawkProtocol protocol\)\s*\{(.*?)\n\t\}",
            self.source,
            re.DOTALL,
        )
        self.assertIsNotNone(match, "hero info request handler is missing")
        return match.group(1)

    def test_request_payload_is_parsed(self):
        handler = self.handler_source()
        self.assertIn(
            "protocol.parseProtocol(PBHeroInfoRequest.getDefaultInstance())",
            handler,
        )

    def test_requested_player_and_hero_are_used(self):
        handler = self.handler_source()
        self.assertRegex(handler, r"heroId\s*=\s*req\.getHeroId\(\)")
        self.assertRegex(
            handler,
            r"playerId\s*=\s*String\.valueOf\(req\.getPlayerId\(\)\)",
        )
        self.assertIn("makesurePlayer(playerId)", handler)
        self.assertIn("targetPlayer.getHeroByCfgId(heroId)", handler)
        self.assertNotRegex(handler, r"heroId\s*=\s*0\s*;")
        self.assertNotRegex(handler, r'playerId\s*=\s*""\s*;')


if __name__ == "__main__":
    unittest.main(verbosity=2)
