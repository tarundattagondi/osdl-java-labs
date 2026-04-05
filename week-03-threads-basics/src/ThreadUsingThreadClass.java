/**
 * Sample Program 1: Thread creation by extending Thread class
 * Demonstrates concurrent room cleaning tasks using two threads.
 */
class RoomCleaningThread extends Thread {
    private String roomName;

    RoomCleaningThread(String roomName) {
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

public class ThreadUsingThreadClass {
    public static void main(String[] args) {
        RoomCleaningThread t1 = new RoomCleaningThread("Room 101");
        RoomCleaningThread t2 = new RoomCleaningThread("Room 102");

        t1.start();
        t2.start();
    }
}
