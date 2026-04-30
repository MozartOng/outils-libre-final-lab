import subprocess
import sys
import unittest
import json
from pathlib import Path

SCRIPT_DIR = Path(__file__).resolve().parent
APP_ROOT = SCRIPT_DIR.parents[1]
JAR_PATH = APP_ROOT / "build" / "libs" / "app-1.0-SNAPSHOT.jar"
JAVA_CMD = "java"
RUNNER_CLASS = "lab.pricing.IntegrationRunner"


def run_pricing(prices, quantities, customer_type, discount_code=None):
    """Invoke the pricing engine JAR and return parsed output."""
    args = [
        JAVA_CMD, "-cp", str(JAR_PATH),
        RUNNER_CLASS,
        ",".join(str(p) for p in prices),
        ",".join(str(q) for q in quantities),
        customer_type,
        discount_code or "NONE",
    ]
    result = subprocess.run(args, capture_output=True, text=True, timeout=10)
    if result.returncode != 0:
        raise RuntimeError(f"Java process failed:\n{result.stderr}")
    return json.loads(result.stdout.strip())


class PricingIntegrationTests(unittest.TestCase):

    def test_regular_no_discount(self):
        r = run_pricing([10.0, 20.0], [2, 1], "REGULAR")
        self.assertAlmostEqual(r["subtotal"], 40.0, places=2)
        self.assertAlmostEqual(r["discount"], 0.0,  places=2)
        self.assertAlmostEqual(r["total"],    43.2,  places=2)

    def test_vip_save20(self):
        r = run_pricing([100.0], [1], "VIP", "SAVE20")
        self.assertAlmostEqual(r["subtotal"], 100.0, places=2)
        self.assertAlmostEqual(r["discount"],  25.0, places=2)
        self.assertAlmostEqual(r["total"],     81.0, places=2)

    def test_unknown_code_ignored(self):
        r = run_pricing([50.0], [1], "REGULAR", "FAKE")
        self.assertAlmostEqual(r["discount"], 0.0,  places=2)
        self.assertAlmostEqual(r["total"],    54.0, places=2)

    def test_save30(self):
        r = run_pricing([100.0], [1], "REGULAR", "SAVE30")
        self.assertAlmostEqual(r["total"], 75.6, places=2)


if __name__ == "__main__":
    if not JAR_PATH.exists():
        print(f"ERROR: JAR not found at {JAR_PATH}. Run cd {APP_ROOT} && ./gradlew jar first.")
        sys.exit(1)
    unittest.main(verbosity=2)