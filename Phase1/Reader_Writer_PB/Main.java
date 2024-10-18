package Reader_Writer_PB;

import java.util.concurrent.locks.ReentrantLock;

class ReaderWriterProblem {
    private int readCount = 0;          // Number of active readers
    private int sharedData = 10;         // Shared resource
    private final ReentrantLock mutex = new ReentrantLock();       // Mutex to protect readCount
    private final ReentrantLock writeLock = new ReentrantLock();   // Lock to give exclusive access to the writer

    // Reader method
    public void read(int readerId) {
        try {
            mutex.lock();     // Acquire mutex lock to update readCount
            readCount++;
            if (readCount == 1) {
                writeLock.lock();  // First reader locks the write lock
            }
            mutex.unlock();   // Release mutex after updating readCount

            // Reading section
            System.out.println("Reader " + readerId + " is reading the shared data: " + sharedData);
            Thread.sleep(1000);  // Simulate reading time

            mutex.lock();    // Acquire mutex lock to update readCount after reading
            readCount--;
            if (readCount == 0) {
                writeLock.unlock();  // Last reader unlocks the write lock
            }
            mutex.unlock();   // Release mutex lock

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Writer method
    public void write(int writerId) {
        try {
            writeLock.lock();  // Writer locks the write lock

            // Writing section
            sharedData++;
            System.out.println("Writer " + writerId + " is writing new data: " + sharedData);
            Thread.sleep(1000);  // Simulate writing time

            writeLock.unlock();  // Writer unlocks the write lock

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Reader extends Thread {
    private ReaderWriterProblem rw;
    private int readerId;

    public Reader(ReaderWriterProblem rw, int readerId) {
        this.rw = rw;
        this.readerId = readerId;
    }

    public void run() {
        rw.read(readerId);
    }
}

class Writer extends Thread {
    private ReaderWriterProblem rw;
    private int writerId;

    public Writer(ReaderWriterProblem rw, int writerId) {
        this.rw = rw;
        this.writerId = writerId;
    }

    public void run() {
        rw.write(writerId);
    }
}

public class Main {
    public static void main(String[] args) {
        ReaderWriterProblem rw = new ReaderWriterProblem();

        // Create reader and writer threads
        Reader[] readers = new Reader[5];
        Writer[] writers = new Writer[3];

        for (int i = 0; i < 5; i++) {
            readers[i] = new Reader(rw, i + 1);
            readers[i].start();
        }

        for (int i = 0; i < 3; i++) {
            writers[i] = new Writer(rw, i + 1);
            writers[i].start();
        }

        // Wait for all threads to finish
        for (int i = 0; i < 5; i++) {
            try {
                readers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 3; i++) {
            try {
                writers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

