/**
 * Exercise 1: Hotel Room Booking with Synchronization and Inter-thread Communication
 * Concept: Synchronized methods, wait(), notify().
 *
 * A hotel has a limited number of rooms. Multiple customer threads try to book
 * rooms concurrently. If no rooms are available, the booking thread waits.
 * When a room is released, waiting threads are notified and can proceed.
 */
class HotelRoomPool {
    private int availableRooms;

    HotelRoomPool(int totalRooms) {
        this.availableRooms = totalRooms;
    }

    synchronized void bookRoom(String guestName) {
        System.out.println(guestName + " requests a room. Available: " + availableRooms);

        while (availableRooms <= 0) {
            System.out.println(guestName + " is waiting — no rooms available.");
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(guestName + " interrupted while waiting");
            }
            System.out.println(guestName + " woke up, checking availability. Available: " + availableRooms);
        }

        availableRooms--;
        System.out.println(guestName + " booked a room. Remaining: " + availableRooms);
    }

    synchronized void releaseRoom(String guestName) {
        availableRooms++;
        System.out.println(guestName + " checked out. Available: " + availableRooms);
        notifyAll();
    }
}

class BookingThread extends Thread {
    private HotelRoomPool pool;
    private String guestName;

    BookingThread(HotelRoomPool pool, String guestName) {
        this.pool = pool;
        this.guestName = guestName;
    }

    @Override
    public void run() {
        pool.bookRoom(guestName);
        // Simulate stay duration
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println(guestName + " stay interrupted");
        }
        pool.releaseRoom(guestName);
    }
}

public class Ex1_HotelRoomBookingSync {
    public static void main(String[] args) {
        // Hotel with only 2 rooms, but 5 guests trying to book
        HotelRoomPool pool = new HotelRoomPool(2);

        System.out.println("===== Hotel Room Booking System =====");
        System.out.println("Total rooms: 2 | Guests arriving: 5\n");

        BookingThread g1 = new BookingThread(pool, "Amit Sharma");
        BookingThread g2 = new BookingThread(pool, "Priya Patel");
        BookingThread g3 = new BookingThread(pool, "Rahul Verma");
        BookingThread g4 = new BookingThread(pool, "Neha Kapoor");
        BookingThread g5 = new BookingThread(pool, "Vikram Singh");

        g1.start();
        g2.start();
        g3.start();
        g4.start();
        g5.start();

        try {
            g1.join();
            g2.join();
            g3.join();
            g4.join();
            g5.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted");
        }

        System.out.println("\nAll guests have been served.");
    }
}
