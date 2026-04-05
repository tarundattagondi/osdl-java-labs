# Week 7: Generics

**Status:** Pending

## Objective

To understand and apply Java generics for type-safe, reusable code.

## Topics Covered

- Generic classes with two type parameters
- Bounded types
- Generic methods

## Lab Exercises

1. Develop a Java application that uses a generic class with two type parameters to store hotel room information. The generic class should be capable of holding different data types for room identifiers and room attributes.

   - Create a generic class `Room<T, U>` where `T` represents Room Number or Room ID and `U` represents Room Type or Price
   - Demonstrate usage with different data types (e.g., `Integer`, `String`, `Double`)
   - Display stored room details

2. Create a Java program for a hotel room management system that uses a generic method to display room-related data of different types such as room numbers, room types, prices, and booking status.

   - Implement a generic method `<T> void display(T data)`
   - Call the method with: Room number (`Integer`), Room type (`String`), Price per night (`Double`), Booking status (`Boolean`)
   - Ensure type safety without explicit casting

3. Develop a Java application that uses bounded type parameters to calculate room charges. The application should accept only numeric values for pricing and discount calculations.

   - Create a generic class or method using `<T extends Number>`
   - Accept room price and discount as generic parameters
   - Perform calculations such as total price and discounted price
   - Prevent non-numeric data types at compile time

4. Design a Java program that uses generic methods to manage an array of hotel rooms. The program should be capable of storing and displaying arrays of different room attributes.

   - Create a generic method to print arrays
   - Use it for: room numbers array, room types array, room prices array
   - Do not use the collections framework

5. Develop a hotel room booking module that uses a generic pair class to associate room numbers with guest details.

   - Create a generic class `Pair<T, U>`
   - Store: Room Number (`Integer`), Guest Name (`String`)
   - Display booking records
   - Ensure no type casting is required
