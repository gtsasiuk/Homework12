package Task1;

import java.time.Duration;
import java.time.Instant;

public class TimeTracker {
    public static void main(String[] args) {
        Instant startTime = Instant.now();

        Thread timeThread = new Thread(() -> {
            while (true) {
                Duration duration = Duration.between(startTime, Instant.now());
                long seconds = duration.getSeconds();
                System.out.println("Час, що минув: " + seconds + " секунд");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        Thread messageThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                    System.out.println("Минуло 5 секунд.");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        timeThread.start();
        messageThread.start();
    }
}

