/**
 * Sample Program 4: Generic method with array parameter
 * Prints elements of any typed array.
 */
public class GenericArrayDemo {

    public static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.print(element + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Integer[] nums = {1, 2, 3, 4};
        String[] names = {"Amit", "Ravi", "Neha"};

        printArray(nums);
        printArray(names);
    }
}
