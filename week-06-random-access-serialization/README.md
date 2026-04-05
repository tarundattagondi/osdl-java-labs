# Week 6: Random Access File, Serialization and Deserialization

**Status:** Pending

## Objective

To work with `RandomAccessFile` for direct file access and implement object persistence using serialization.

## Topics Covered

- `RandomAccessFile`
- Serialization
- Deserialization

## Lab Exercises

1. Design and implement a Java application to manage hotel room bookings where room records are stored in a file and accessed using `RandomAccessFile`. Each room record should be of fixed length, enabling direct (random) access to any room's booking information without reading the file sequentially.

   The system must support operations such as adding rooms, viewing room details, and updating booking status by directly navigating to the required record position in the file.

   Each room record must contain:
   - Room Number (`int`)
   - Room Type (fixed-length `String`, e.g., 20 characters)
   - Price per Night (`double`)
   - Booking Status (`boolean`)

   Provide an option to:
   - Add new room records
   - Display details of a specific room using its room number
   - Update booking status (book / vacate a room)

   Use the `seek()` method to jump directly to the position of a room record. Ensure data is read and written in the same sequence and format. Close the file after each operation.

2. Design and implement a Java application for managing hotel room bookings where room booking details are stored as serialized objects in a file. The application should use serialization to save hotel room booking objects permanently and deserialization to retrieve them when required. This approach should simulate real-world object persistence without using a database.

   Create a `Room` class that implements `Serializable`. Each room object must store:
   - Room Number
   - Room Type
   - Price per Night
   - Booking Status
   - Guest Name

   Serialize room booking objects and store them in a file. Deserialize objects from the file to:
   - Display all room details
   - Search for a room using room number

   Allow updating booking status by:
   - Deserializing the objects
   - Modifying the required room object
   - Re-serializing the updated objects back to the file

   Handle file and class-related exceptions properly.
