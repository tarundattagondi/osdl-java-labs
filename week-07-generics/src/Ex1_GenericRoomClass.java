/**
 * Exercise 1: Generic Room class with two type parameters
 * Concept: Generic class Room<T, U> for flexible room identifiers and attributes.
 *
 * T = Room Number or Room ID, U = Room Type or Price.
 */
class GenericRoom<T, U> {
    private T roomId;
    private U roomAttribute;

    public GenericRoom(T roomId, U roomAttribute) {
        this.roomId = roomId;
        this.roomAttribute = roomAttribute;
    }

    public T getRoomId() { return roomId; }
    public U getRoomAttribute() { return roomAttribute; }

    public void display() {
        System.out.println("Room ID/Number : " + roomId);
        System.out.println("Room Attribute : " + roomAttribute);
    }
}

public class Ex1_GenericRoomClass {
    public static void main(String[] args) {
        // Integer room number, String room type
        GenericRoom<Integer, String> room1 = new GenericRoom<>(101, "Deluxe");
        System.out.println("--- Room 1 ---");
        room1.display();

        // String room ID, Double price
        GenericRoom<String, Double> room2 = new GenericRoom<>("STE-501", 5000.0);
        System.out.println("\n--- Room 2 ---");
        room2.display();

        // Integer room number, Double price
        GenericRoom<Integer, Double> room3 = new GenericRoom<>(302, 3500.0);
        System.out.println("\n--- Room 3 ---");
        room3.display();
    }
}
