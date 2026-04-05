# Week 2: Java Library – Wrapper Classes, Enumeration, and Autoboxing

**Status:** Pending

## Objective

To understand Java wrapper classes, enumerations, autoboxing, and unboxing.

## Topics Covered

- Wrapper classes (Integer, Double, Character, etc.)
- Autoboxing and Unboxing
- Enumeration (`enum`)
- Enum methods and constructors

## Lab Exercises

1. The Hotel Billing system should calculate the total bill amount for hotel guests based on room charges and additional service charges. Store numeric values such as room tariff, number of days stayed, and service charges using wrapper class objects (`Integer`, `Double`) instead of primitive data types.

   Demonstrate autoboxing by automatically converting primitive values to wrapper class objects when assigning values or storing them in collections. Demonstrate unboxing by automatically converting wrapper class objects back to primitive types while performing arithmetic operations for bill calculation.

   Create a main class to:
   - Initialize room tariff and number of days using primitive data types and store them in wrapper objects.
   - Perform total bill calculation using unboxed primitive values.
   - Display the final hotel bill.

2. Design and implement a Java application to manage room tariff details in a Hotel Management System using Java enumerations (`enum`). The application should demonstrate the use of enum constants, enum constructors, and enum methods.

   Define an enum named `RoomType` to represent different types of hotel rooms such as STANDARD, DELUXE, and SUITE. Each enum constant should be associated with a base tariff value using an enum constructor. The enum should also include methods to return the base tariff and to calculate the total room cost based on the number of days stayed.

   Create a main class to select a room type, specify the number of days of stay, and compute the total room tariff by invoking the enum methods. The application should clearly illustrate how enum constructors are used to initialize constant-specific data and how enum methods operate on that data.

## Course Outcome

Students will be able to apply wrapper classes, autoboxing, unboxing, enum methods, and constructors.
