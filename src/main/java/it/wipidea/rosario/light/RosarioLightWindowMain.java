package it.wipidea.rosario.light;

import javax.swing.*;
import java.awt.*;

public class RosarioLightWindowMain {

    private JFrame jFrame;

    public RosarioLightWindowMain()  {
        jFrame = new JFrame("Rosario Light");
        jFrame.setPreferredSize(new Dimension(180,180));
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

        RosarioSymbolPanel panel = new RosarioSymbolPanel();

        RosarioManualButtonPanel rosarioManualButtonPanel = new RosarioManualButtonPanel(panel);
        jFrame.getContentPane().add(rosarioManualButtonPanel, BorderLayout.NORTH);

        jFrame.getContentPane().add(panel, BorderLayout.CENTER);

        RosarioButtonPanel rosarioButtonPanel = new RosarioButtonPanel(panel);
        jFrame.getContentPane().add(rosarioButtonPanel, BorderLayout.SOUTH);


        jFrame.pack();
        jFrame.setVisible(true);
    }

    public static void main(String[] args) {
        RosarioLightWindowMain rosarioLightMain = new RosarioLightWindowMain();
    }

}
