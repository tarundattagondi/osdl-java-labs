/**
 * Exercise 3: Bounded type parameters for room charge calculations
 * Concept: <T extends Number> ensures only numeric types for pricing/discounts.
 */
class RoomChargeCalculator<T extends Number> {
    private T roomPrice;
    private T discountPercent;

    public RoomChargeCalculator(T roomPrice, T discountPercent) {
        this.roomPrice = roomPrice;
        this.discountPercent = discountPercent;
    }

    public double getTotalPrice() {
        return roomPrice.doubleValue();
    }

    public double getDiscountedPrice() {
        double price = roomPrice.doubleValue();
        double discount = discountPercent.doubleValue();
        return price - (price * discount / 100.0);
    }
}

public class Ex3_BoundedRoomCharges {
    public static void main(String[] args) {
        System.out.println("===== Room Charge Calculator =====\n");

        // Integer values
        RoomChargeCalculator<Integer> standard = new RoomChargeCalculator<>(2000, 10);
        System.out.println("Standard Room (Integer):");
        System.out.println("  Base Price      : Rs. " + standard.getTotalPrice());
        System.out.println("  Discounted Price: Rs. " + standard.getDiscountedPrice());

        // Double values
        RoomChargeCalculator<Double> deluxe = new RoomChargeCalculator<>(3500.0, 15.5);
        System.out.println("\nDeluxe Room (Double):");
        System.out.println("  Base Price      : Rs. " + deluxe.getTotalPrice());
        System.out.println("  Discounted Price: Rs. " + String.format("%.2f", deluxe.getDiscountedPrice()));

        // Float values
        RoomChargeCalculator<Float> suite = new RoomChargeCalculator<>(5000.0f, 20.0f);
        System.out.println("\nSuite (Float):");
        System.out.println("  Base Price      : Rs. " + suite.getTotalPrice());
        System.out.println("  Discounted Price: Rs. " + String.format("%.2f", suite.getDiscountedPrice()));

        // Compile-time error if uncommented:
        // RoomChargeCalculator<String> invalid = new RoomChargeCalculator<>("high", "low");
    }
}
