/**
 * Exercise 4: Generic method for room attribute arrays
 * Concept: Generic printArray method to display arrays of different room data types.
 * No collections framework used — arrays only.
 */
public class Ex4_GenericRoomArrays {

    public static <T> void printArray(String label, T[] array) {
        System.out.print(label + ": ");
        for (T element : array) {
            System.out.print(element + "  ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Integer[] roomNumbers = {101, 202, 303, 404, 505};
        String[] roomTypes = {"Standard", "Deluxe", "Suite", "Standard", "Deluxe"};
        Double[] roomPrices = {2000.0, 3500.0, 5000.0, 2000.0, 3500.0};

        System.out.println("===== Hotel Room Attributes =====\n");
        printArray("Room Numbers", roomNumbers);
        printArray("Room Types  ", roomTypes);
        printArray("Room Prices ", roomPrices);
    }
}
