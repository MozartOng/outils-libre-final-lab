package lab.pricing;

public class PricingResult {
    public final double subtotal;
    public final double discount;
    public final double tax;
    public final double total;

    public PricingResult(double subtotal, double discount, double tax) {
        this.subtotal = subtotal;
        this.discount = discount;
        this.tax = tax;
        this.total = subtotal - discount + tax;
    }

    @Override
    public String toString() {
        return String.format(
            "Subtotal: %.2f | Discount: %.2f | Tax: %.2f | Total: %.2f",
            subtotal, discount, tax, total
        );
    }
}