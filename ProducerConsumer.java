import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

class ProducerConsumer {
    private static final int BUFFER_SIZE = 5; // Buffer size
    private final Queue<Integer> buffer = new LinkedList<>(); // Shared buffer

    // Semaphores for synchronization
    private final Semaphore mutex = new Semaphore(1); // Mutex for critical sections
    private final Semaphore empty = new Semaphore(BUFFER_SIZE); // Number of empty slots
    private final Semaphore full = new Semaphore(0); // Number of full slots

    // Producer method to add items to the buffer
    public void produce() throws InterruptedException {
        while (true) {
            int item = (int) (Math.random() * 100); // Generate a random item

            // Simulate random delay before producing
            Thread.sleep((int) (Math.random() * 800)); 
            empty.acquire(); // Wait if the buffer is full
            mutex.acquire(); // Enter critical section, wait if mutex = 0

            buffer.add(item); // Add item to the buffer      // critical section
            System.out.println("Producer produced: " + item); // critical section

            mutex.release(); // Exit critical section
            full.release(); // Signal that an item has been produced

            Thread.sleep(1000); // Simulate time taken to produce an item
        }
    }

    // Consumer method to remove items from the buffer
    public void consume() throws InterruptedException {
        while (true) {
            // Simulate random delay before consuming
            Thread.sleep((int) (Math.random() * 1000)); 
            full.acquire(); // Wait if the buffer is empty
            mutex.acquire(); // Enter critical section

            int item = buffer.peek(); // Remove item from the buffer
            System.out.println("Consumer consumed: " + item);

            mutex.release(); // Exit critical section
            empty.release(); // Signal that an item has been consumed

            buffer.poll();
            Thread.sleep(995); // Simulate time taken to consume an item
        }
    }

    // Main method
    public static void main(String[] args) {
        ProducerConsumer pc = new ProducerConsumer();

        // Producer thread
        Thread producerThread = new Thread(() -> {
            try {
                pc.produce();
            } catch (InterruptedException e) {
                
            }
        });

        // Consumer thread
        Thread consumerThread = new Thread(() -> {
            try {
                pc.consume();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Start the threads
        producerThread.start();
        consumerThread.start();
    }
}
