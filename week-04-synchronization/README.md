# Week 4: Multithreaded Programming – Synchronization

**Status:** Completed

## Objective

To study thread synchronization and inter-thread communication.

## Topics Covered

- Synchronized methods
- Synchronized blocks
- Deadlock
- Inter-thread communication (`wait()`, `notify()`)

## Sample Programs

| File | Concept |
|------|---------|
| `SynchronizedMethodDemo.java` | `synchronized` method prevents concurrent withdrawal from shared BankAccount |
| `SynchronizedBlockDemo.java` | Same scenario using a `synchronized` block instead of a method |
| `ProducerConsumerDemo.java` | `wait()` and `notify()` coordinate a producer and consumer sharing a buffer |
| `DeadlockDemo.java` | Two threads lock resources in opposite order, causing a deadlock (hangs intentionally) |

## Lab Exercises

### Exercise 1 — Hotel Room Booking with Synchronization
**File:** `Ex1_HotelRoomBookingSync.java`

Design and implement a Java-based hotel room management application that simulates concurrent room booking and room release operations using multiple threads. The system must ensure data consistency when multiple customers attempt to book or release rooms simultaneously. A hotel has a limited number of rooms. Multiple customer threads attempt to book rooms at the same time. If no rooms are available, the booking thread must wait. When a room is released by another thread, the waiting booking thread must be notified and allowed to proceed.

## How to Run

```bash
cd week-04-synchronization
javac src/*.java
```

```bash
java -cp src SynchronizedMethodDemo
java -cp src SynchronizedBlockDemo
java -cp src ProducerConsumerDemo
java -cp src DeadlockDemo          # Will hang — press Ctrl+C to stop
java -cp src Ex1_HotelRoomBookingSync
```

Note: `DeadlockDemo` hangs by design to demonstrate deadlock. Terminate it manually with Ctrl+C after observing the "waiting for" messages.
