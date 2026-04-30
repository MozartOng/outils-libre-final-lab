package lab.pricing;

import java.util.List;

public class Order {
    private final List<Double> prices;
    private final List<Integer> quantities;
    private final String customerType;
    private final String discountCode;

    public Order(List<Double> prices, List<Integer> quantities,
                 String customerType, String discountCode) {
        if (prices == null || quantities == null)
            throw new IllegalArgumentException("Prices and quantities cannot be null");
        if (prices.size() != quantities.size())
            throw new IllegalArgumentException("Prices and quantities must be the same length");
        this.prices = List.copyOf(prices);
        this.quantities = List.copyOf(quantities);
        this.customerType = customerType != null ? customerType : "REGULAR";
        this.discountCode = discountCode;
    }

    public double subtotal() {
        double sum = 0;
        for (int i = 0; i < prices.size(); i++) {
            sum += prices.get(i) * quantities.get(i);
        }
        return sum;
    }

    public String getCustomerType() { return customerType; }
    public String getDiscountCode() { return discountCode; }
}