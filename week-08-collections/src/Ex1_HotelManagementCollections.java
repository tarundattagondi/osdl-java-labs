/**
 * Exercise 1: Hotel Management System using Collection Framework
 * Concept: ArrayList, HashMap, Iterator, Collections.sort(), menu-driven console UI.
 *
 * Manages rooms, customers, bookings, and checkouts. No arrays — all data in Collections.
 * Runs a non-interactive demo that exercises every menu option with sample hotel data.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collections;
import java.util.Comparator;

public class Ex1_HotelManagementCollections {

    // ---- Room ----
    static class Room {
        int roomNumber;
        String roomType;
        double pricePerDay;
        boolean available;

        Room(int roomNumber, String roomType, double pricePerDay) {
            this.roomNumber = roomNumber;
            this.roomType = roomType;
            this.pricePerDay = pricePerDay;
            this.available = true;
        }

        @Override
        public String toString() {
            return String.format("Room %-4d | %-8s | Rs. %-7.0f | %s",
                    roomNumber, roomType, pricePerDay,
                    available ? "Available" : "Occupied");
        }
    }

    // ---- Customer ----
    static class Customer {
        int customerId;
        String name;
        String contactNumber;
        int allocatedRoom;

        Customer(int customerId, String name, String contactNumber, int allocatedRoom) {
            this.customerId = customerId;
            this.name = name;
            this.contactNumber = contactNumber;
            this.allocatedRoom = allocatedRoom;
        }

        @Override
        public String toString() {
            return String.format("ID %-4d | %-18s | %-12s | Room %d",
                    customerId, name, contactNumber, allocatedRoom);
        }
    }

    // ---- Data stores ----
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Customer> customers = new ArrayList<>();
    static HashMap<Integer, Customer> roomCustomerMap = new HashMap<>();
    static int nextCustomerId = 1;

    // ---- Operations ----

    static void addRoom(int roomNumber, String roomType, double price) {
        rooms.add(new Room(roomNumber, roomType, price));
        System.out.println("  Added: Room " + roomNumber + " (" + roomType + ")");
    }

    static void displayAllRooms() {
        System.out.println("  Room No  | Type     | Price/Day | Status");
        System.out.println("  ------------------------------------------------");
        for (Room r : rooms) {
            System.out.println("  " + r);
        }
    }

    static void displayAvailableRooms() {
        System.out.println("  Available Rooms:");
        Iterator<Room> it = rooms.iterator();
        boolean found = false;
        while (it.hasNext()) {
            Room r = it.next();
            if (r.available) {
                System.out.println("  " + r);
                found = true;
            }
        }
        if (!found) System.out.println("  No rooms available.");
    }

    static void sortRoomsByPrice() {
        Collections.sort(rooms, Comparator.comparingDouble(r -> r.pricePerDay));
        System.out.println("  Rooms sorted by price (ascending).");
    }

    static void bookRoom(int roomNumber, String guestName, String contact) {
        Room target = findRoom(roomNumber);
        if (target == null) {
            System.out.println("  Room " + roomNumber + " does not exist.");
            return;
        }
        if (!target.available) {
            System.out.println("  Room " + roomNumber + " is already occupied.");
            return;
        }

        target.available = false;
        Customer c = new Customer(nextCustomerId++, guestName, contact, roomNumber);
        customers.add(c);
        roomCustomerMap.put(roomNumber, c);
        System.out.println("  Booked Room " + roomNumber + " for " + guestName
                + " (Customer ID: " + c.customerId + ")");
    }

    static void checkoutRoom(int roomNumber) {
        Room target = findRoom(roomNumber);
        if (target == null) {
            System.out.println("  Room " + roomNumber + " does not exist.");
            return;
        }
        if (target.available) {
            System.out.println("  Room " + roomNumber + " is not occupied.");
            return;
        }

        target.available = true;
        Customer c = roomCustomerMap.remove(roomNumber);
        if (c != null) {
            customers.remove(c);
            System.out.println("  Checked out " + c.name + " from Room " + roomNumber);
        }
    }

    static void displayAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("  No customers currently checked in.");
            return;
        }
        System.out.println("  ID     | Name               | Contact      | Room");
        System.out.println("  --------------------------------------------------------");
        for (Customer c : customers) {
            System.out.println("  " + c);
        }
    }

    static Room findRoom(int roomNumber) {
        for (Room r : rooms) {
            if (r.roomNumber == roomNumber) return r;
        }
        return null;
    }

    // ---- Main: non-interactive demo exercising all operations ----
    public static void main(String[] args) {

        System.out.println("============================================");
        System.out.println("   Hotel Management System (Collections)");
        System.out.println("============================================\n");

        // 1. Add Rooms
        System.out.println("[1] Add Rooms");
        addRoom(101, "Single", 1500);
        addRoom(202, "Double", 2500);
        addRoom(303, "Deluxe", 3500);
        addRoom(404, "Suite", 5000);
        addRoom(105, "Single", 1500);
        addRoom(206, "Double", 2500);

        // 2. Display All Rooms
        System.out.println("\n[2] All Rooms");
        displayAllRooms();

        // 3. Sort by price
        System.out.println("\n[3] Sort Rooms by Price");
        sortRoomsByPrice();
        displayAllRooms();

        // 4. Display Available Rooms
        System.out.println("\n[4] Available Rooms");
        displayAvailableRooms();

        // 5. Book Rooms
        System.out.println("\n[5] Book Rooms");
        bookRoom(101, "Amit Sharma", "9876543210");
        bookRoom(303, "Priya Patel", "9123456789");
        bookRoom(404, "Rahul Verma", "9988776655");
        bookRoom(303, "Neha Kapoor", "9111222333"); // already occupied

        // 6. Display All Customers
        System.out.println("\n[6] All Customers");
        displayAllCustomers();

        // 7. Display Available Rooms after bookings
        System.out.println("\n[7] Available Rooms (after bookings)");
        displayAvailableRooms();

        // 8. Checkout
        System.out.println("\n[8] Checkout Room 303");
        checkoutRoom(303);

        // 9. Verify checkout
        System.out.println("\n[9] All Customers (after checkout)");
        displayAllCustomers();

        System.out.println("\n[10] Available Rooms (after checkout)");
        displayAvailableRooms();

        // 11. Attempt to book now-available room
        System.out.println("\n[11] Book Room 303 for new guest");
        bookRoom(303, "Vikram Singh", "9000111222");

        // 12. Final state
        System.out.println("\n[12] Final Room Status");
        displayAllRooms();

        System.out.println("\n[13] Final Customer List");
        displayAllCustomers();

        System.out.println("\n============================================");
        System.out.println("   Demo complete.");
        System.out.println("============================================");
    }
}
