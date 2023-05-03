package it.wipidea.rosario.light;

import javax.swing.*;
import java.awt.*;

public class RosarioLightWindowMain {

    public static RosarioManualButtonPanel instanceRosarioManualButton;
    public static RosarioButtonPanel instanceRosarioButton;
    public static RosarioSymbolPanel instanceRosarioSymbol;

    public static Boolean activateSingleLine = true;

    public JFrame jFrame;

    public RosarioLightWindowMain()  {
        jFrame = new JFrame("Rosario Light");
        jFrame.setPreferredSize(new Dimension(280,180));
        jFrame.setLocation(350, 250);
        jFrame.setUndecorated(true);

        Thread tExit = new Thread() {
            @Override
            public void run() {
                super.run();
                System.out.println("Exiting!");
            }
        };

        Runtime.getRuntime().addShutdownHook(tExit);

        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        RosarioSymbolPanel panel = instanceRosarioSymbol = new RosarioSymbolPanel();

        RosarioManualButtonPanel rosarioManualButtonPanel = instanceRosarioManualButton = new RosarioManualButtonPanel(panel);
        jFrame.getContentPane().add(rosarioManualButtonPanel, BorderLayout.NORTH);

        jFrame.getContentPane().add(panel, BorderLayout.CENTER);

        RosarioButtonPanel rosarioButtonPanel = instanceRosarioButton = new RosarioButtonPanel(panel);
        jFrame.getContentPane().add(rosarioButtonPanel, BorderLayout.SOUTH);


        jFrame.pack();
        jFrame.setVisible(true);
    }

    public static void main(String[] args) {
        RosarioLightWindowMain rosarioLightMain = new RosarioLightWindowMain();
    }

}
