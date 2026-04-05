/**
 * Exercise 2: File Copy using Character Streams
 * Concept: FileReader to read, FileWriter to write — character-level copy.
 *
 * Reads textual data from a source text file using FileReader and writes
 * the same content into another text file using FileWriter.
 * Uses hotel room tariff data as sample content.
 */
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Ex2_CharStreamFileCopy {
    public static void main(String[] args) {
        String sourceFile = "room_tariffs.txt";
        String destFile = "room_tariffs_copy.txt";

        // Create sample source file with hotel tariff data
        try (FileWriter setup = new FileWriter(sourceFile)) {
            setup.write("===== Grand Palace Hotel — Room Tariff Card =====\n");
            setup.write("Room Type    | Base Price | AC Charge | Total\n");
            setup.write("-------------|------------|-----------|------\n");
            setup.write("Standard     | Rs. 2000   | Rs. 0     | Rs. 2000\n");
            setup.write("Standard AC  | Rs. 2000   | Rs. 500   | Rs. 2500\n");
            setup.write("Deluxe       | Rs. 3500   | Rs. 0     | Rs. 3500\n");
            setup.write("Suite        | Rs. 5000   | Rs. 0     | Rs. 5000\n");
            System.out.println("Source file created: " + sourceFile);
        } catch (IOException e) {
            System.out.println("Error creating source: " + e.getMessage());
            return;
        }

        // Copy source to destination using character streams
        int charsCopied = 0;
        try (FileReader fr = new FileReader(sourceFile);
             FileWriter fw = new FileWriter(destFile)) {

            int ch;
            while ((ch = fr.read()) != -1) {
                fw.write(ch);
                charsCopied++;
            }

            System.out.println("File copied successfully.");
            System.out.println("Characters copied: " + charsCopied);
        } catch (IOException e) {
            System.out.println("Copy error: " + e.getMessage());
        }

        // Verify by reading destination file
        System.out.println("\nContents of " + destFile + ":");
        try (FileReader verify = new FileReader(destFile)) {
            int ch;
            while ((ch = verify.read()) != -1) {
                System.out.print((char) ch);
            }
        } catch (IOException e) {
            System.out.println("Verification error: " + e.getMessage());
        }
    }
}
