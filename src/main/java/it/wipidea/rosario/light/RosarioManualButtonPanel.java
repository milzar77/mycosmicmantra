package it.wipidea.rosario.light;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RosarioManualButtonPanel extends JPanel implements IButtonCaller {

    public RosarioManualButtonPanel(IPanelCaller panelCaller) {
        JButton jbAddPaterNoster = new JButton(ERosarioSymbol.SYM_PATER.textSymbol);
        jbAddPaterNoster.setToolTipText(ERosarioSymbol.SYM_PATER.textPrayerTooltip);
        jbAddPaterNoster.setActionCommand("Praying Pater Noster");
        jbAddPaterNoster.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //System.out.println( String.format("%s", actionEvent.getActionCommand()) );
                panelCaller.recitaPreghiera(ERosarioSymbol.SYM_PATER.textSymbol);
            }
        });

        JButton jbAddAveMater = new JButton(ERosarioSymbol.SYM_AVE.textSymbol);// u2605 6
        //JButton jbAddAveMater0 = new JButton("\u00b7");// \u2022 \u2219  \u2206 07  \u25cf \u25c9 \u25d0 \u25d6 \u25d8
        jbAddAveMater.setToolTipText(ERosarioSymbol.SYM_AVE.textPrayerTooltip);
        jbAddAveMater.setActionCommand("Praying Ave Mater");
        jbAddAveMater.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //System.out.println( String.format("%s", actionEvent.getActionCommand()) );
                panelCaller.recitaPreghiera(ERosarioSymbol.SYM_AVE.textSymbol);
            }
        });

        JButton jbAddGloriaPater = new JButton(ERosarioSymbol.SYM_GLORIA.textSymbol);
        jbAddGloriaPater.setToolTipText(ERosarioSymbol.SYM_GLORIA.textPrayerTooltip);
        jbAddGloriaPater.setActionCommand("Praying Gloria");
        jbAddGloriaPater.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //System.out.println( String.format("%s", actionEvent.getActionCommand()) );
                panelCaller.recitaPreghiera(ERosarioSymbol.SYM_GLORIA.textSymbol);
            }
        });


        JButton jbAddSalveRegina = new JButton(ERosarioSymbol.SYM_SALVE.textSymbol); // u2665 u26f0
        jbAddSalveRegina.setToolTipText(ERosarioSymbol.SYM_SALVE.textPrayerTooltip);
        jbAddSalveRegina.setActionCommand("Praying Salve Regina");
        jbAddSalveRegina.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //System.out.println( String.format("%s", actionEvent.getActionCommand()) );
                panelCaller.recitaPreghiera(ERosarioSymbol.SYM_SALVE.textSymbol);
            }
        });


        add(jbAddPaterNoster);

        add(jbAddAveMater);

        add(jbAddGloriaPater);

        add(jbAddSalveRegina);


    }

}
