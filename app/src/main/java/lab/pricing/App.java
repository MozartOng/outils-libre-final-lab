package lab.pricing;

import java.util.List;

public class App {
    public static void main(String[] args) {
        PricingEngine engine = new PricingEngine();
        engine.calc(List.of(10.0, 20.0), List.of(2, 1), "VIP", "SAVE10");
    }
}