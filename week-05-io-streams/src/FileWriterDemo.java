/**
 * Sample Program 4: FileWriter (write, append, try-with-resources)
 * Demonstrates writing to a file, appending to a file, and using try-with-resources.
 */
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class FileWriterDemo {
    public static void main(String[] args) {

        // Part 1: Write to file
        System.out.println("=== Writing to output.txt ===");
        try {
            FileWriter fw = new FileWriter("output.txt");
            fw.write("Hello Java\n");
            fw.write("FileWriter Demo");
            fw.close();
            System.out.println("Data written successfully.");
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
        printFile("output.txt");

        // Part 2: Append to file
        System.out.println("\n=== Appending to output.txt ===");
        try {
            FileWriter fw = new FileWriter("output.txt", true);
            fw.write("\nAppending new data...");
            fw.close();
            System.out.println("Data appended successfully.");
        } catch (IOException e) {
            System.out.println(e);
        }
        printFile("output.txt");

        // Part 3: Try-with-resources
        System.out.println("\n=== Try-with-resources (overwrite) ===");
        try (FileWriter fw = new FileWriter("output.txt")) {
            fw.write("Using try-with-resources");
        } catch (IOException e) {
            System.out.println(e);
        }
        printFile("output.txt");
    }

    /** Helper to print file contents for verification. */
    private static void printFile(String filename) {
        try (FileReader fr = new FileReader(filename)) {
            System.out.println("Contents of " + filename + ":");
            int ch;
            while ((ch = fr.read()) != -1) {
                System.out.print((char) ch);
            }
            System.out.println();
        } catch (IOException e) {
            System.out.println("Read error: " + e.getMessage());
        }
    }
}
