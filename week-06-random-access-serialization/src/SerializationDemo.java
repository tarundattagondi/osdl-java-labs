/**
 * Sample Program 2: Serialization and Deserialization
 * Serializes a Student object to a file, then deserializes it back.
 */
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

public class SerializationDemo {

    static class Student implements Serializable {
        int id;
        String name;
        double marks;

        Student(int id, String name, double marks) {
            this.id = id;
            this.name = name;
            this.marks = marks;
        }
    }

    public static void main(String[] args) {
        String filename = "student.dat";

        // Serialization
        try {
            Student s1 = new Student(101, "Ravi", 85.5);

            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(s1);
            oos.close();
            fos.close();

            System.out.println("Object serialized successfully.");
        } catch (IOException e) {
            System.out.println("Serialization error: " + e.getMessage());
        }

        // Deserialization
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Student s = (Student) ois.readObject();

            System.out.println("ID: " + s.id);
            System.out.println("Name: " + s.name);
            System.out.println("Marks: " + s.marks);

            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Deserialization error: " + e.getMessage());
        }

        // Clean up
        new java.io.File(filename).delete();
    }
}
