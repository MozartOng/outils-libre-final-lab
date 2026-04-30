package lab.pricing;

public class TaxService {

    private static final double TAX_RATE = 0.08;

    public double calculateTax(double amountAfterDiscount) {
        if (amountAfterDiscount < 0)
            throw new IllegalArgumentException("Amount after discount cannot be negative");
        return amountAfterDiscount * TAX_RATE;
    }
}