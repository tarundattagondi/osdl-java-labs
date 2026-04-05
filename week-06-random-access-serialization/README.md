# Week 6: Random Access File, Serialization and Deserialization

**Status:** Completed

## Objective

To work with `RandomAccessFile` for direct file access and implement object persistence using serialization.

## Topics Covered

- `RandomAccessFile`
- Serialization
- Deserialization

## Sample Programs

| File | Concept |
|------|---------|
| `RandomAccessFileDemo.java` | Write int/UTF/double, `seek()` to beginning and to a specific offset, read back |
| `SerializationDemo.java` | Serialize a `Student` object to file, deserialize and print fields |

## Lab Exercises

### Exercise 1 — Hotel Room Management with RandomAccessFile
**File:** `Ex1_HotelRoomRandomAccess.java`

Design and implement a Java application to manage hotel room bookings where room records are stored in a file and accessed using `RandomAccessFile`. Each room record should be of fixed length, enabling direct (random) access to any room's booking information without reading the file sequentially.

Each room record must contain:
- Room Number (`int`)
- Room Type (fixed-length `String`, 20 characters)
- Price per Night (`double`)
- Booking Status (`boolean`)

Provides options to:
- Add new room records
- Display details of a specific room using its room number
- Update booking status (book / vacate a room)

### Exercise 2 — Hotel Room Booking with Serialization
**File:** `Ex2_HotelRoomSerialization.java`

Design and implement a Java application for managing hotel room bookings where room booking details are stored as serialized objects in a file. The application uses serialization to save hotel room booking objects permanently and deserialization to retrieve them when required.

Creates a `Room` class that implements `Serializable`. Each room object stores:
- Room Number, Room Type, Price per Night, Booking Status, Guest Name

Operations demonstrated:
- Serialize room objects and store in a file
- Deserialize and display all rooms
- Search for a room by room number
- Book and vacate rooms by deserializing, modifying, and re-serializing

## How to Run

```bash
cd week-06-random-access-serialization
javac src/*.java
```

```bash
java -cp src RandomAccessFileDemo
java -cp src SerializationDemo
java -cp src Ex1_HotelRoomRandomAccess
java -cp src Ex2_HotelRoomSerialization
```

Note: Programs create and delete temporary data files (`data.txt`, `student.dat`, `hotel_rooms.dat`, `rooms.ser`) during execution. No manual cleanup needed.
