/**
 * Exercise 5: Generic Pair class for room booking records
 * Concept: Pair<T, U> associates room numbers with guest names — no type casting needed.
 */
class BookingPair<T, U> {
    private T roomNumber;
    private U guestName;

    public BookingPair(T roomNumber, U guestName) {
        this.roomNumber = roomNumber;
        this.guestName = guestName;
    }

    public T getRoomNumber() { return roomNumber; }
    public U getGuestName() { return guestName; }

    public void display() {
        System.out.println("Room: " + roomNumber + " | Guest: " + guestName);
    }
}

public class Ex5_GenericBookingPair {
    public static void main(String[] args) {
        System.out.println("===== Hotel Booking Records =====\n");

        BookingPair<Integer, String> b1 = new BookingPair<>(101, "Amit Sharma");
        BookingPair<Integer, String> b2 = new BookingPair<>(202, "Priya Patel");
        BookingPair<Integer, String> b3 = new BookingPair<>(303, "Rahul Verma");
        BookingPair<Integer, String> b4 = new BookingPair<>(501, "Neha Kapoor");

        b1.display();
        b2.display();
        b3.display();
        b4.display();

        // Access without casting
        System.out.println("\nDirect access (no casting):");
        Integer room = b1.getRoomNumber();
        String guest = b1.getGuestName();
        System.out.println("Room " + room + " is booked by " + guest);
    }
}
