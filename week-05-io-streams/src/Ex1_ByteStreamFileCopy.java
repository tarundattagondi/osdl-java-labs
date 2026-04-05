/**
 * Exercise 1: File Copy using Byte Streams
 * Concept: FileInputStream to read, FileOutputStream to write — byte-level copy.
 *
 * Copies the contents of a source file to a destination file using byte streams.
 * Uses hotel guest registration data as sample content.
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Ex1_ByteStreamFileCopy {
    public static void main(String[] args) {
        String sourceFile = "guest_register.txt";
        String destFile = "guest_register_backup.txt";

        // Create sample source file with hotel data
        try (FileOutputStream setup = new FileOutputStream(sourceFile)) {
            String data = "===== Grand Palace Hotel - Guest Register =====\n"
                    + "Room 101 | Amit Sharma    | Check-in: 2025-06-01 | Deluxe\n"
                    + "Room 205 | Priya Patel    | Check-in: 2025-06-01 | Standard\n"
                    + "Room 312 | Rahul Verma    | Check-in: 2025-06-02 | Suite\n"
                    + "Room 108 | Neha Kapoor    | Check-in: 2025-06-02 | Deluxe\n"
                    + "Room 410 | Vikram Singh   | Check-in: 2025-06-03 | Standard\n";
            setup.write(data.getBytes());
            System.out.println("Source file created: " + sourceFile);
        } catch (IOException e) {
            System.out.println("Error creating source: " + e.getMessage());
            return;
        }

        // Copy source to destination using byte streams
        FileInputStream fis = null;
        FileOutputStream fos = null;
        int bytesCopied = 0;

        try {
            fis = new FileInputStream(sourceFile);
            fos = new FileOutputStream(destFile);

            int data;
            while ((data = fis.read()) != -1) {
                fos.write(data);
                bytesCopied++;
            }

            System.out.println("File copied successfully.");
            System.out.println("Bytes copied: " + bytesCopied);
        } catch (IOException e) {
            System.out.println("Copy error: " + e.getMessage());
        } finally {
            try {
                if (fis != null) fis.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                System.out.println("Error closing streams");
            }
        }

        // Verify by reading destination file
        System.out.println("\nContents of " + destFile + ":");
        try (FileInputStream verify = new FileInputStream(destFile)) {
            int ch;
            while ((ch = verify.read()) != -1) {
                System.out.print((char) ch);
            }
        } catch (IOException e) {
            System.out.println("Verification error: " + e.getMessage());
        }
    }
}
