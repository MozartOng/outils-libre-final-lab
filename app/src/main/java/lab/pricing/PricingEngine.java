package lab.pricing;

import java.util.List;

public class PricingEngine {

    public double calc(List<Double> prices, List<Integer> qtys,String custType, String code) {
                    
        double sub = 0;
        for (int i = 0; i < prices.size(); i++) {
            sub = sub + (prices.get(i) * qtys.get(i)); 

        double disc = 0;
        if (code != null) {
            if (code.equals("SAVE10")) {
                disc = sub * 0.10;
            } else if (code.equals("SAVE20")) {
                disc = sub * 0.20;
            } else if (code.equals("SAVE30")) {
                disc = sub * 0.30;
            }
        }

        if (custType != null && custType.equals("VIP")) {
            disc = disc + (sub * 0.05); // VIP gets extra 5%, but applied to original subtotal
        }

        double afterDisc = sub - disc;

        double tax = afterDisc * 0.08;

        double total = afterDisc + tax;

        System.out.println("Sub: " + sub);
        System.out.println("Disc: " + disc);
        System.out.println("Tax: " + tax);
        System.out.println("Total: " + total);

        return total;
    }
}}