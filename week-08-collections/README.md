# Week 8: Collection Framework – List Interface

**Status:** Pending

## Objective

To use the Java Collection Framework for dynamic data management.

## Topics Covered

- Collection interfaces
- List interface
- `ArrayList`
- `Iterator`

## Lab Exercises

1. Design and implement a Hotel Management System in Java using the Collection Framework to manage hotel operations efficiently. The system should store, retrieve, update, and process hotel-related data dynamically using appropriate collection classes.

   **System Requirements:**

   **Room Management**
   - Store room details: Room Number, Room Type (Single, Double, Deluxe, Suite), Room Price per Day, Availability Status
   - Allow adding new rooms and displaying all available rooms

   **Customer Management**
   - Maintain customer details: Customer ID, Name, Contact Number, Room Number Allocated
   - Support operations to add, view, and remove customer records

   **Booking Management**
   - Enable room booking and checkout functionality
   - Update room availability automatically after booking or checkout
   - Prevent booking of already occupied rooms

   **Collections Usage**
   - Use `ArrayList` to store room and customer objects
   - Use `HashMap` to map room numbers to customer details
   - Use `Iterator` to traverse and manage records
   - Apply sorting (e.g., by room price or room number) using `Collections.sort()`

   **Menu-Driven Interface**
   - Provide a console-based menu with options: Add Room, Display Available Rooms, Add Customer, Book Room, Checkout Customer, Display All Customers, Exit

   **Constraints**
   - Do not use arrays for data storage
   - All data must be handled using Java Collections
   - Ensure proper validation and exception handling
