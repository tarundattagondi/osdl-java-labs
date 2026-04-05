/**
 * Sample Program 2: Synchronized Block
 * Demonstrates thread-safe withdrawal using a synchronized block instead of a synchronized method.
 */
class BankAccountBlock {
    private int balance = 1000;

    void withdraw(int amount) {
        System.out.println(Thread.currentThread().getName()
                + " is trying to withdraw " + amount);

        synchronized (this) {
            if (balance >= amount) {
                System.out.println(Thread.currentThread().getName()
                        + " is processing withdrawal...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted");
                }
                balance -= amount;
                System.out.println(Thread.currentThread().getName()
                        + " completed withdrawal. Remaining balance: " + balance);
            } else {
                System.out.println(Thread.currentThread().getName()
                        + " - Insufficient balance!");
            }
        }
    }
}

class CustomerBlock extends Thread {
    private BankAccountBlock account;
    private int amount;

    CustomerBlock(BankAccountBlock account, int amount, String name) {
        super(name);
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void run() {
        account.withdraw(amount);
    }
}

public class SynchronizedBlockDemo {
    public static void main(String[] args) {
        BankAccountBlock account = new BankAccountBlock();

        CustomerBlock c1 = new CustomerBlock(account, 700, "Customer-1");
        CustomerBlock c2 = new CustomerBlock(account, 500, "Customer-2");

        c1.start();
        c2.start();
    }
}
