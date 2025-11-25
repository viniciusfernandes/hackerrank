package br.com.challanges.leetcode.concurrency;

class FooBar {
    private final int n;

    public FooBar(int n) {
        this.n = n;
    }

    private final Object lock = new Object();
    private volatile boolean isFoo = true;

    public void foo(Runnable printFoo) throws InterruptedException {
        synchronized (lock) {
            for (int i = 0; i < n; i++) {
                while (!isFoo) {
                    lock.wait();
                }
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                isFoo = false;
                lock.notifyAll();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        synchronized (lock) {
            for (int i = 0; i < n; i++) {
                while (isFoo) {
                    lock.wait();
                }
                // printBar.run() outputs "bar". Do not change or remove this line.
                printBar.run();
                isFoo = true;
                lock.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        FooBar fooBar = new FooBar(2);
        Runnable foo = () -> {
            try {
                fooBar.foo(() -> System.out.print("foo"));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        Runnable bar = () -> {
            try {
                fooBar.bar(() -> System.out.print("bar"));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Thread tFoo = new Thread(foo);
        Thread tBar = new Thread(bar);
        tFoo.start();
        tBar.start();
    }
}