package Task2;

import java.util.function.IntConsumer;

public class FizzBuzzMultithreaded {
    private int n;
    private int currentNumber = 1;
    private final Object lock = new Object();

    public FizzBuzzMultithreaded(int n) {
        this.n = n;
    }
    
    public void fizz(Runnable printFizz) throws InterruptedException {
        synchronized (lock) {
            while (currentNumber <= n) {
                while (currentNumber <= n && (currentNumber % 3 != 0 || currentNumber % 5 == 0)) {
                    lock.wait();
                }
                if (currentNumber <= n) {
                    printFizz.run();
                    currentNumber++;
                    lock.notifyAll();
                }
            }
        }
    }

    public void buzz(Runnable printBuzz) throws InterruptedException {
        synchronized (lock) {
            while (currentNumber <= n) {
                while (currentNumber <= n && (currentNumber % 5 != 0 || currentNumber % 3 == 0)) {
                    lock.wait();
                }
                if (currentNumber <= n) {
                    printBuzz.run();
                    currentNumber++;
                    lock.notifyAll();
                }
            }
        }
    }

    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        synchronized (lock) {
            while (currentNumber <= n) {
                while (currentNumber <= n && (currentNumber % 3 != 0 || currentNumber % 5 != 0)) {
                    lock.wait();
                }
                if (currentNumber <= n) {
                    printFizzBuzz.run();
                    currentNumber++;
                    lock.notifyAll();
                }
            }
        }
    }

    public void number(IntConsumer printNumber) throws InterruptedException {
        synchronized (lock) {
            while (currentNumber <= n) {
                while (currentNumber <= n && (currentNumber % 3 == 0 || currentNumber % 5 == 0)) {
                    lock.wait();
                }
                if (currentNumber <= n) {
                    printNumber.accept(currentNumber);
                    currentNumber++;
                    lock.notifyAll();
                }
            }
        }
    }

    public static void main(String[] args) {
        int n = 15;
        FizzBuzzMultithreaded fizzBuzz = new FizzBuzzMultithreaded(n);

        Thread threadA = new Thread(() -> {
            try {
                fizzBuzz.fizz(() -> System.out.print("fizz, "));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                fizzBuzz.buzz(() -> System.out.print("buzz, "));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread threadC = new Thread(() -> {
            try {
                fizzBuzz.fizzbuzz(() -> System.out.print("fizzbuzz, "));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread threadD = new Thread(() -> {
            try {
                fizzBuzz.number(number -> System.out.print(number + ", "));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

        try {
            threadA.join();
            threadB.join();
            threadC.join();
            threadD.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\b\b");
    }
}
