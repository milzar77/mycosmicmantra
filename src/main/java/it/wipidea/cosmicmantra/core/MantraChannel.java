package it.wipidea.cosmicmantra.core;


import it.wipidea.cosmicmantra.controller.ASingleController;
import it.wipidea.cosmicmantra.gui.MantraFX;
import it.wipidea.cosmicmantra.gui.MantraFrame;
import it.wipidea.cosmicmantra.core.objects.MantraWord;

import java.util.Vector;

public class MantraChannel {

    // on / off canale in shell
    // switch canale su apposita versione (distro grid 2D, mono sequence 2D, 3D view sequencer, Swing Panels)

    public MantraFrame mantraWindow;

    public ASingleController mantraSingleControllerInstance;

    public MantraChannel(ASingleController aSingleController) {
        mantraSingleControllerInstance = aSingleController;
    }

    public void initMantraChannel(String fileName, Vector<MantraWord> vMantraWords, Integer limit_for_mantra_keyword, long mantra_interval_keyword) {

        //mantraWindow = new MantraDrawerGUI(fileName, vMantraLines, limit_for_mantra_keyword, mantra_interval_keyword);
        mantraWindow = new MantraFrame(fileName, vMantraWords, limit_for_mantra_keyword, mantra_interval_keyword);

        mantraWindow.setBg( mantraSingleControllerInstance.PROPS.getProperty("BG", "155,0,155") );
        //mantraWindow.setFg( mantraInstance.PROPS.getProperty("FG", "30,0,30") );
        mantraWindow.setFg( "0,255,255" );

        mantraWindow.canvasBuffer.setBg( mantraSingleControllerInstance.PROPS.getProperty("BG", "155,0,155") );
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
        if (mantraSingleControllerInstance.PROPS.containsKey("dontReadBackAgain")) {
            if ( mantraSingleControllerInstance.PROPS.getProperty("dontReadBackAgain").equals("bySingleStep") ) {
                System.out.printf("ByStep READ: %s\n", row);
                mantraWindow.communicateMessage2(row, limit_for_mantra_keyword, mw);
            } else if ( mantraSingleControllerInstance.PROPS.getProperty("dontReadBackAgain").equals("byLimitedStep") ) {
                System.out.printf("NOT_ByStep READ: %s\n", row);
                mantraWindow.canvasBuffer = null;
                mantraWindow.communicateMessage3(row, limit_for_mantra_keyword, mw);
            } else {
                mantraWindow.communicateMessage1(row, limit_for_mantra_keyword, mw);
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

