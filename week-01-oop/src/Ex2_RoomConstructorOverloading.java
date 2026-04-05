/**
 * Exercise 2: Room and DeluxeRoom — Constructor Overloading and Inheritance
 * Concept: Constructor overloading, single inheritance, super keyword.
 *
 * Room has overloaded constructors (room number + type, or room number + type + price).
 * DeluxeRoom extends Room with Wi-Fi and breakfast amenities.
 */
class Room {
    protected int roomNumber;
    protected String roomType;
    protected double basePrice;

    Room(int roomNumber, String roomType) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.basePrice = 0.0;
    }

    Room(int roomNumber, String roomType, double basePrice) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.basePrice = basePrice;
    }

    void displayDetails() {
        System.out.println("Room Number : " + roomNumber);
        System.out.println("Room Type   : " + roomType);
        System.out.println("Base Price  : " + basePrice);
    }
}

class DeluxeRoom extends Room {
    private boolean freeWifi;
    private boolean complimentaryBreakfast;

    DeluxeRoom(int roomNumber, String roomType) {
        super(roomNumber, roomType);
        this.freeWifi = false;
        this.complimentaryBreakfast = false;
    }

    DeluxeRoom(int roomNumber, String roomType, double basePrice,
               boolean freeWifi, boolean complimentaryBreakfast) {
        super(roomNumber, roomType, basePrice);
        this.freeWifi = freeWifi;
        this.complimentaryBreakfast = complimentaryBreakfast;
    }

    void displayDeluxeDetails() {
        super.displayDetails();
        System.out.println("Free Wi-Fi             : " + (freeWifi ? "Yes" : "No"));
        System.out.println("Complimentary Breakfast: " + (complimentaryBreakfast ? "Yes" : "No"));
    }
}

public class Ex2_RoomConstructorOverloading {
    public static void main(String[] args) {
        // Room with two-argument constructor
        Room r1 = new Room(101, "Standard");
        System.out.println("===== Room (2-arg constructor) =====");
        r1.displayDetails();

        // Room with three-argument constructor
        Room r2 = new Room(102, "Suite", 5000.0);
        System.out.println("\n===== Room (3-arg constructor) =====");
        r2.displayDetails();

        // DeluxeRoom with two-argument constructor (defaults)
        DeluxeRoom d1 = new DeluxeRoom(201, "Deluxe");
        System.out.println("\n===== DeluxeRoom (2-arg constructor) =====");
        d1.displayDeluxeDetails();

        // DeluxeRoom with full constructor
        DeluxeRoom d2 = new DeluxeRoom(202, "Deluxe", 4500.0, true, true);
        System.out.println("\n===== DeluxeRoom (full constructor) =====");
        d2.displayDeluxeDetails();
    }
}
