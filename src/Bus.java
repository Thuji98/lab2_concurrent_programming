import java.util.concurrent.atomic.AtomicInteger;

public class Bus extends Thread{

    //generate bus id
    private final int busId;
    private static final AtomicInteger count = new AtomicInteger(0);
    private int generateBusId() {
        return count.incrementAndGet();
    }

    private final SharedResource sharedResource;

    //maximum number of passengers, a bus can board (capacity)
    private final int MAX_BUS_SEATS = 50;

    public Bus(SharedResource sharedResource) {
        this.busId = generateBusId();
        this.sharedResource = sharedResource;
    }

    public void depart() {
        System.out.println("\nRiders boarded");
        System.out.println("BUS-" + busId + " DEPARTS!!!\n");
    }

    @Override
    public void run(){
        try {
            //Bus waits to acquire mutex so that it can start boarding riders
            sharedResource.getMutex().acquire();
            System.out.println("\nBUS-" + busId + " ARRIVES!!!");

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        //A bus can only board maximum of 50 riders. Others need to wait for the arrival of next bus
        int boardCount = Math.min(sharedResource.getRidersWaiting().get(), MAX_BUS_SEATS);
        System.out.println("Count of riders waiting in the board area - " + sharedResource.getRidersWaiting().get());
        System.out.println("Count of riders able to board into bus - " + boardCount + "\n");

        //Loop signals each rider in turn and waits until the rider get boarded
        //DO NOT ALLOW MORE THAN 50 RIDERS TO BOARD
        for(int i=0; i < boardCount; i++){
            sharedResource.getBus().release();  //signals each rider that they can board into bus

            try {
                sharedResource.getBoarded().acquire();  //bus signals to say rider has boarded
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int waitingRiders = sharedResource.getRidersWaiting().get();    //number of riders waiting for bus
        sharedResource.getRidersWaiting().set(Math.max(waitingRiders - 50, 0));

        depart();

        sharedResource.getMutex().release();    //remaining riders come to stop and wait for bus

    }
}