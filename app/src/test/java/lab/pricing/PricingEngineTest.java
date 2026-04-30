package lab.pricing;

import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PricingEngineTest {

    private PricingEngine engine;

    @BeforeEach
    void setUp() {
        engine = new PricingEngine();
    }

    @Test
    @DisplayName("Regular customer, no discount code")
    void testRegularNoDiscount() {
        // prices: [10.0, 20.0], qtys: [2, 1] → subtotal = 40.0
        // no discount, tax 8% → total = 40.0 * 1.08 = 43.20
        double result = engine.calc(List.of(10.0, 20.0), List.of(2, 1), "REGULAR", null);
        assertEquals(43.20, result, 0.001);
    }

    @Test
    @DisplayName("Regular customer with SAVE10 discount")
    void testRegularSave10() {
        // subtotal = 100.0, discount 10% = 10.0, after = 90.0, tax = 7.2, total = 97.2
        double result = engine.calc(List.of(100.0), List.of(1), "REGULAR", "SAVE10");
        assertEquals(97.20, result, 0.001);
    }

    @Test
    @DisplayName("VIP customer with SAVE20 discount")
    void testVipSave20() {
        // subtotal = 100.0
        // SAVE20 disc = 20.0, VIP disc = 5.0 → total disc = 25.0
        // after disc = 75.0, tax = 6.0, total = 81.0
        double result = engine.calc(List.of(100.0), List.of(1), "VIP", "SAVE20");
        assertEquals(81.0, result, 0.001);
    }

    @Test
    @DisplayName("VIP customer, no discount code")
    void testVipNoDiscount() {
        // subtotal = 200.0, VIP disc = 10.0, after = 190.0, tax = 15.2, total = 205.2
        double result = engine.calc(List.of(50.0, 150.0), List.of(1, 1), "VIP", null);
        assertEquals(205.20, result, 0.001);
    }

    @Test
    @DisplayName("Unknown discount code is ignored")
    void testUnknownDiscountCode() {
        // subtotal = 50.0, unknown code → no discount, tax 8% → 54.0
        double result = engine.calc(List.of(50.0), List.of(1), "REGULAR", "BOGUS99");
        assertEquals(54.0, result, 0.001);
    }

    @Test
    @DisplayName("Single item, single quantity")
    void testSingleItem() {
        double result = engine.calc(List.of(100.0), List.of(1), "REGULAR", null);
        assertEquals(108.0, result, 0.001);
    }
}