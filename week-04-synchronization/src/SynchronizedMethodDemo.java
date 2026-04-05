/**
 * Sample Program 1: Synchronized Method
 * Demonstrates thread-safe withdrawal from a shared BankAccount using synchronized method.
 */
class BankAccount {
    private int balance = 1000;

    synchronized void withdraw(int amount) {
        System.out.println(Thread.currentThread().getName()
                + " is trying to withdraw " + amount);

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

class Customer extends Thread {
    private BankAccount account;
    private int amount;

    Customer(BankAccount account, int amount, String name) {
        super(name);
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void run() {
        account.withdraw(amount);
    }
}

public class SynchronizedMethodDemo {
    public static void main(String[] args) {
        BankAccount account = new BankAccount();

        Customer c1 = new Customer(account, 700, "Customer-1");
        Customer c2 = new Customer(account, 500, "Customer-2");

        c1.start();
        c2.start();
    }
}
