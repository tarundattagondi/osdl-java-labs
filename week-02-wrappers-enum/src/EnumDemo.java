/**
 * Sample Program 3: Enumeration (enum)
 * Demonstrates enum declaration, switch on enum, and iterating with values().
 */

// Enum declaration
enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

public class EnumDemo {
    public static void main(String[] args) {

        // Using enum
        Day today = Day.FRIDAY;
        System.out.println("Today is: " + today);

        // Using enum in switch statement
        switch (today) {
            case MONDAY:
                System.out.println("Start of the work week");
                break;
            case FRIDAY:
                System.out.println("Almost weekend!");
                break;
            case SATURDAY:
            case SUNDAY:
                System.out.println("Weekend");
                break;
            default:
                System.out.println("Midweek day");
        }

        // Iterating through enum values
        System.out.println("\nAll Days:");
        for (Day d : Day.values()) {
            System.out.println(d);
        }
    }
}
