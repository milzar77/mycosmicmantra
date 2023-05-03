package it.wipidea.rosario.light;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RosarioManualButtonPanel extends JPanel implements IButtonCaller {

    public final String sPaterPrefix = ERosarioSymbol.SYM_PATER.textSymbol+"PN";
    public final String sAvePrefix = ERosarioSymbol.SYM_AVE.textSymbol+"AM";
    public Color colorPater, colorMater;
    public JButton jbAddPaterNoster, jbAddAveMater, jbAddGloriaPater, jbAddSalveRegina;

    public RosarioManualButtonPanel(IPanelCaller panelCaller) {
        jbAddPaterNoster = new JButton( String.format(sPaterPrefix + "[%s]", 0) );
        if (colorPater==null)
            colorPater = jbAddPaterNoster.getBackground();
        jbAddPaterNoster.setToolTipText(ERosarioSymbol.SYM_PATER.textPrayerTooltip);
        jbAddPaterNoster.setActionCommand("Praying Pater Noster");
        jbAddPaterNoster.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //System.out.println( String.format("%s", actionEvent.getActionCommand()) );
                panelCaller.recitaPreghiera(ERosarioSymbol.SYM_PATER.textSymbol);
            }
        });

        jbAddAveMater = new JButton( String.format(sAvePrefix + "[%02d%n]", 0)  );// u2605 6
        //JButton jbAddAveMater0 = new JButton("\u00b7");// \u2022 \u2219  \u2206 07  \u25cf \u25c9 \u25d0 \u25d6 \u25d8
        if (colorMater==null)
            colorMater = jbAddAveMater.getBackground();
        jbAddAveMater.setToolTipText(ERosarioSymbol.SYM_AVE.textPrayerTooltip);
        jbAddAveMater.setActionCommand("Praying Ave Mater");
        jbAddAveMater.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //System.out.println( String.format("%s", actionEvent.getActionCommand()) );
                panelCaller.recitaPreghiera(ERosarioSymbol.SYM_AVE.textSymbol);
            }
        });

        jbAddGloriaPater = new JButton(ERosarioSymbol.SYM_GLORIA.textSymbol+"GL");
        jbAddGloriaPater.setToolTipText(ERosarioSymbol.SYM_GLORIA.textPrayerTooltip);
        jbAddGloriaPater.setActionCommand("Praying Gloria");
        jbAddGloriaPater.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //System.out.println( String.format("%s", actionEvent.getActionCommand()) );
                panelCaller.recitaPreghiera(ERosarioSymbol.SYM_GLORIA.textSymbol);
            }
        });


        jbAddSalveRegina = new JButton(ERosarioSymbol.SYM_SALVE.textSymbol+"SR"); // u2665 u26f0
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
