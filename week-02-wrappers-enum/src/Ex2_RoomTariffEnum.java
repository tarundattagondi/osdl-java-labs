/**
 * Exercise 2: Room Tariff Management using Enumerations
 * Concept: Enum constants, enum constructors, enum methods.
 *
 * Define an enum RoomCategory with STANDARD, DELUXE, and SUITE, each
 * associated with a base tariff via an enum constructor. Includes methods
 * to return the base tariff and compute the total cost for a given stay.
 */

enum RoomCategory {
    STANDARD(2000),
    DELUXE(3500),
    SUITE(5000);

    private final int baseTariff;

    RoomCategory(int baseTariff) {
        this.baseTariff = baseTariff;
    }

    public int getBaseTariff() {
        return baseTariff;
    }

    public int calculateTotalCost(int nights) {
        return baseTariff * nights;
    }
}

public class Ex2_RoomTariffEnum {
    public static void main(String[] args) {

        System.out.println("===== Hotel Room Tariff Schedule =====\n");

        // Display all room types and their base tariffs
        for (RoomCategory category : RoomCategory.values()) {
            System.out.println(category + " : Rs. " + category.getBaseTariff() + " per night");
        }

        // Select a room and compute cost
        RoomCategory selected = RoomCategory.SUITE;
        int nights = 5;

        System.out.println("\n----- Booking Details -----");
        System.out.println("Guest Name      : Meera Kapoor");
        System.out.println("Room Category   : " + selected);
        System.out.println("Base Tariff     : Rs. " + selected.getBaseTariff());
        System.out.println("Number of Nights: " + nights);
        System.out.println("Total Room Cost : Rs. " + selected.calculateTotalCost(nights));

        // Second booking
        RoomCategory selected2 = RoomCategory.STANDARD;
        int nights2 = 2;

        System.out.println("\n----- Booking Details -----");
        System.out.println("Guest Name      : Arjun Reddy");
        System.out.println("Room Category   : " + selected2);
        System.out.println("Base Tariff     : Rs. " + selected2.getBaseTariff());
        System.out.println("Number of Nights: " + nights2);
        System.out.println("Total Room Cost : Rs. " + selected2.calculateTotalCost(nights2));
    }
}
