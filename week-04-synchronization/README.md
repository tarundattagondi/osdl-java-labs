# Week 4: Multithreaded Programming – Synchronization

**Status:** Pending

## Objective

To study thread synchronization and inter-thread communication.

## Topics Covered

- Synchronized methods
- Synchronized blocks
- Deadlock
- Inter-thread communication (`wait()`, `notify()`)

## Lab Exercises

1. Design and implement a Java-based hotel room management application that simulates concurrent room booking and room release operations using multiple threads. The system must ensure data consistency when multiple customers attempt to book or release rooms simultaneously.

   A hotel has a limited number of rooms. Multiple customer threads attempt to book rooms at the same time. If no rooms are available, the booking thread must wait. When a room is released by another thread, the waiting booking thread must be notified and allowed to proceed.
