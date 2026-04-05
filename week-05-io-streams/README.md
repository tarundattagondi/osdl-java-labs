# Week 5: Input/Output Streams – Byte Streams, Character Streams

**Status:** Completed

## Objective

To perform file operations using byte-oriented I/O streams and character-oriented I/O streams.

## Topics Covered

- Difference between byte and character streams
- `FileInputStream`
- `FileOutputStream`
- Reading and writing binary files
- `FileReader`
- `FileWriter`

## Sample Programs

| File | Concept |
|------|---------|
| `FileInputStreamDemo.java` | Read a file byte by byte with `FileInputStream` |
| `FileOutputStreamDemo.java` | Write string data to a file with `FileOutputStream` |
| `FileReaderDemo.java` | Read text file with `FileReader` — manual close and try-with-resources |
| `FileWriterDemo.java` | Write, append, and overwrite a file with `FileWriter` |

## Lab Exercises

### Exercise 1 — File Copy using Byte Streams
**File:** `Ex1_ByteStreamFileCopy.java`

Design and implement a Java application that copies the contents of one file to another using byte streams. The program must use `FileInputStream` to read data from a source file and `FileOutputStream` to write the same data to a destination file.

### Exercise 2 — File Copy using Character Streams
**File:** `Ex2_CharStreamFileCopy.java`

Design and implement a Java application that reads textual data from an existing text file using `FileReader` and writes the same content into another text file using `FileWriter`.

## How to Run

```bash
cd week-05-io-streams
javac src/*.java
```

```bash
java -cp src FileInputStreamDemo
java -cp src FileOutputStreamDemo
java -cp src FileReaderDemo
java -cp src FileWriterDemo
java -cp src Ex1_ByteStreamFileCopy
java -cp src Ex2_CharStreamFileCopy
```

Note: These programs create temporary files (`input.txt`, `output.txt`, `guest_register.txt`, etc.) in the current directory. They are safe to delete after running.
