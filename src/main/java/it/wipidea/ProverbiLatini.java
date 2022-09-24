package it.wipidea;

import java.awt.*;
import java.io.IOException;

public class ProverbiLatini extends it.wipidea.MyCosmicMantra {

    public ProverbiLatini(String fileName) throws IOException, InterruptedException {
        super(fileName);
    }

    public final static void main(String[] args) throws InterruptedException, IOException {
       // System.setProperty( "inputFile", args[0] );

        //ProverbiLatini myMantraInstance = new ProverbiLatini( System.getProperty("inputFile") );
        ProverbiLatini myMantraInstance = new ProverbiLatini( args[0] );
//        myMantraInstance.startMantra();
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



        System.out.println("STARTED");

        //MantraPanelUI window = new MantraPanelUI(thread);

        //window.communicatesMessage("This is a test");

        thread.start();

    }

}
