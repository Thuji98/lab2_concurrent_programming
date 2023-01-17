public class Main {

    //initialize the shared resource
    private static final SharedResource SHARED_RESOURCE = new SharedResource();

    public static void main(String[] args) {

        System.out.println(
                "----------------------------------" +
                "\nSenate Bus System Started !!!" +
                "\n----------------------------------\n");

        //start rider thread
        new RiderThread(SHARED_RESOURCE, 3000).start(); //inter-arrival time of riders = 30sec (default)

        //start bus thread
        new BusThread(SHARED_RESOURCE, 120000).start(); //inter-arrival time of busses = 20min (default)
    }
}
