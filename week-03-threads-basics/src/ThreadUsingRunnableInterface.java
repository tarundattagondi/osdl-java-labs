/**
 * Sample Program 2: Thread creation using Runnable interface
 * Demonstrates concurrent room cleaning via Runnable implementation.
 */
class RoomCleaningTask implements Runnable {
    private String roomName;

    RoomCleaningTask(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(roomName + " - Cleaning step " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }
        }
    }
}

public class ThreadUsingRunnableInterface {
    public static void main(String[] args) {
        RoomCleaningTask task1 = new RoomCleaningTask("Room 201");
        RoomCleaningTask task2 = new RoomCleaningTask("Room 202");

        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);

        t1.start();
        t2.start();
    }
}
