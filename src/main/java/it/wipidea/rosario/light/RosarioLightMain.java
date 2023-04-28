package it.wipidea.rosario.light;

import javax.swing.*;
import java.awt.*;

public class RosarioLightMain {

    private JFrame jFrame;

    public RosarioLightMain()  {
        jFrame = new JFrame("Rosario Light");
        jFrame.setSize(new Dimension(200,300));
        //jFrame.setUndecorated(false);
        jFrame.setVisible(true);
    }

    public static void main(String[] args) {
        RosarioLightMain rosarioLightMain = new RosarioLightMain();
    }

}
