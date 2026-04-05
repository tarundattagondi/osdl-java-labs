/**
 * Exercise 2: Hotel Room Booking with Serialization
 * Concept: Serializable objects, ObjectOutputStream/ObjectInputStream,
 *          persist room bookings to file, search, update, and re-serialize.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Ex2_HotelRoomSerialization {

    static class Room implements Serializable {
        private static final long serialVersionUID = 1L;

        int roomNumber;
        String roomType;
        double pricePerNight;
        boolean booked;
        String guestName;

        Room(int roomNumber, String roomType, double pricePerNight) {
            this.roomNumber = roomNumber;
            this.roomType = roomType;
            this.pricePerNight = pricePerNight;
            this.booked = false;
            this.guestName = "";
        }

        void book(String guestName) {
            this.booked = true;
            this.guestName = guestName;
        }

        void vacate() {
            this.booked = false;
            this.guestName = "";
        }

        @Override
        public String toString() {
            return String.format("Room %-4d | %-10s | Rs. %-7.0f | %-9s | %s",
                    roomNumber, roomType, pricePerNight,
                    booked ? "Booked" : "Available",
                    booked ? guestName : "-");
        }
    }

    static void serializeRooms(List<Room> rooms, String filename) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
        oos.writeObject(rooms);
        oos.close();
    }

    @SuppressWarnings("unchecked")
    static List<Room> deserializeRooms(String filename) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
        List<Room> rooms = (List<Room>) ois.readObject();
        ois.close();
        return rooms;
    }

    static void displayAll(List<Room> rooms) {
        System.out.printf("%-10s %-12s %-12s %-10s %-15s%n",
                "Room No", "Type", "Price/Night", "Status", "Guest");
        System.out.println("------------------------------------------------------------");
        for (Room r : rooms) {
            System.out.println(r);
        }
    }

    static Room findRoom(List<Room> rooms, int roomNumber) {
        for (Room r : rooms) {
            if (r.roomNumber == roomNumber) return r;
        }
        return null;
    }

    public static void main(String[] args) {
        String filename = "rooms.ser";

        try {
            // Create rooms and serialize
            List<Room> rooms = new ArrayList<>();
            rooms.add(new Room(101, "Standard", 2000));
            rooms.add(new Room(202, "Deluxe", 3500));
            rooms.add(new Room(303, "Suite", 5000));
            rooms.add(new Room(104, "Standard", 2000));

            serializeRooms(rooms, filename);
            System.out.println("Rooms serialized to " + filename + "\n");

            // Deserialize and display all
            System.out.println("===== All Rooms (deserialized) =====");
            rooms = deserializeRooms(filename);
            displayAll(rooms);

            // Search for a specific room
            System.out.println("\n===== Search: Room 202 =====");
            Room found = findRoom(rooms, 202);
            if (found != null) {
                System.out.println(found);
            } else {
                System.out.println("Room not found.");
            }

            // Book rooms
            System.out.println("\n===== Booking Rooms =====");
            Room r101 = findRoom(rooms, 101);
            if (r101 != null) {
                r101.book("Amit Sharma");
                System.out.println("Room 101 booked for Amit Sharma");
            }
            Room r303 = findRoom(rooms, 303);
            if (r303 != null) {
                r303.book("Priya Patel");
                System.out.println("Room 303 booked for Priya Patel");
            }

            // Re-serialize after updates
            serializeRooms(rooms, filename);
            System.out.println("Updated rooms re-serialized.\n");

            // Deserialize again and display
            System.out.println("===== All Rooms (after booking) =====");
            rooms = deserializeRooms(filename);
            displayAll(rooms);

            // Vacate a room
            System.out.println("\n===== Vacating Room 101 =====");
            r101 = findRoom(rooms, 101);
            if (r101 != null) {
                r101.vacate();
                System.out.println("Room 101 vacated.");
            }
            serializeRooms(rooms, filename);

            // Final state
            System.out.println("\n===== Final Room Status =====");
            rooms = deserializeRooms(filename);
            displayAll(rooms);

            // Clean up
            new File(filename).delete();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
