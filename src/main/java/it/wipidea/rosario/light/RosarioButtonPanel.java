package it.wipidea.rosario.light;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RosarioButtonPanel extends JPanel implements IButtonCaller {

    public RosarioButtonPanel(IPanelCaller panelCaller) {


        JButton jbAdd = new JButton("Prega");
        jbAdd.setToolTipText("Recita preghiera");
        jbAdd.setActionCommand("Praying");
        jbAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panelCaller.recitaPreghieraAutomatica();
            }
        });

        add(jbAdd);

        JButton jbClose = new JButton("CLOSE");
        jbClose.setActionCommand("Close");
        jbClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        add(jbClose);


    }

}
