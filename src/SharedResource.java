import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class SharedResource {
    private final AtomicInteger ridersWaiting = new AtomicInteger(0);    //number of riders waiting for bus in the boarding area
    private final Semaphore mutex = new Semaphore(1);   //to protect ridersCount
    private final Semaphore bus = new Semaphore(0);     //to signal the arrival of bus
    private final Semaphore boarded = new Semaphore(0);     //to signal completion of riders boarding

    public SharedResource () {
    }

    public AtomicInteger getRidersWaiting() {
        return ridersWaiting;
    }

    public Semaphore getMutex() {
        return mutex;
    }

    public Semaphore getBus() {
        return bus;
    }

    public Semaphore getBoarded() {
        return boarded;
    }
}