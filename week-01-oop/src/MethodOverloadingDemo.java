/**
 * Sample Program 2: Method Overloading
 * Demonstrates compile-time polymorphism with overloaded add methods.
 */
class Calculator {
    int add(int a, int b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }

    double add(double a, double b) {
        return a + b;
    }
}

public class MethodOverloadingDemo {
    public static void main(String[] args) {
        Calculator calc = new Calculator();

        System.out.println("Addition of two integers   : " + calc.add(10, 20));
        System.out.println("Addition of three integers  : " + calc.add(10, 20, 30));
        System.out.println("Addition of two double values: " + calc.add(12.5, 7.5));
    }
}
