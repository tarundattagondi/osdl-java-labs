/**
 * Sample Program 1: FileInputStream
 * Reads a file byte by byte using FileInputStream and prints its contents.
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileInputStreamDemo {
    public static void main(String[] args) {
        // Create a sample input file first
        try (FileOutputStream setup = new FileOutputStream("input.txt")) {
            setup.write("Welcome to Java FileInputStream.\nThis is a sample file.".getBytes());
        } catch (IOException e) {
            System.out.println("Setup error: " + e.getMessage());
            return;
        }

        FileInputStream fis = null;
        try {
            fis = new FileInputStream("input.txt");

            int data;
            System.out.println("File contents:");
            while ((data = fis.read()) != -1) {
                System.out.print((char) data);
            }
            System.out.println();
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing file");
            }
        }
    }
}
