import java.util.concurrent.atomic.AtomicInteger;

public class Rider extends Thread{

    //generate rider id
    private final int riderId;
    private static final AtomicInteger count = new AtomicInteger(0);
    private int generateRiderId() {
        return count.incrementAndGet();
    }

    private final SharedResource sharedResource;

    public Rider(SharedResource sharedResource) {
        this.riderId = generateRiderId();
        this.sharedResource = sharedResource;
    }

    public void boardBus() {
        System.out.println("Rider-" + riderId +" boards into bus");
    }

    public void run(){
        try {
            sharedResource.getMutex().acquire();    //a new rider comes to the bus stop and waits at the mutex
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        sharedResource.getRidersWaiting().incrementAndGet();  //a new rider joins the waiting queue
        sharedResource.getMutex().release();
        System.out.println("Rider-" + riderId + " is waiting for bus" );

        try {
            sharedResource.getBus().acquire();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        boardBus();

        sharedResource.getBoarded().release();
    }
}