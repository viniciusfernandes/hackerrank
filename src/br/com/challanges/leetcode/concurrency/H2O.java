package br.com.challanges.leetcode.concurrency;

class H2O {

    public H2O() {

    }

    private final Object lock = new Object();
    private volatile int hidrogen = 0;

    //oohhhh
    //hoh
    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        synchronized (lock) {
            while (hidrogen >= 2) {
                lock.wait();
            }
            hidrogen++;
            releaseHydrogen.run();
            lock.notifyAll();
        }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        synchronized (lock) {
            while (hidrogen < 2) {
                lock.wait();
            }
            releaseOxygen.run();
            hidrogen = 0;
            lock.notifyAll();
        }
    }

    public static void main(String[] args) {
        H2O h2o = new H2O();
        String molecules = "OOHHHHHHOOHH";
        for (int i = 0; i < molecules.length(); i++) {
            Runnable r;
            if (molecules.charAt(i) == 'O') {
                r = () -> {
                    try {
                        h2o.oxygen(() -> System.out.print("O"));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                };
            } else {
                r = () -> {
                    try {
                        h2o.hydrogen(() -> System.out.print("H"));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                };
            }
            Thread t = new Thread(r);
            t.start();
        }

    }

}