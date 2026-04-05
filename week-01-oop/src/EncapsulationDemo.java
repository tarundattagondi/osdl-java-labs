/**
 * Sample Program 1: Encapsulation
 * Demonstrates private data members with public getters/setters.
 */
class Student {
    private int rollNumber;
    private String name;
    private double marks;

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMarks(double marks) {
        if (marks >= 0 && marks <= 100) {
            this.marks = marks;
        } else {
            System.out.println("Invalid marks. Marks should be between 0 and 100.");
        }
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public String getName() {
        return name;
    }

    public double getMarks() {
        return marks;
    }
}

public class EncapsulationDemo {
    public static void main(String[] args) {
        Student student = new Student();

        student.setRollNumber(1);
        student.setName("Ananya");
        student.setMarks(85.5);

        System.out.println("Student Details");
        System.out.println("---------------");
        System.out.println("Roll Number : " + student.getRollNumber());
        System.out.println("Name        : " + student.getName());
        System.out.println("Marks       : " + student.getMarks());
    }
}
