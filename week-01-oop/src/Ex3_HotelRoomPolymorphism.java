/**
 * Exercise 3: Hotel Room Booking System — Runtime Polymorphism
 * Concept: Inheritance, method overriding, base class reference to derived objects.
 *
 * Room base class with calculateTariff(). StandardRoom and LuxuryRoom override it
 * with room-specific pricing. Demonstrates runtime polymorphism via base class reference.
 */
class HotelRoom {
    protected int roomNumber;
    protected double baseTariff;

    HotelRoom(int roomNumber, double baseTariff) {
        this.roomNumber = roomNumber;
        this.baseTariff = baseTariff;
    }

    double calculateTariff() {
        return baseTariff;
    }

    void displayDetails() {
        System.out.println("Room Number : " + roomNumber);
        System.out.println("Base Tariff : " + baseTariff);
    }
}

class StandardRoom extends HotelRoom {
    private boolean hasAC;

    StandardRoom(int roomNumber, double baseTariff, boolean hasAC) {
        super(roomNumber, baseTariff);
        this.hasAC = hasAC;
    }

    @Override
    double calculateTariff() {
        double tariff = baseTariff;
        if (hasAC) {
            tariff += 500.0; // AC surcharge
        }
        return tariff;
    }

    @Override
    void displayDetails() {
        super.displayDetails();
        System.out.println("Room Type   : Standard");
        System.out.println("AC          : " + (hasAC ? "Yes" : "No"));
        System.out.println("Total Tariff: " + calculateTariff());
    }
}

class LuxuryRoom extends HotelRoom {
    private double amenityCharge;
    private double premiumServiceCharge;

    LuxuryRoom(int roomNumber, double baseTariff, double amenityCharge, double premiumServiceCharge) {
        super(roomNumber, baseTariff);
        this.amenityCharge = amenityCharge;
        this.premiumServiceCharge = premiumServiceCharge;
    }

    @Override
    double calculateTariff() {
        return baseTariff + amenityCharge + premiumServiceCharge;
    }

    @Override
    void displayDetails() {
        super.displayDetails();
        System.out.println("Room Type             : Luxury");
        System.out.println("Amenity Charge        : " + amenityCharge);
        System.out.println("Premium Service Charge: " + premiumServiceCharge);
        System.out.println("Total Tariff          : " + calculateTariff());
    }
}

public class Ex3_HotelRoomPolymorphism {
    public static void main(String[] args) {
        // Base class references pointing to derived class objects
        HotelRoom room1 = new StandardRoom(301, 2000.0, true);
        HotelRoom room2 = new StandardRoom(302, 2000.0, false);
        HotelRoom room3 = new LuxuryRoom(501, 5000.0, 1500.0, 2000.0);

        HotelRoom[] rooms = {room1, room2, room3};

        System.out.println("===== Hotel Room Tariff Report =====\n");
        for (HotelRoom room : rooms) {
            // Runtime polymorphism: correct calculateTariff() called based on actual object
            room.displayDetails();
            System.out.println("Tariff (via polymorphism): " + room.calculateTariff());
            System.out.println("-----------------------------------");
        }
    }
}
