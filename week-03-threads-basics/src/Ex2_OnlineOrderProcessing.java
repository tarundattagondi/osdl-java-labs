/**
 * Exercise 2: Online Order Processing System
 * Concept: Concurrent thread execution, sleep() for simulating delays.
 *
 * Multiple customer orders are processed simultaneously. Each order goes
 * through validation, payment, and shipment stages in its own thread.
 */
class OrderProcessor extends Thread {
    private String orderId;
    private String customerName;
    private String item;

    OrderProcessor(String orderId, String customerName, String item) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.item = item;
    }

    @Override
    public void run() {
        System.out.println("[" + orderId + "] " + customerName + " - Order placed: " + item);

        // Stage 1: Validation
        System.out.println("[" + orderId + "] Validating order...");
        pause(400);

        // Stage 2: Payment
        System.out.println("[" + orderId + "] Processing payment...");
        pause(600);

        // Stage 3: Packaging
        System.out.println("[" + orderId + "] Packaging order...");
        pause(500);

        // Stage 4: Shipment
        System.out.println("[" + orderId + "] Order shipped to " + customerName);
        System.out.println("[" + orderId + "] --- Order complete ---\n");
    }

    private void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.println("[" + orderId + "] Thread interrupted");
        }
    }
}

public class Ex2_OnlineOrderProcessing {
    public static void main(String[] args) {
        System.out.println("===== Online Order Processing System =====\n");

        OrderProcessor order1 = new OrderProcessor("ORD-1001", "Anita Verma", "Wireless Headphones");
        OrderProcessor order2 = new OrderProcessor("ORD-1002", "Suresh Nair", "Laptop Stand");
        OrderProcessor order3 = new OrderProcessor("ORD-1003", "Pooja Rao", "USB-C Hub");

        // Start all orders concurrently
        order1.start();
        order2.start();
        order3.start();

        // Wait for all to finish
        try {
            order1.join();
            order2.join();
            order3.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted");
        }

        System.out.println("All orders processed successfully.");
    }
}
