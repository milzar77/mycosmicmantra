package it.wipidea.cosmicmantra.gui;


import it.wipidea.cosmicmantra.controller.AMainController;
import it.wipidea.cosmicmantra.core.MyKeyChecker;
import it.wipidea.cosmicmantra.utils.MantraUtil;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;

public class MyJFrame extends JFrame {

    public static ConcurrentHashMap STATS = new ConcurrentHashMap();

    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static Double wWin = screenSize.getWidth();
    private static Double hWin = screenSize.getHeight();

    private Color currentBG;
    private Color currentFG;

    public MyJFrame(String winTitle, ConcurrentHashMap stats) {
        super(winTitle);
        STATS = stats;
        textBuffer += "Press CTRL+S for some singularity stats.\n";
        textBuffer += "Press CTRL+W or K for kill all processes.\n";

        Dimension dim = new Dimension(320,600);
        setPreferredSize(dim);

        currentBG = Color.LIGHT_GRAY;
        currentFG = Color.BLACK;

        setBackground(currentBG);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.addKeyListener(new MyKeyChecker());

        int x = 0, y = 0, w = 0, h = 0;

        x = (int) (wWin - dim.getWidth()*1.1);

        y = (int) (dim.getWidth()*0.25);

        setBounds(x, y, (int)dim.getWidth(), (int)dim.getHeight());

        this.repaint();

    }

    private String textBuffer = "";
    public void printStats(boolean chaoticWay) {

        System.out.println("STATS: " + STATS);

        String content = "=== SOME STATS ===\n\n";

        if (chaoticWay) {
            for (Object k : STATS.keySet()) {
                content += String.format("%s = %s\n", k, STATS.get(k));
            }
        } else {
            String keyAlreadyListed = "";

            keyAlreadyListed += AMainController.STAT_KEY_TOTALS+";";
            //String.format("%,d", no);
            content += String.format("%s = %s\n", AMainController.STAT_KEY_TOTALS,
                    String.format("%,d", STATS.get(AMainController.STAT_KEY_TOTALS))
            );

            keyAlreadyListed += AMainController.STAT_KEY_TOTAL_PRIMENUMS+";";
            content += String.format("%s = %s\n", AMainController.STAT_KEY_TOTAL_PRIMENUMS,
                    //"WIP"
                    String.format("%,d", AMainController.elenco_numeri_primi.size())
            );

            keyAlreadyListed += AMainController.STAT_KEY_LAST1ST_PRIMENUM+";";
            content += String.format("%s = %s\n", AMainController.STAT_KEY_LAST1ST_PRIMENUM,
                    String.format("%,d", STATS.get(AMainController.STAT_KEY_LAST1ST_PRIMENUM))
            );

            for (Object k : STATS.keySet()) {
                if ( keyAlreadyListed.indexOf(k.toString()) != -1 ) {
                    continue;
                }
                content += String.format("%s = %s\n", k,
                        String.format("%,d", STATS.get(k))
                );
            }

        }

        textBuffer = content;

        this.repaint();
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(currentBG);
        g2d.fillRect(0, 0, 320, 600);

        g2d.setColor(currentFG);

        MantraUtil.drawStringWithNewLine(
                g2d,
                textBuffer,
                10,50
        );

    }

}

