/**
 * Sample Program 2: FileOutputStream
 * Writes string data to a file using FileOutputStream.
 */
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOutputStreamDemo {
    public static void main(String[] args) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("output.txt");

            String message = "Welcome to Java FileOutputStream.\nFile writing example.";
            byte[] data = message.getBytes();
            fos.write(data);

            System.out.println("Data written to file successfully.");
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing file");
            }
        }

        // Verify by reading back
        try (java.io.FileInputStream fis = new java.io.FileInputStream("output.txt")) {
            System.out.println("Verification — output.txt contains:");
            int ch;
            while ((ch = fis.read()) != -1) {
                System.out.print((char) ch);
            }
            System.out.println();
        } catch (IOException e) {
            System.out.println("Verification error: " + e.getMessage());
        }
    }
}
