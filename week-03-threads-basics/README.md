# Week 3: Multithreaded Programming – Basics

**Status:** Pending

## Objective

To understand the concept of multithreading and thread lifecycle in Java.

## Topics Covered

- Thread creation using `Thread` class
- Thread creation using `Runnable` interface
- Thread lifecycle
- `sleep()`, `join()`, `yield()`

## Lab Exercises

1. Design and implement a Java application to simulate a Hotel Room Service Management System where multiple service requests are handled concurrently using multithreading.

   In a hotel, different room service tasks such as room cleaning, food delivery, and maintenance may occur at the same time. To efficiently manage these tasks, the application should create separate threads for each service request so that they can execute concurrently rather than sequentially.

   Create individual threads for different service operations using Java thread creation techniques (`Thread` class or `Runnable` interface). Each thread should simulate a service task by displaying status messages and pausing execution using the `sleep()` method to represent processing time.

   The main program should start multiple threads simultaneously and demonstrate concurrent execution of hotel service tasks.

2. Design and implement a Java application to simulate an Online Order Processing System where multiple customer orders are processed simultaneously using multithreading.

   In an e-commerce platform, several operations such as order validation, payment processing, and order shipment must be handled concurrently for different customers. To improve system performance and responsiveness, each order processing task should be executed in a separate thread.

   Create individual threads for handling different customer orders or different stages of order processing. Each thread should simulate processing by displaying status messages and using the `sleep()` method to represent time-consuming operations.

   The main program should start multiple threads at the same time and demonstrate concurrent execution of order-related tasks.
