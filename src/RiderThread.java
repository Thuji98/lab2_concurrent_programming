import java.util.logging.Level;
import java.util.logging.Logger;

public class RiderThread extends Thread {
    private final SharedResource sharedResource;
    private final long mean;

    public RiderThread(SharedResource sharedResource, long mean){
        this.sharedResource = sharedResource;
        this.mean = mean;
    }

    @Override
    public void run(){
        long sleepInterval = 0;
        while(true){
            sleepInterval = (long) ( -(Math.log(Math.random()))* mean);
            try {
                Rider.sleep(sleepInterval);
                new Rider(sharedResource).start();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}