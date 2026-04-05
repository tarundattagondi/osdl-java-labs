/**
 * Sample Program 3: Inheritance and Runtime Polymorphism
 * Base class Employee, derived class Manager. Overrides calculateSalary().
 */
class Employee {
    protected int empId;
    protected String name;
    protected double basicSalary;

    Employee(int empId, String name, double basicSalary) {
        this.empId = empId;
        this.name = name;
        this.basicSalary = basicSalary;
    }

    double calculateSalary() {
        return basicSalary;
    }

    void displayDetails() {
        System.out.println("Employee ID   : " + empId);
        System.out.println("Employee Name : " + name);
    }
}

class Manager extends Employee {
    private double hra;
    private double da;

    Manager(int empId, String name, double basicSalary, double hra, double da) {
        super(empId, name, basicSalary);
        this.hra = hra;
        this.da = da;
    }

    @Override
    double calculateSalary() {
        return basicSalary + hra + da;
    }

    void displayManagerDetails() {
        super.displayDetails();
        System.out.println("Basic Salary  : " + basicSalary);
        System.out.println("HRA           : " + hra);
        System.out.println("DA            : " + da);
        System.out.println("Total Salary  : " + calculateSalary());
    }
}

public class EmployeeDemo {
    public static void main(String[] args) {
        Employee emp = new Manager(101, "Rahul Sharma", 40000, 8000, 6000);

        System.out.println("---- Manager Salary Details ----");

        double totalSalary = emp.calculateSalary();

        if (emp instanceof Manager) {
            Manager mgr = (Manager) emp;
            mgr.displayManagerDetails();
        }
        System.out.println("--------------------------------");
        System.out.println("Salary Calculated Using Polymorphism: " + totalSalary);
    }
}
