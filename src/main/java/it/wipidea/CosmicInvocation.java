package it.wipidea;

import java.io.IOException;

public class CosmicInvocation extends it.wipidea.MyCosmicMantra {

    public CosmicInvocation(String fileName) throws IOException, InterruptedException {
        super(fileName);
    }

    public final static void main(String[] args) throws InterruptedException, IOException {

        CosmicInvocation myMantraInstance = new CosmicInvocation( args[0] );
        myMantraInstance.startMantra();
        /*
        Thread thread = new Thread(){
            @Override
            public synchronized void start() {
                super.start();
                try {
                    myMantraInstance.startMantra();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        thread.start();
        */

    }

}
