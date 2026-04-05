/**
 * Sample Program 1: ArrayList Operations
 * Demonstrates add, insert, get, set, remove, contains, iterator, sort, clear.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class ArrayListDemo {
    public static void main(String[] args) {

        // 1. Creating an ArrayList
        ArrayList<String> students = new ArrayList<>();

        // 2. Adding elements
        students.add("Amit");
        students.add("Riya");
        students.add("Neha");
        students.add("Karan");
        System.out.println("Initial ArrayList: " + students);

        // 3. Adding element at a specific index
        students.add(2, "Sonal");
        System.out.println("After insertion at index 2: " + students);

        // 4. Accessing elements
        System.out.println("Element at index 1: " + students.get(1));

        // 5. Updating elements
        students.set(3, "Rahul");
        System.out.println("After updating index 3: " + students);

        // 6. Removing elements
        students.remove("Neha");   // remove by value
        students.remove(0);        // remove by index
        System.out.println("After removals: " + students);

        // 7. Checking size
        System.out.println("Size of ArrayList: " + students.size());

        // 8. Searching elements
        System.out.println("Contains 'Riya'? " + students.contains("Riya"));

        // 9. Iterating using for-each loop
        System.out.println("Iterating using for-each:");
        for (String name : students) {
            System.out.println("  " + name);
        }

        // 10. Iterating using Iterator
        System.out.println("Iterating using Iterator:");
        Iterator<String> it = students.iterator();
        while (it.hasNext()) {
            System.out.println("  " + it.next());
        }

        // 11. Sorting the ArrayList
        Collections.sort(students);
        System.out.println("After sorting: " + students);

        // 12. Clearing the ArrayList
        students.clear();
        System.out.println("After clearing: " + students);
    }
}
