package it.wipidea.cosmicmantra.core;

import it.wipidea.cosmicmantra.MantraCoreRunner;
import it.wipidea.cosmicmantra.gui.MantraFX;
import it.wipidea.cosmicmantra.gui.MantraFrame;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class MantraChannelManager {

    protected static final LinkedList<MantraChannel> channelList;
    private static final Integer MANTRA_WIN_MARGIN_X = 40;
    private static final Integer MANTRA_WIN_MARGIN_Y = 30;
    public static MantraChannel previouseChannel;
    public static MantraChannel activeChannel;

    static {
        channelList = new LinkedList<>();
    }
    public static void registerMantraChannel(MantraChannel mantraChannel) {
        channelList.add(mantraChannel);
        System.err.println("Mantra Channel Registered!");
    }

    private static Integer startFromX = 0;
    private static Integer startFromY = MANTRA_WIN_MARGIN_Y;

    public static void resetMantraDesktopMonoSequence() {

        int cnt = 0;

        for (MantraChannel mch : channelList) {
            mch.mantraWindow.setState(JFrame.ICONIFIED);
            cnt++;
        }
    }

    @SuppressWarnings("InfiniteRecursion")
    public static void updateMantraDesktopArlequinSequence() {

        int cnt = 0;

        //cnt = 0;
        //for (MantraChannel mch : channelList) {
        while (cnt>=0) {

            if (cnt==0) {
                for (MantraChannel mch1 : channelList) {
                    mch1.mantraWindow.setState(JFrame.ICONIFIED);
                }
            }

            MantraChannel mch = channelList.get(cnt);
            try {
                //TODO: da attivare temporizzazione in base a durata del mantra, mantenere comunque anche intervallo fissato
                if (mch.mantraInstance.MANTRA_INTERVAL_PRAYER==null)
                    Thread.sleep(7000L );
                else
                    Thread.sleep( mch.mantraInstance.MANTRA_INTERVAL_PRAYER );

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            mch.mantraWindow.setState(JFrame.NORMAL);
            cnt++;
            if (cnt>channelList.size()) {
                cnt = 0;
            }
        }

        //updateMantraDesktopArlequinSequence();

    }

    @SuppressWarnings("InfiniteRecursion")
    public static void updateMantraDesktopScroogeSequence() {

        int cnt = 0;

        for (MantraChannel mch : channelList) {
            mch.mantraWindow.setState(JFrame.ICONIFIED);
            cnt++;
        }

        for (MantraChannel mch : channelList) {
            if (activeChannel==null) {
                previouseChannel = null;
            } else {
                previouseChannel = activeChannel;
            }
            activeChannel = mch;

            try {
                Thread.sleep(7000L );
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //mch.mantraWindow.setState(JFrame.MAXIMIZED_BOTH);

            mch.mantraWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
            mch.mantraWindow.requestFocus();

//            MantraFX.startFairyLights();

            //mch.mantraWindow.requestFocusInWindow();
            cnt++;
        }

        //MantraFX.startFairyLights();

        updateMantraDesktopScroogeSequence();

    }

    public static void updateMantraDesktopPosition() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Double wWin = screenSize.getWidth();
        Double hWin = screenSize.getHeight();

        MantraCoreRunner.windowHook.setState(JFrame.ICONIFIED);

        //Integer lastX = 0;
        //Integer lastY = 0;
        Integer x = 0;
        Integer y = 0;
        Integer w = 0;
        Integer h = 0;

        Integer lastItemX = MANTRA_WIN_MARGIN_X;
        Integer lastItemW = 0;

        Integer lastItemY = MANTRA_WIN_MARGIN_Y;
        Integer itemMaxH = 0;

        int cnt = 0;

        for (MantraChannel mch : channelList) {
            // se dimensione mantra maggiore di posizione iniziale + width last
            x = mch.mantraWindow.getX();
            y = mch.mantraWindow.getY();
            w = mch.mantraWindow.getWidth();
            h = mch.mantraWindow.getHeight();

            if (cnt==0) {
                //first place
                itemMaxH = h;
            } else if (cnt==1) {
                //second place
            } else{
                //other places
            }

            if (itemMaxH<h) {
                itemMaxH=h;
            }


            startFromX = lastItemX + MANTRA_WIN_MARGIN_X;

            if ( (startFromX+w) > wWin )  {
                startFromX = MANTRA_WIN_MARGIN_X;
                startFromY = MANTRA_WIN_MARGIN_Y+startFromY+itemMaxH;
            }

            //System.out.printf("%s ==> %s : %s %s %s\n", cnt, startFromX, startFromY, w,h);

            mch.mantraWindow.setBounds(startFromX, startFromY, w,h);

            //mch.mantraWindow.setLocation(startFromX, startFromY);

            lastItemW = mch.mantraWindow.getWidth();
            lastItemX = mch.mantraWindow.getX() + lastItemW;

            //mch.mantraWindow.setVisible(true);

            cnt++;
        }

        //channelList.getLast().mantraWindow.setBounds(startFrom, lastY, w,h);

    }
}
