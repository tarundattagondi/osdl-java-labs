/**
 * Exercise 1: Hotel Room Service Management System
 * Concept: Multithreading with Thread class and Runnable interface, sleep().
 *
 * Multiple hotel service tasks (room cleaning, food delivery, maintenance)
 * run concurrently in separate threads, simulating real-time service handling.
 */
class CleaningService extends Thread {
    private String roomNumber;

    CleaningService(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public void run() {
        System.out.println("[Cleaning] " + roomNumber + " - Service started");
        for (int i = 1; i <= 4; i++) {
            System.out.println("[Cleaning] " + roomNumber + " - Step " + i + ": "
                    + getStepDescription(i));
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                System.out.println("[Cleaning] Thread interrupted");
            }
        }
        System.out.println("[Cleaning] " + roomNumber + " - Service completed\n");
    }

    private String getStepDescription(int step) {
        switch (step) {
            case 1: return "Removing used towels and linens";
            case 2: return "Vacuuming and mopping floor";
            case 3: return "Replacing toiletries and minibar";
            case 4: return "Final inspection";
            default: return "Processing";
        }
    }
}

class FoodDeliveryService implements Runnable {
    private String roomNumber;
    private String orderItem;

    FoodDeliveryService(String roomNumber, String orderItem) {
        this.roomNumber = roomNumber;
        this.orderItem = orderItem;
    }

    @Override
    public void run() {
        System.out.println("[Food] " + roomNumber + " - Order received: " + orderItem);
        String[] stages = {"Preparing order", "Plating food", "En route to room", "Delivered"};
        for (int i = 0; i < stages.length; i++) {
            System.out.println("[Food] " + roomNumber + " - " + stages[i]);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("[Food] Thread interrupted");
            }
        }
        System.out.println("[Food] " + roomNumber + " - Service completed\n");
    }
}

class MaintenanceService implements Runnable {
    private String roomNumber;
    private String issue;

    MaintenanceService(String roomNumber, String issue) {
        this.roomNumber = roomNumber;
        this.issue = issue;
    }

    @Override
    public void run() {
        System.out.println("[Maintenance] " + roomNumber + " - Issue reported: " + issue);
        String[] stages = {"Inspecting issue", "Gathering tools", "Performing repair", "Testing fix"};
        for (int i = 0; i < stages.length; i++) {
            System.out.println("[Maintenance] " + roomNumber + " - " + stages[i]);
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                System.out.println("[Maintenance] Thread interrupted");
            }
        }
        System.out.println("[Maintenance] " + roomNumber + " - Service completed\n");
    }
}

public class Ex1_HotelRoomServiceThreads {
    public static void main(String[] args) {
        System.out.println("===== Hotel Room Service Management =====\n");

        // Thread created by extending Thread
        CleaningService cleaning = new CleaningService("Room 305");

        // Threads created using Runnable
        Thread foodDelivery = new Thread(new FoodDeliveryService("Room 412", "Club Sandwich & Coffee"));
        Thread maintenance = new Thread(new MaintenanceService("Room 210", "AC not cooling"));

        // Start all service threads concurrently
        cleaning.start();
        foodDelivery.start();
        maintenance.start();

        // Wait for all threads to finish
        try {
            cleaning.join();
            foodDelivery.join();
            maintenance.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted");
        }

        System.out.println("All service requests processed.");
    }
}
