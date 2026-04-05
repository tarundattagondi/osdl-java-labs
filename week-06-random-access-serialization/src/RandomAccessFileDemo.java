/**
 * Sample Program 1: RandomAccessFile
 * Demonstrates writing and reading data at specific positions using seek().
 */
import java.io.RandomAccessFile;
import java.io.IOException;

public class RandomAccessFileDemo {
    public static void main(String[] args) {
        try {
            RandomAccessFile raf = new RandomAccessFile("data.txt", "rw");

            // Writing data to file
            raf.writeInt(101);
            raf.writeUTF("Java");
            raf.writeDouble(99.5);

            // Move file pointer to beginning
            raf.seek(0);

            // Reading data sequentially
            int id = raf.readInt();
            String name = raf.readUTF();
            double marks = raf.readDouble();

            System.out.println("ID: " + id);
            System.out.println("Name: " + name);
            System.out.println("Marks: " + marks);

            // Random access: move pointer and read again
            raf.seek(4); // Skips integer (4 bytes)
            System.out.println("Name (Random Access): " + raf.readUTF());

            raf.close();

            // Clean up
            new java.io.File("data.txt").delete();
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
    }
}
