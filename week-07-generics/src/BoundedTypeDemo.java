/**
 * Sample Program 2: Bounded type parameters
 * NumberOperations<T extends Number> restricts T to numeric types only.
 */
class NumberOperations<T extends Number> {
    private T num1;
    private T num2;

    public NumberOperations(T num1, T num2) {
        this.num1 = num1;
        this.num2 = num2;
    }

    public double add() {
        return num1.doubleValue() + num2.doubleValue();
    }

    public double multiply() {
        return num1.doubleValue() * num2.doubleValue();
    }
}

public class BoundedTypeDemo {
    public static void main(String[] args) {
        NumberOperations<Integer> obj1 = new NumberOperations<>(10, 20);
        System.out.println("Addition (Integer): " + obj1.add());
        System.out.println("Multiplication (Integer): " + obj1.multiply());

        NumberOperations<Double> obj2 = new NumberOperations<>(5.5, 2.0);
        System.out.println("Addition (Double): " + obj2.add());
        System.out.println("Multiplication (Double): " + obj2.multiply());
    }
}
