package lab.pricing;

import java.util.Map;

public class DiscountService {

    private static final Map<String, Double> CODE_RATES = Map.of(
        "SAVE10", 0.10,
        "SAVE20", 0.20,
        "SAVE30", 0.30
    );
    private static final double VIP_RATE = 0.05;

    public double calculateDiscount(Order order) {
        double subtotal = order.subtotal();
        double discount = codeDiscount(subtotal, order.getDiscountCode());
        discount += vipDiscount(subtotal, order.getCustomerType());
        return discount;
    }

    private double codeDiscount(double subtotal, String code) {
        if (code == null) return 0;
        return subtotal * CODE_RATES.getOrDefault(code, 0.0);
    }

    private double vipDiscount(double subtotal, String customerType) {
        return "VIP".equals(customerType) ? subtotal * VIP_RATE : 0;
    }
}