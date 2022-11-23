package it.wipidea.cosmicmantra.core;


import it.wipidea.cosmicmantra.gui.MantraFX;
import it.wipidea.cosmicmantra.gui.MantraFrame;
import it.wipidea.cosmicmantra.core.objects.MantraWord;

import java.util.Vector;

public class MantraChannel {

    // on / off canale in shell
    // switch canale su apposita versione (distro grid 2D, mono sequence 2D, 3D view sequencer, Swing Panels)

    public MantraFrame mantraWindow;

    public MyCosmicMantraCore mantraInstance;

    public MantraChannel(MyCosmicMantraCore myCosmicMantraCore) {
        mantraInstance = myCosmicMantraCore;
    }

    public void initMantraChannel(String fileName, Vector<MantraWord> vMantraWords, Integer limit_for_mantra_keyword, long mantra_interval_keyword) {

        //mantraWindow = new MantraDrawerGUI(fileName, vMantraLines, limit_for_mantra_keyword, mantra_interval_keyword);
        mantraWindow = new MantraFrame(fileName, vMantraWords, limit_for_mantra_keyword, mantra_interval_keyword);

        mantraWindow.setBg( mantraInstance.PROPS.getProperty("BG", "155,0,155") );
        //mantraWindow.setFg( mantraInstance.PROPS.getProperty("FG", "30,0,30") );
        mantraWindow.setFg( "0,255,255" );

        mantraWindow.canvasBuffer.setBg( mantraInstance.PROPS.getProperty("BG", "155,0,155") );
        //mantraWindow.canvasBuffer.setFg( mantraInstance.PROPS.getProperty("FG", "30,0,30") );
        mantraWindow.canvasBuffer.setFg( "255,255,0" );
        mantraWindow.canvasBuffer.posXBuffer = 20;//mantraWindow.posXBuffer;
        mantraWindow.canvasBuffer.posYBuffer = mantraWindow.posYBuffer;

        MantraChannelManager.registerMantraChannel(this);

    }

    public void communicateMessage(int row, MantraWord mw, Integer limit_for_mantra_keyword) {
        if (true) {
            //LOG CAOS: System.out.println(message);
        }
        if (mantraInstance.PROPS.containsKey("dontReadBackAgain")) {
            if ( mantraInstance.PROPS.getProperty("dontReadBackAgain").equals("byStep") ) {
                System.out.println("ByStep READ");
                mantraWindow.communicateMessage2(row, limit_for_mantra_keyword, mw);
            } else {
                System.out.println("NOT_ByStep READ");
                mantraWindow.communicateMessage3(row, limit_for_mantra_keyword, mw);
            }
        } else {
            mantraWindow.communicateMessage1(row, limit_for_mantra_keyword, mw);
        }

        /*try {
            Thread.sleep(INTERVAL_KEYWORD);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/

        //ONLY FOR SCROOGE
        MantraFX.startFairyLights();

    }
}

