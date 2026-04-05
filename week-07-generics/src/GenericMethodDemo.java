/**
 * Sample Program 3: Generic method
 * A single method accepts any type and prints it.
 */
public class GenericMethodDemo {

    public static <T> void display(T value) {
        System.out.println("Value: " + value);
    }

    public static void main(String[] args) {
        display(100);
        display("Java");
        display(45.6);
        display(true);
    }
}
