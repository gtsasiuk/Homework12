package Task2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FizzBuzzMultithreaded {
    private int n;
    private BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public FizzBuzzMultithreaded(int n) {
        this.n = n;
    }

    public void fizz() throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 != 0) {
                queue.put("fizz");
            }
        }
    }

    public void buzz() throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if (i % 5 == 0 && i % 3 != 0) {
                queue.put("buzz");
            }
        }
    }

    public void fizzbuzz() throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                queue.put("fizzbuzz");
            }
        }
    }

    public void number() throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            String result;
            if (i % 3 == 0 && i % 5 == 0) {
                result = queue.take();
            } else if (i % 3 == 0) {
                result = queue.take();
            } else if (i % 5 == 0) {
                result = queue.take();
            } else {
                result = Integer.toString(i);
            }
            System.out.print(result + (i < n ? ", " : ""));
        }
    }

    public static void main(String[] args) {
        int n = 15;
        FizzBuzzMultithreaded fizzBuzz = new FizzBuzzMultithreaded(n);

        Thread threadA = new Thread(() -> {
            try {
                fizzBuzz.fizz();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                fizzBuzz.buzz();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread threadC = new Thread(() -> {
            try {
                fizzBuzz.fizzbuzz();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread threadD = new Thread(() -> {
            try {
                fizzBuzz.number();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();
    }
}