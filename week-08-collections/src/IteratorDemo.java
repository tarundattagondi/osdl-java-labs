/**
 * Sample Program 2: Iterator
 * Accesses collection elements using Iterator interface.
 */
import java.util.ArrayList;
import java.util.Iterator;

public class IteratorDemo {
    public static void main(String[] args) {

        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(10);
        numbers.add(20);
        numbers.add(30);
        numbers.add(40);

        Iterator<Integer> itr = numbers.iterator();

        System.out.println("Elements in the ArrayList:");
        while (itr.hasNext()) {
            Integer value = itr.next();
            System.out.println("  " + value);
        }
    }
}
