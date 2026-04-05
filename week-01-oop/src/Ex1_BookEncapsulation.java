/**
 * Exercise 1: Book Class with Encapsulation
 * Concept: Private data members, public getters/setters, validation.
 *
 * Create a Book class with private fields (bookId, title, author, price,
 * availability). Validate that price is positive in the setter.
 */
class Book {
    private int bookId;
    private String title;
    private String author;
    private double price;
    private boolean available;

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(double price) {
        if (price > 0) {
            this.price = price;
        } else {
            System.out.println("Invalid price. Price must be a positive value.");
        }
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void displayDetails() {
        System.out.println("Book ID      : " + bookId);
        System.out.println("Title        : " + title);
        System.out.println("Author       : " + author);
        System.out.println("Price        : " + price);
        System.out.println("Available    : " + (available ? "Yes" : "No"));
    }
}

public class Ex1_BookEncapsulation {
    public static void main(String[] args) {
        Book book1 = new Book();
        book1.setBookId(101);
        book1.setTitle("Hotel Management Fundamentals");
        book1.setAuthor("Priya Sharma");
        book1.setPrice(450.0);
        book1.setAvailable(true);

        Book book2 = new Book();
        book2.setBookId(102);
        book2.setTitle("Front Desk Operations");
        book2.setAuthor("Rajesh Patel");
        book2.setPrice(320.0);
        book2.setAvailable(false);

        System.out.println("===== Book 1 =====");
        book1.displayDetails();

        System.out.println("\n===== Book 2 =====");
        book2.displayDetails();

        // Demonstrate validation: negative price
        System.out.println("\nAttempting to set negative price on Book 1:");
        book1.setPrice(-100.0);
        System.out.println("Book 1 price unchanged: " + book1.getPrice());
    }
}
