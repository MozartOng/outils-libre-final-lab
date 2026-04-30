package lab.pricing;

import java.util.List;

public class PricingEngine {

    private final DiscountService discountService;
    private final TaxService taxService;

    public PricingEngine() {
        this(new DiscountService(), new TaxService());
    }

    // Constructor injection (testability)
    public PricingEngine(DiscountService discountService, TaxService taxService) {
        this.discountService = discountService;
        this.taxService = taxService;
    }

    public PricingResult calculate(Order order) {
        double subtotal = order.subtotal();
        double discount = discountService.calculateDiscount(order);
        double tax = taxService.calculateTax(subtotal - discount);
        return new PricingResult(subtotal, discount, tax);
    }

    public double calc(List<Double> prices, List<Integer> qtys, String type, String code) {
        return calculate(new Order(prices, qtys, type, code)).total;
    }
}