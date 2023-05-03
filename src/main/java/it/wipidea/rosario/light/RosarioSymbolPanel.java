package it.wipidea.rosario.light;

import javax.swing.*;
import java.awt.*;

public class RosarioSymbolPanel extends JPanel implements IPanelCaller {



    private JTextArea jta;

    private Integer prayerIndex = 0;
    private Integer globalPrayerIndex = 0;
    private Integer theMorningStarPrayerIndex = 0;
    private Integer incrementoRosarioSerie = 1;

    public RosarioSymbolPanel() {
        jta = new JTextArea("");

        jta.setBackground(RosarioConsts.bgColor1);
        jta.setForeground(RosarioConsts.fgColor1);

        this.setLayout(new BorderLayout());
        this.add(jta, BorderLayout.CENTER);
    }

    /**
     *
     */
    @Override
    public void recitaPreghiera(String prayerSymbol) {
        String currentText = jta.getText();
        if (currentText == null) {
            currentText = "";
        }

        jta.setText(currentText+prayerSymbol);

    }

    /**
     *
     */
    @Override
    public void recitaPreghieraAutomatica() {
        String currentText = jta.getText();
        if (currentText == null) {
            currentText = "";
        }

        RosarioLightWindowMain.instanceRosarioManualButton.jbAddPaterNoster.setBackground(RosarioLightWindowMain.instanceRosarioManualButton.colorPater);
        RosarioLightWindowMain.instanceRosarioManualButton.jbAddAveMater.setBackground(RosarioLightWindowMain.instanceRosarioManualButton.colorMater);

        if (globalPrayerIndex<RosarioConsts.schemeMantraForAllOfUs.length*RosarioConsts.MANTRA_TOTALCOUNT_FORALLOFUS) {
            if (prayerIndex<RosarioConsts.schemeMantraForAllOfUs.length) {
                jta.setText(currentText + RosarioConsts.schemeMantraForAllOfUs[prayerIndex].textSymbol);

                if ( RosarioConsts.schemeMantraForAllOfUs[prayerIndex].equals(ERosarioSymbol.SYM_PATER) ) {
                    RosarioLightWindowMain.instanceRosarioManualButton.jbAddPaterNoster.setBackground(Color.YELLOW);
                    RosarioLightWindowMain.instanceRosarioManualButton.jbAddAveMater.setBackground(RosarioLightWindowMain.instanceRosarioManualButton.colorMater);

                    RosarioLightWindowMain.instanceRosarioManualButton.jbAddPaterNoster.setText( String.format( RosarioLightWindowMain.instanceRosarioManualButton.sPaterPrefix + "[%s]", incrementoRosarioSerie) );
                }

                if ( RosarioConsts.schemeMantraForAllOfUs[prayerIndex].equals(ERosarioSymbol.SYM_AVE) ) {
                    RosarioLightWindowMain.instanceRosarioManualButton.jbAddPaterNoster.setBackground(RosarioLightWindowMain.instanceRosarioManualButton.colorPater);
                    RosarioLightWindowMain.instanceRosarioManualButton.jbAddAveMater.setBackground(Color.YELLOW);

                    RosarioLightWindowMain.instanceRosarioManualButton.jbAddAveMater.setText( String.format( RosarioLightWindowMain.instanceRosarioManualButton.sAvePrefix + "[%02d%n]", prayerIndex) );
                }

                prayerIndex++;

            } else {
                prayerIndex=0;
                incrementoRosarioSerie++;

                currentText = RosarioLightWindowMain.activateSingleLine ? "" : currentText;

                jta.setText(currentText + (RosarioLightWindowMain.activateSingleLine ? "" : "\n") + RosarioConsts.schemeMantraForAllOfUs[prayerIndex].textSymbol);

                if ( RosarioConsts.schemeMantraForAllOfUs[prayerIndex].equals(ERosarioSymbol.SYM_PATER) ) {
                    RosarioLightWindowMain.instanceRosarioManualButton.jbAddPaterNoster.setBackground(Color.YELLOW);
                    RosarioLightWindowMain.instanceRosarioManualButton.jbAddAveMater.setBackground(RosarioLightWindowMain.instanceRosarioManualButton.colorMater);

                    RosarioLightWindowMain.instanceRosarioManualButton.jbAddPaterNoster.setText( String.format(RosarioLightWindowMain.instanceRosarioManualButton.sPaterPrefix + "[%s]", incrementoRosarioSerie) );
                    RosarioLightWindowMain.instanceRosarioManualButton.jbAddAveMater.setText( String.format(RosarioLightWindowMain.instanceRosarioManualButton.sAvePrefix + "[%02d%n]", 0) );
                }

                if ( RosarioConsts.schemeMantraForAllOfUs[prayerIndex].equals(ERosarioSymbol.SYM_AVE) ) {
                    RosarioLightWindowMain.instanceRosarioManualButton.jbAddPaterNoster.setBackground(RosarioLightWindowMain.instanceRosarioManualButton.colorPater);
                    RosarioLightWindowMain.instanceRosarioManualButton.jbAddAveMater.setBackground(Color.YELLOW);

                    RosarioLightWindowMain.instanceRosarioManualButton.jbAddAveMater.setText( String.format(RosarioLightWindowMain.instanceRosarioManualButton.sAvePrefix + "[%02d%n]", prayerIndex) );
                }

                prayerIndex++;

            }
        } else {
            if (prayerIndex<RosarioConsts.schemeMantraForThePope.length) {
                jta.setText(currentText + RosarioConsts.schemeMantraForThePope[prayerIndex].textSymbol);
                prayerIndex++;
            } else {

                if (globalPrayerIndex<(RosarioConsts.schemeMantraForAllOfUs.length*RosarioConsts.MANTRA_TOTALCOUNT_FORALLOFUS)+(RosarioConsts.schemeMantraForThePope.length*RosarioConsts.MANTRA_TOTALCOUNT_FORTHEPOPE)) {
                    prayerIndex = 0;
                    jta.setText(currentText + "\n" + RosarioConsts.schemeMantraForThePope[prayerIndex].textSymbol);
                    prayerIndex++;
                    System.out.println("OK");
                } else {
                    if (theMorningStarPrayerIndex==0) {
                        System.out.println("Rise the Morning Star");
                        //prayerIndex = 0;
                        jta.setText(currentText + RosarioConsts.schemeMantraForTheMorningStar[theMorningStarPrayerIndex].textSymbol);
                        theMorningStarPrayerIndex++;
                    }
                }
            }
        }
        globalPrayerIndex++;
    }
}
