# Week 8: Collection Framework – List Interface

**Status:** Completed

## Objective

To use the Java Collection Framework for dynamic data management.

## Topics Covered

- Collection interfaces
- List interface
- `ArrayList`
- `Iterator`

## Sample Programs

| File | Concept |
|------|---------|
| `ArrayListDemo.java` | Add, insert, get, set, remove, contains, for-each, Iterator, sort, clear |
| `IteratorDemo.java` | Traverse `ArrayList<Integer>` using `Iterator` |
| `ListSortingDemo.java` | `Collections.sort()` ascending and `Collections.reverseOrder()` descending |

## Lab Exercises

### Exercise 1 — Hotel Management System using Collections
**File:** `Ex1_HotelManagementCollections.java`

Design and implement a Hotel Management System in Java using the Collection Framework to manage hotel operations efficiently. The system should store, retrieve, update, and process hotel-related data dynamically using appropriate collection classes.

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
- `ArrayList` to store room and customer objects
- `HashMap` to map room numbers to customer details
- `Iterator` to traverse and manage records
- `Collections.sort()` with `Comparator` to sort rooms by price

**Menu-Driven Interface**
- Non-interactive demo exercises all operations: Add Room, Display Available Rooms, Add Customer, Book Room, Checkout Customer, Display All Customers

**Constraints**
- No arrays used for data storage
- All data handled using Java Collections
- Proper validation (duplicate booking prevention, checkout of unoccupied rooms)

## How to Run

```bash
cd week-08-collections
javac src/*.java
```

```bash
java -cp src ArrayListDemo
java -cp src IteratorDemo
java -cp src ListSortingDemo
java -cp src Ex1_HotelManagementCollections
```
