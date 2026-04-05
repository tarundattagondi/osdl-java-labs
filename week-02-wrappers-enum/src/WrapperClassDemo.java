/**
 * Sample Program 1: Wrapper Classes
 * Demonstrates boxing (primitive to wrapper) and unboxing (wrapper to primitive).
 */
public class WrapperClassDemo {
    public static void main(String[] args) {

        // Primitive data types
        int a = 10;
        double b = 25.5;
        char c = 'A';
        boolean flag = true;

        // Converting primitives to wrapper objects (Boxing)
        Integer intObj = Integer.valueOf(a);
        Double doubleObj = Double.valueOf(b);
        Character charObj = Character.valueOf(c);
        Boolean boolObj = Boolean.valueOf(flag);

        // Displaying wrapper objects
        System.out.println("Wrapper Objects:");
        System.out.println("Integer Object   : " + intObj);
        System.out.println("Double Object    : " + doubleObj);
        System.out.println("Character Object : " + charObj);
        System.out.println("Boolean Object   : " + boolObj);

        // Converting wrapper objects back to primitives (Unboxing)
        int x = intObj.intValue();
        double y = doubleObj.doubleValue();
        char z = charObj.charValue();
        boolean status = boolObj.booleanValue();

        // Displaying primitive values after unboxing
        System.out.println("\nPrimitive Values After Unboxing:");
        System.out.println("int value     : " + x);
        System.out.println("double value  : " + y);
        System.out.println("char value    : " + z);
        System.out.println("boolean value : " + status);
    }
}
