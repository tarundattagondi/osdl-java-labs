/**
 * Sample Program 3: List Sorting
 * Sorts an ArrayList in ascending and descending order using Collections.sort().
 */
import java.util.ArrayList;
import java.util.Collections;

public class ListSortingDemo {
    public static void main(String[] args) {

        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(45);
        numbers.add(10);
        numbers.add(30);
        numbers.add(25);
        numbers.add(5);

        System.out.println("Original List: " + numbers);

        Collections.sort(numbers);
        System.out.println("Sorted List (Ascending): " + numbers);

        Collections.sort(numbers, Collections.reverseOrder());
        System.out.println("Sorted List (Descending): " + numbers);
    }
}
