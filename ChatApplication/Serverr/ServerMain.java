

public class ServerMain {

    public static void main(String[] args) {
        Server s = new Server(); //it will invoke the GUI part
        s.waitForCleint();       // it will wait for the client
        s.setIOStream();         // it will set the Streams through which we will transfer the data 

        
    }
}