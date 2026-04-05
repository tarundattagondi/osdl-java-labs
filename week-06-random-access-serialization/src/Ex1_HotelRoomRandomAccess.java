/**
 * Exercise 1: Hotel Room Management with RandomAccessFile
 * Concept: Fixed-length records, seek() for direct access, add/view/update operations.
 *
 * Each room record has a fixed size:
 *   Room Number (int, 4 bytes)
 *   Room Type   (fixed 20-char UTF string, 2 + 20*2 = 42 bytes written via helper)
 *   Price/Night (double, 8 bytes)
 *   Booked      (boolean, 1 byte)
 * We use a fixed-size write approach to keep record length constant.
 */
import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.File;

public class Ex1_HotelRoomRandomAccess {

    // Fixed record size: 4 (int) + 42 (padded UTF) + 8 (double) + 1 (boolean) = 55 bytes
    static final int TYPE_LENGTH = 20; // chars for room type
    static final int RECORD_SIZE = 4 + (2 + TYPE_LENGTH * 2) + 8 + 1; // 55 bytes

    static void writeFixedString(RandomAccessFile raf, String s, int length) throws IOException {
        // Pad or truncate to exactly 'length' characters, then write as modified UTF
        StringBuilder sb = new StringBuilder(s);
        sb.setLength(length); // pads with null chars if shorter, truncates if longer
        raf.writeUTF(sb.toString());
    }

    static String readFixedString(RandomAccessFile raf, int length) throws IOException {
        String s = raf.readUTF();
        return s.trim().replace("\0", "");
    }

    static void addRoom(String filename, int roomNumber, String roomType,
                         double pricePerNight, boolean booked) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(filename, "rw");
        raf.seek(raf.length()); // append at end
        raf.writeInt(roomNumber);
        writeFixedString(raf, roomType, TYPE_LENGTH);
        raf.writeDouble(pricePerNight);
        raf.writeBoolean(booked);
        raf.close();
    }

    static void displayRoom(String filename, int targetRoom) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(filename, "r");
        boolean found = false;

        while (raf.getFilePointer() < raf.length()) {
            int roomNum = raf.readInt();
            String type = readFixedString(raf, TYPE_LENGTH);
            double price = raf.readDouble();
            boolean booked = raf.readBoolean();

            if (roomNum == targetRoom) {
                System.out.println("Room Number : " + roomNum);
                System.out.println("Room Type   : " + type);
                System.out.println("Price/Night : Rs. " + price);
                System.out.println("Status      : " + (booked ? "Booked" : "Available"));
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Room " + targetRoom + " not found.");
        }
        raf.close();
    }

    static void updateBookingStatus(String filename, int targetRoom, boolean newStatus)
            throws IOException {
        RandomAccessFile raf = new RandomAccessFile(filename, "rw");
        boolean found = false;

        while (raf.getFilePointer() < raf.length()) {
            long recordStart = raf.getFilePointer();
            int roomNum = raf.readInt();
            readFixedString(raf, TYPE_LENGTH); // skip type
            raf.readDouble();                  // skip price

            if (roomNum == targetRoom) {
                // We are now at the boolean field — overwrite it
                raf.writeBoolean(newStatus);
                System.out.println("Room " + targetRoom + " status updated to "
                        + (newStatus ? "Booked" : "Available"));
                found = true;
                break;
            } else {
                raf.readBoolean(); // skip boolean to move to next record
            }
        }

        if (!found) {
            System.out.println("Room " + targetRoom + " not found.");
        }
        raf.close();
    }

    static void displayAllRooms(String filename) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(filename, "r");
        System.out.printf("%-10s %-15s %-12s %-10s%n", "Room No", "Type", "Price/Night", "Status");
        System.out.println("------------------------------------------------");

        while (raf.getFilePointer() < raf.length()) {
            int roomNum = raf.readInt();
            String type = readFixedString(raf, TYPE_LENGTH);
            double price = raf.readDouble();
            boolean booked = raf.readBoolean();
            System.out.printf("%-10d %-15s Rs. %-8.0f %-10s%n",
                    roomNum, type, price, (booked ? "Booked" : "Available"));
        }
        raf.close();
    }

    public static void main(String[] args) {
        String filename = "hotel_rooms.dat";

        try {
            // Clean start
            new File(filename).delete();

            // Add rooms
            System.out.println("===== Adding Rooms =====");
            addRoom(filename, 101, "Standard", 2000.0, false);
            addRoom(filename, 202, "Deluxe", 3500.0, false);
            addRoom(filename, 303, "Suite", 5000.0, false);
            addRoom(filename, 104, "Standard", 2000.0, true);
            System.out.println("4 rooms added.\n");

            // Display all rooms
            System.out.println("===== All Rooms =====");
            displayAllRooms(filename);

            // Display specific room
            System.out.println("\n===== Lookup Room 202 =====");
            displayRoom(filename, 202);

            // Book room 202
            System.out.println("\n===== Booking Room 202 =====");
            updateBookingStatus(filename, 202, true);

            // Vacate room 104
            System.out.println("\n===== Vacating Room 104 =====");
            updateBookingStatus(filename, 104, false);

            // Display all rooms after updates
            System.out.println("\n===== All Rooms (after updates) =====");
            displayAllRooms(filename);

            // Clean up
            new File(filename).delete();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
