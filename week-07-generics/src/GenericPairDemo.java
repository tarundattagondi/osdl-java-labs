/**
 * Sample Program 1: Generic class with two type parameters
 * Demonstrates a Pair<T, U> class holding two values of different types.
 */
class Pair<T, U> {
    private T first;
    private U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() { return first; }
    public U getSecond() { return second; }

    public void display() {
        System.out.println("First Value: " + first);
        System.out.println("Second Value: " + second);
    }
}

public class GenericPairDemo {
    public static void main(String[] args) {
        Pair<Integer, String> p1 = new Pair<>(101, "Deluxe Room");
        p1.display();

        System.out.println();

        Pair<String, Double> p2 = new Pair<>("Room Price", 3500.50);
        p2.display();
    }
}
