# Week 7: Generics

**Status:** Completed

## Objective

To understand and apply Java generics for type-safe, reusable code.

## Topics Covered

- Generic classes with two type parameters
- Bounded types
- Generic methods

## Sample Programs

| File | Concept |
|------|---------|
| `GenericPairDemo.java` | `Pair<T, U>` class holding two values of different types |
| `BoundedTypeDemo.java` | `NumberOperations<T extends Number>` restricts to numeric types |
| `GenericMethodDemo.java` | Single generic method `<T> void display(T value)` |
| `GenericArrayDemo.java` | Generic method printing elements of any typed array |
| `GenericBoundedMethodDemo.java` | Bounded generic method `<T extends Number> double sum(T, T)` |

## Lab Exercises

### Exercise 1 — Generic Room Class with Two Type Parameters
**File:** `Ex1_GenericRoomClass.java`

Create a generic class `Room<T, U>` where `T` represents Room Number or Room ID and `U` represents Room Type or Price. Demonstrate usage with different data types (`Integer`, `String`, `Double`) and display stored room details.

### Exercise 2 — Generic Display Method for Room Data
**File:** `Ex2_GenericDisplayMethod.java`

Implement a generic method `<T> void display(T data)` and call it with Room number (`Integer`), Room type (`String`), Price per night (`Double`), and Booking status (`Boolean`). Ensure type safety without explicit casting.

### Exercise 3 — Bounded Type Room Charge Calculator
**File:** `Ex3_BoundedRoomCharges.java`

Create a generic class using `<T extends Number>` that accepts room price and discount as generic parameters. Perform calculations such as total price and discounted price. Non-numeric data types are prevented at compile time.

### Exercise 4 — Generic Method for Room Attribute Arrays
**File:** `Ex4_GenericRoomArrays.java`

Create a generic method to print arrays of room numbers, room types, and room prices. Does not use the collections framework.

### Exercise 5 — Generic Booking Pair
**File:** `Ex5_GenericBookingPair.java`

Create a generic class `Pair<T, U>` to associate Room Number (`Integer`) with Guest Name (`String`). Display booking records with no type casting required.

## How to Run

```bash
cd week-07-generics
javac src/*.java
```

```bash
java -cp src GenericPairDemo
java -cp src BoundedTypeDemo
java -cp src GenericMethodDemo
java -cp src GenericArrayDemo
java -cp src GenericBoundedMethodDemo
java -cp src Ex1_GenericRoomClass
java -cp src Ex2_GenericDisplayMethod
java -cp src Ex3_BoundedRoomCharges
java -cp src Ex4_GenericRoomArrays
java -cp src Ex5_GenericBookingPair
```

## Course Outcome

Students will understand generic classes, bounded types, and generic methods for writing type-safe, reusable Java code.
