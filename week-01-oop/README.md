# Week 1: Review of Object-Oriented Programming Concepts

**Status:** Completed

## Objective

To refresh OOP concepts using Java.

## Topics Covered

- Classes and Objects
- Encapsulation
- Inheritance
- Polymorphism
- Method Overloading and Overriding
- `this` and `super` keywords
- Constructors
- Abstraction

## Sample Programs

| File | Concept |
|------|---------|
| `EncapsulationDemo.java` | Private fields, public getters/setters |
| `MethodOverloadingDemo.java` | Compile-time polymorphism via overloaded methods |
| `EmployeeDemo.java` | Inheritance, method overriding, runtime polymorphism |

## Lab Exercises

### Exercise 1 — Book Class with Encapsulation
**File:** `Ex1_BookEncapsulation.java`

Create a `Book` class with private data members including book ID, book title, author name, price, and availability status. Provide public setter methods to assign values to these data members and public getter methods to retrieve their values. Include validation in setter methods to ensure that the price is a positive value.

### Exercise 2 — Room/DeluxeRoom Constructor Overloading and Inheritance
**File:** `Ex2_RoomConstructorOverloading.java`

Create a base class named `Room` to represent general room details in a hotel. The class should contain data members such as room number, room type, and base price. Implement multiple constructors (constructor overloading) in the `Room` class to initialize room objects in different ways, such as:
- Initializing only the room number and type
- Initializing room number, type, and base price

Create a derived class named `DeluxeRoom` that inherits from the `Room` class using single inheritance. The derived class should include additional data members such as free Wi-Fi availability and complimentary breakfast. Implement appropriate constructors in the derived class that invoke the base class constructors using the `super` keyword.

Create a main class to instantiate objects of both `Room` and `DeluxeRoom` using different constructors and display the room details. This application should clearly illustrate constructor overloading and inheritance.

### Exercise 3 — Hotel Room Booking System with Runtime Polymorphism
**File:** `Ex3_HotelRoomPolymorphism.java`

Design and implement a Java application to simulate a Hotel Room Booking System that demonstrates the object-oriented concepts of inheritance and runtime polymorphism.

Create a base class named `Room` that represents a general hotel room. The class should contain data members such as room number and base tariff, and a method `calculateTariff()` to compute the room cost.

Create derived classes such as `StandardRoom` and `LuxuryRoom` that inherit from the `Room` class. Each derived class should override the `calculateTariff()` method to compute the tariff based on room-specific features such as air conditioning, additional amenities, or premium services.

In the main class, create a base class reference of type `Room` and assign it to objects of different derived classes (`StandardRoom`, `LuxuryRoom`). Invoke the `calculateTariff()` method using the base class reference to demonstrate runtime polymorphism, where the method call is resolved at runtime based on the actual object type.

### Exercise 4 — Abstract Room Class with Amenities Interface
**File:** `Ex4_AbstractRoomWithAmenities.java`

Create an abstract class named `Room` that represents a generic hotel room. The abstract class should contain common data members such as room number and base price, and include an abstract method `calculateTariff()` that must be implemented by all subclasses. It may also include concrete methods such as `displayRoomDetails()`.

Create derived classes such as `StandardRoom` and `LuxuryRoom` that extend the abstract `Room` class and provide concrete implementations for the `calculateTariff()` method based on room-specific features.

Create an interface named `Amenities` that declares methods such as `provideWifi()` and `provideBreakfast()`. The derived room classes should implement this interface to define the amenities offered for each room type.

Create a main class to instantiate different room objects using a base class reference and invoke the implemented methods to demonstrate abstraction and interface-based design.

## How to Run

```bash
cd week-01-oop
javac src/*.java
```

```bash
java -cp src EncapsulationDemo
java -cp src MethodOverloadingDemo
java -cp src EmployeeDemo
java -cp src Ex1_BookEncapsulation
java -cp src Ex2_RoomConstructorOverloading
java -cp src Ex3_HotelRoomPolymorphism
java -cp src Ex4_AbstractRoomWithAmenities
```

## Course Outcome

Students will be able to design and implement Java programs using fundamental OOP principles.
