/**
 * Exercise 1: Hotel Billing System using Wrapper Classes
 * Concept: Wrapper classes, autoboxing, unboxing, arithmetic with wrappers.
 *
 * Calculate the total hotel bill using wrapper class objects (Integer, Double)
 * instead of primitives. Demonstrate autoboxing when assigning values and
 * unboxing when performing arithmetic for bill calculation.
 */
public class Ex1_HotelBillingWrappers {
    public static void main(String[] args) {

        // Initialize room tariff and days using primitives
        double roomTariffPrimitive = 3500.0;
        int daysStayedPrimitive = 4;
        double serviceChargePrimitive = 1200.0;

        // Autoboxing: primitives stored in wrapper objects
        Double roomTariff = roomTariffPrimitive;
        Integer daysStayed = daysStayedPrimitive;
        Double serviceCharge = serviceChargePrimitive;

        System.out.println("===== Hotel Billing System =====");
        System.out.println("Guest Name       : Vikram Mehta");
        System.out.println("Room Type        : Deluxe");
        System.out.println("Room Tariff/Night: " + roomTariff);
        System.out.println("Days Stayed      : " + daysStayed);
        System.out.println("Service Charge   : " + serviceCharge);

        // Unboxing: wrapper objects converted back to primitives for arithmetic
        double roomTotal = roomTariff * daysStayed;
        double gst = roomTotal * 0.18;
        double totalBill = roomTotal + serviceCharge + gst;

        // Autoboxing the results back into wrapper objects
        Double roomTotalWrapper = roomTotal;
        Double gstWrapper = gst;
        Double totalBillWrapper = totalBill;

        System.out.println("\n----- Bill Breakdown -----");
        System.out.println("Room Charges     : " + roomTariffPrimitive + " x " + daysStayedPrimitive + " = " + roomTotalWrapper);
        System.out.println("Service Charge   : " + serviceCharge);
        System.out.println("GST (18%)        : " + String.format("%.2f", gstWrapper));
        System.out.println("---------------------------");
        System.out.println("Total Bill       : " + String.format("%.2f", totalBillWrapper));
    }
}
