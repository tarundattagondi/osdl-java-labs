/**
 * Exercise 2: Generic method to display room data of different types
 * Concept: Single generic method <T> void display(T data) handles all types.
 */
public class Ex2_GenericDisplayMethod {

    public static <T> void display(T data) {
        System.out.println("[" + data.getClass().getSimpleName() + "] " + data);
    }

    public static void main(String[] args) {
        System.out.println("===== Hotel Room Info Display =====");

        System.out.print("Room Number    : ");
        display(301);

        System.out.print("Room Type      : ");
        display("Suite");

        System.out.print("Price per Night: ");
        display(5000.0);

        System.out.print("Booking Status : ");
        display(true);
    }
}
