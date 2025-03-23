package br.com.challanges;

class SharedResource {
    private int counter = 0;

    // Synchronized method to ensure only one thread updates the counter at a time
    public  void increment() {
        synchronized(Thread.currentThread().getName()){
            counter++;
        }

        System.out.println(Thread.currentThread().getName() + " - Counter: " + counter);
    }
}

public class SynchronizedExample {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        // Creating multiple threads that access the shared resource
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                resource.increment();
            }
        }, "Thread 1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                resource.increment();
            }
        }, "Thread 2");

        t1.start();
        t2.start();
    }
}
