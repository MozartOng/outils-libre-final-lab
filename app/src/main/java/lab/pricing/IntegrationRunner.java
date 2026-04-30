package lab.pricing;

import java.util.*;

public class IntegrationRunner {
    public static void main(String[] args) throws Exception {
        List<Double> prices = parseDoubles(args[0]);
        List<Integer> quantities = parseInts(args[1]);
        String customerType = args[2];
        String discountCode = "NONE".equals(args[3]) ? null : args[3];

        Order order = new Order(prices, quantities, customerType, discountCode);
        PricingEngine engine = new PricingEngine();
        PricingResult result = engine.calculate(order);

        System.out.printf("{\"subtotal\":%.2f,\"discount\":%.2f,\"tax\":%.2f,\"total\":%.2f}%n",
            result.subtotal, result.discount, result.tax, result.total);
    }

    private static List<Double> parseDoubles(String csv) {
        List<Double> list = new ArrayList<>();
        for (String s : csv.split(",")) list.add(Double.parseDouble(s.trim()));
        return list;
    }

    private static List<Integer> parseInts(String csv) {
        List<Integer> list = new ArrayList<>();
        for (String s : csv.split(",")) list.add(Integer.parseInt(s.trim()));
        return list;
    }
}