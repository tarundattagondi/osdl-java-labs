/**
 * Sample Program 2: Autoboxing and Unboxing
 * Demonstrates automatic conversion between primitives and wrapper objects.
 */
public class AutoBoxingUnboxingDemo {
    public static void main(String[] args) {

        // Autoboxing: primitive to wrapper object
        int num = 50;
        Integer intObj = num; // Autoboxing

        System.out.println("Autoboxing Example:");
        System.out.println("Primitive value : " + num);
        System.out.println("Wrapper object  : " + intObj);

        // Unboxing: wrapper object to primitive
        Integer obj = 100;
        int value = obj; // Unboxing

        System.out.println("\nUnboxing Example:");
        System.out.println("Wrapper object  : " + obj);
        System.out.println("Primitive value : " + value);
    }
}
