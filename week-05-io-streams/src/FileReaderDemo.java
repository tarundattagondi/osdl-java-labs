/**
 * Sample Program 3: FileReader (manual close and try-with-resources)
 * Reads a text file character by character using FileReader.
 * Demonstrates both traditional close and try-with-resources approaches.
 */
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileReaderDemo {
    public static void main(String[] args) {
        // Create a sample input file
        try (FileWriter setup = new FileWriter("input.txt")) {
            setup.write("Hello Java\nFileReader Demo");
        } catch (IOException e) {
            System.out.println("Setup error: " + e.getMessage());
            return;
        }

        // Approach 1: Manual close
        System.out.println("=== Manual close approach ===");
        try {
            FileReader fr = new FileReader("input.txt");
            int ch;
            while ((ch = fr.read()) != -1) {
                System.out.print((char) ch);
            }
            fr.close();
            System.out.println();
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }

        // Approach 2: Try-with-resources
        System.out.println("\n=== Try-with-resources approach ===");
        try (FileReader fr = new FileReader("input.txt")) {
            int ch;
            while ((ch = fr.read()) != -1) {
                System.out.print((char) ch);
            }
            System.out.println();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
