public class BusThread extends Thread {

    private final SharedResource sharedResource;
    private final long mean;

    public BusThread(SharedResource sharedResource, long mean){
        this.sharedResource = sharedResource;
        this.mean = mean;
    }

    @Override
    public void run(){
        long sleepInterval = 0;
        while(true){
            sleepInterval = (long) ( -(Math.log(Math.random()))* mean);
            try {
                Bus.sleep(sleepInterval);
                new Bus(sharedResource).start();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}