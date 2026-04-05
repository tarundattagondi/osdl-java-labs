/**
 * Sample Program 3: Thread methods — sleep(), join(), yield()
 * Demonstrates sequential execution via join() and cooperative scheduling via yield().
 */
class BookingThread extends Thread {
    private String taskName;

    BookingThread(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(taskName + " - Processing step " + i);

            Thread.yield();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }
        }
        System.out.println(taskName + " completed.");
    }
}

public class ThreadMethodsDemo {
    public static void main(String[] args) {
        BookingThread t1 = new BookingThread("Room Booking");
        BookingThread t2 = new BookingThread("Payment Processing");

        t1.start();

        try {
            // join() makes main thread wait until t1 completes
            t1.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted");
        }

        // Start second thread after t1 finishes
        t2.start();

        System.out.println("Main thread completed.");
    }
}
