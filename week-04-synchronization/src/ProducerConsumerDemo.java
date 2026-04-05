/**
 * Sample Program 3: Inter-thread Communication — Producer-Consumer
 * Demonstrates wait() and notify() for coordinating producer and consumer threads.
 */
class Buffer {
    private int data;
    private boolean available = false;

    synchronized void produce(int value) {
        while (available) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Producer interrupted");
            }
        }

        data = value;
        available = true;
        System.out.println("Produced: " + data);
        notify();
    }

    synchronized int consume() {
        while (!available) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Consumer interrupted");
            }
        }

        available = false;
        System.out.println("Consumed: " + data);
        notify();
        return data;
    }
}

class Producer extends Thread {
    private Buffer buffer;

    Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            buffer.produce(i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Producer sleep interrupted");
            }
        }
    }
}

class Consumer extends Thread {
    private Buffer buffer;

    Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            buffer.consume();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Consumer sleep interrupted");
            }
        }
    }
}

public class ProducerConsumerDemo {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();

        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);

        producer.start();
        consumer.start();
    }
}
