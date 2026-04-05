/**
 * Sample Program 5: Bounded generic method
 * sum() accepts only Number subtypes, preventing non-numeric arguments at compile time.
 */
public class GenericBoundedMethodDemo {

    public static <T extends Number> double sum(T a, T b) {
        return a.doubleValue() + b.doubleValue();
    }

    public static void main(String[] args) {
        System.out.println(sum(10, 20));     // Integer
        System.out.println(sum(5.5, 2.5));   // Double
        // Compilation error if uncommented:
        // System.out.println(sum("A", "B"));
    }
}
