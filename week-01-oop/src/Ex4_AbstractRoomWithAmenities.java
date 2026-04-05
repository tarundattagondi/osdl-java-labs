/**
 * Exercise 4: Abstract Class and Interface
 * Concept: Abstraction via abstract class, interface-based design.
 *
 * Abstract Room class with abstract calculateTariff() and concrete displayRoomDetails().
 * Amenities interface declares provideWifi() and provideBreakfast().
 * StandardRoom and LuxuryRoom extend Room and implement Amenities.
 */
abstract class AbstractRoom {
    protected int roomNumber;
    protected double basePrice;

    AbstractRoom(int roomNumber, double basePrice) {
        this.roomNumber = roomNumber;
        this.basePrice = basePrice;
    }

    abstract double calculateTariff();

    void displayRoomDetails() {
        System.out.println("Room Number : " + roomNumber);
        System.out.println("Base Price  : " + basePrice);
    }
}

interface Amenities {
    void provideWifi();
    void provideBreakfast();
}

class StandardHotelRoom extends AbstractRoom implements Amenities {
    StandardHotelRoom(int roomNumber, double basePrice) {
        super(roomNumber, basePrice);
    }

    @Override
    double calculateTariff() {
        return basePrice;
    }

    @Override
    public void provideWifi() {
        System.out.println("Wi-Fi       : Basic (shared, limited speed)");
    }

    @Override
    public void provideBreakfast() {
        System.out.println("Breakfast   : Continental breakfast included");
    }
}

class LuxuryHotelRoom extends AbstractRoom implements Amenities {
    private double premiumCharge;

    LuxuryHotelRoom(int roomNumber, double basePrice, double premiumCharge) {
        super(roomNumber, basePrice);
        this.premiumCharge = premiumCharge;
    }

    @Override
    double calculateTariff() {
        return basePrice + premiumCharge;
    }

    @Override
    public void provideWifi() {
        System.out.println("Wi-Fi       : High-speed dedicated connection");
    }

    @Override
    public void provideBreakfast() {
        System.out.println("Breakfast   : Full buffet with room service option");
    }
}

public class Ex4_AbstractRoomWithAmenities {
    public static void main(String[] args) {
        // Base class references to derived objects
        AbstractRoom room1 = new StandardHotelRoom(101, 2000.0);
        AbstractRoom room2 = new LuxuryHotelRoom(501, 5000.0, 3000.0);

        AbstractRoom[] rooms = {room1, room2};

        System.out.println("===== Hotel Room Details with Amenities =====\n");
        for (AbstractRoom room : rooms) {
            room.displayRoomDetails();
            System.out.println("Total Tariff: " + room.calculateTariff());

            // Access interface methods via casting
            if (room instanceof Amenities) {
                Amenities amenities = (Amenities) room;
                amenities.provideWifi();
                amenities.provideBreakfast();
            }
            System.out.println("----------------------------------------------");
        }
    }
}
