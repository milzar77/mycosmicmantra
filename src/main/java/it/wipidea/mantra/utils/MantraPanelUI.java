package it.wipidea.mantra.utils;

import it.wipidea.MyCosmicMantra;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Vector;

public class MantraPanelUI extends JFrame {

    private Long INTERVAL_KEYWORD = 0L;
    private Integer LIMIT_KEYS = 0;
    public int JUMPS = LIMIT_KEYS>0 ? LIMIT_KEYS : 3;
    public int TEXT_JUMP_Y = 30;
    public int UI_WIDTH = 0;//777;
    public int UI_HEIGHT = (TEXT_JUMP_Y *2) * JUMPS;

    public int MARGIN_X = 20;
    public int MARGIN_Y = 20;

    private JLabel label;
    private JPanel panelBuffer;


    private Font font = new Font(Font.MONOSPACED, Font.PLAIN, 16);

    private BufferedImage img;// = new BufferedImage(UI_WIDTH, UI_HEIGHT, BufferedImage.TYPE_INT_ARGB);
    private Graphics2D imgG2;// = img.createGraphics();

    private Vector<String> sentences;

    private Short CREATION_CODE = 0;

    public MantraPanelUI(String fileName, Vector<String> vMantraLines, Integer lmt, Long ik){
        super(fileName);
        CREATION_CODE = 1;
        LIMIT_KEYS = lmt;
        INTERVAL_KEYWORD = ik;
        JUMPS = LIMIT_KEYS>0 ? LIMIT_KEYS : 3;
        //this.initPanel();
        //FV: ricevo il riferimento al vettore al momento ancora vuoto, in via di popolamento solo in passaggi successivi
        this.sentences = vMantraLines;

        /*SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                synchronized (MantraPanelUI.this.sentences) {
                    //while (sentences.size() <= MyCosmicMantra.LIMIT_FOR_MANTRA_KEYWORD) {
                        System.out.println("Waiting...");
                    //}
                }
            }
        });*/

        //FV: ATTENZIONE, creo il buffer con dimensioni fissate da parametri di default
        //TODO:FIX: sistemare iMAGE BUFFER
        img = new BufferedImage(640, UI_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        imgG2 = img.createGraphics();
        //System.out.printf("Image Buffer Created @ %s x %s\nMantraContent: %s\n", UI_WIDTH, UI_HEIGHT, vMantraLines.toString());

        System.out.printf("DEBUG: Inizio a misurare la dimensione effettiva delle frasi associate ai mantra\n");
        this.measureSentences();

        this.initPanel();
    }

    private void measureSentences() {


        //font = new Font(Font.SERIF, Font.PLAIN, 30);
        Double dX = 0D;
        //Double dY = 0D;
        TEXT_JUMP_Y = 30;
        for (String sentence : sentences) {
            //String sentence = vMantraLines.firstElement();
            FontRenderContext fontRenderCtx = imgG2.getFontRenderContext();
            Rectangle2D bounds = font.getStringBounds(sentence, fontRenderCtx);
            //System.out.println(bounds.getWidth());
            if (dX < bounds.getWidth()) {
                dX = MARGIN_X+bounds.getWidth();
            }
            if (TEXT_JUMP_Y < bounds.getHeight()) {
                TEXT_JUMP_Y = /*MARGIN_Y+*/(int)bounds.getHeight();
                //System.out.printf("Text JUMP is %s\n", TEXT_JUMP_Y);
            }
        }
        System.out.println("MAX X="+dX);
        System.out.println("MAX Y="+ TEXT_JUMP_Y);
        UI_WIDTH = MARGIN_X+dX.intValue();
        UI_HEIGHT = MARGIN_Y+ TEXT_JUMP_Y * (int)(LIMIT_KEYS*1.5D);
        System.out.printf("Message [limited to %s] needs resizing @ %s x %s\n", LIMIT_KEYS, UI_WIDTH, UI_HEIGHT);
/*TODO:FIX: riattivare
        if (this.firstScanAfterStart<2)
            calcolaSequenza(this, true);
*/
    }



    private void initPanel() {

        calcolaSequenza(this, false);

        if (CREATION_CODE==2) {
            //this.getContentPane().removeAll();
        }

        this.setPreferredSize(new Dimension(UI_WIDTH,UI_HEIGHT));
        this.setVisible(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        //panel.setPreferredSize(new Dimension(UI_WIDTH,UI_HEIGHT));

        panelBuffer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g.create();
                // Drawing the image.
                int w = img.getWidth();
                int h = img.getHeight();

                g2.drawImage(img, 0, 0, MARGIN_X*2+w, h, null);

                g2.dispose();
            }
        };

        imgG2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        imgG2.setStroke(new BasicStroke(3));
        imgG2.setFont(font);
        panel.setBackground(Color.WHITE);

        //label = new JLabel();

        panel.setLayout( new BoxLayout(panel, BoxLayout.Y_AXIS));
        //panel.add(label);
        panel.add(panelBuffer);

        this.getContentPane().add(panel);
        this.pack();

        CREATION_CODE=2;

        //System.out.printf("Init Message Panel @ %s x %s\n", UI_WIDTH, UI_HEIGHT);
        //TODO:FIX: riattivare
        calcolaSequenza(this, true);

    }

    public void communicateMessage(int row, long rows) {

        //FV: ATTENZIONE, misuro ed adeguo la window alle dimensioni della frase
        this.measureSentences();

        int last = JUMPS;
        int cnt = 0;
        for (String sentence : sentences) {

            boolean mybool = ( (cnt+1)/LIMIT_KEYS.doubleValue()) == 1;//cnt%MyCosmicMantra.LIMIT_FOR_MANTRA_KEYWORD!=0;
            //System.out.printf("LIMITE PARI? %s\n", (LIMIT_KEYS%2==0));
            if (LIMIT_KEYS%2==0) {
                //System.out.println("IL LIMITE E' PARI");
            } else {
                //System.out.printf("IL LIMITE E' DISPARI: %s, RESULT=%s\n", mybool, ( (cnt+1)/MyCosmicMantra.LIMIT_FOR_MANTRA_KEYWORD.doubleValue()) );
            }

            //System.out.printf("CURRENT MESSAGE[%s]: %s\n", cnt, sentence);

            if (mybool) {
                int posY = cnt * TEXT_JUMP_Y;
                //System.out.println("Bingo! Cleaning previous sentences");
                //imgG2.setColor(Color.WHITE);
                //imgG2.drawRect(0, 0, UI_WIDTH, UI_HEIGHT);
                //imgG2.fillRect(0, 0, UI_WIDTH, UI_HEIGHT);
                //panelBuffer.repaint();
                imgG2.setColor(Color.GREEN);
                imgG2.drawString(sentence, MARGIN_X, MARGIN_Y+posY);
                cnt=0;
                //System.out.printf("Printing sentence @ Y: %s\n", MARGIN_Y+posY);
            } else {
                int posY = cnt * TEXT_JUMP_Y;
                imgG2.setColor(Color.BLUE);
                imgG2.drawString(sentence, MARGIN_X, MARGIN_Y+posY);
                cnt++;
                //System.out.printf("Printing sentence @ Y: %s\n", MARGIN_Y+posY);
            }

            panelBuffer.repaint();

            try {
                Thread.sleep(INTERVAL_KEYWORD);
                if (mybool) {
                    imgG2.setColor(Color.WHITE);
                    //imgG2.drawRect(0, 0, UI_WIDTH, UI_HEIGHT);
                    imgG2.fillRect(0, 0, UI_WIDTH, UI_HEIGHT);
                    //panelBuffer.repaint();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }




    }

    //TODO:FIX: Ragionare in termini multithread (vedi variabili threadlocal) con Questi due oggetti istanziati / isolati, oppure con altra variabile condivisa
    private short firstScanAfterStart = 0;
    //private static Vector<Object[]> pointsTracker = new Vector<>();

    private void adaptSize1() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Double x = screenSize.getWidth();
        Double y = screenSize.getHeight();

    }

    private static void scriviUltimaPosizione(Object[] objects) {
        File f = new File(MyCosmicMantra.GFX_PATH);
        try {
            FileWriter fw = new FileWriter(f, true);
            //fw.append( String.format( "x=%s;y=%s;w=%s;h=%s", ((Point)objects[0]).x, ((Point)objects[0]).y, ((Dimension)objects[1]).width, ((Dimension)objects[1]).height ));
            fw.append( String.format( "%s;%s;%s;%s\n", ((Point)objects[0]).x, ((Point)objects[0]).y, ((Dimension)objects[1]).width, ((Dimension)objects[1]).height ));
            fw.flush();
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void pulisciCache() {
        File f = new File(MyCosmicMantra.GFX_PATH);
        try {
            FileWriter fw = new FileWriter(f);
            fw.append("");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean leggiSeVuoto() {
        return "".equals( leggiCache() );
    }



    private static String[] splitta(int idx) {
        String cache = leggiCache();
        if (cache==null||cache=="") {
            return new String[]{"0","0"};
        }
        //(Point) pointsTracker.lastElement()[0]
        String[] lines = cache.split("\n");
        if (lines.length>0) {
            String[] els = lines[idx < 0 ? lines.length + idx : (idx< lines.length ? idx : 0 )].split(";");
            return els;
        } else {
            return new String[]{"0","0"};
        }
    }

    private static Point ultimoPunto() {
        String[] els = splitta(-1);
        return new Point(Integer.parseInt(els[0]), Integer.parseInt(els[1]));
    }

    private static Dimension ultimaDimensione() {
        String[] els = splitta(-1);
        return new Dimension(Integer.parseInt(els[2]), Integer.parseInt(els[3]));
    }

    private static String leggiCache() {
        File f = new File(MyCosmicMantra.GFX_PATH);
        if (!f.exists()) {
            return null;
        }
        String out = "";
        try {
            FileReader fr = new FileReader(f);
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line;
                while ((line = br.readLine()) != null) {
                    // process the line.
                    out+=line+"\n";
                }
            }
            fr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return out;
    }

    private static void calcolaSequenza(MantraPanelUI instance, boolean isFinalResize) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Double x = screenSize.getWidth();
        Double y = screenSize.getHeight();
        Integer w = instance.UI_WIDTH;//FIX:creare routine di anteprima per il calcolo altrimenti persiste problema di sporcizia eseuczioni
        Integer h = instance.UI_HEIGHT;

        Point p = leggiSeVuoto() ? new Point(0,0) : ultimoPunto();
        Dimension d = new Dimension(w,h);

        System.out.printf("DEBUG: Inizio a calcolare la sequenza inziale, se esiste riprendo l'ultima posizione orizzontale X=%s,W=%S\n",p.x,w);

        if (leggiSeVuoto()) {
            System.out.printf("DEBUG: Sequenza INIZIALIZZATA, eseguo il BOUNDING e accodo l'ultima posizione X=%s,W=%S\n",p.x,w);
            //FV: problema di accesso sincrono in multithreaded, da risolvere con eventuale file condiviso
            //pointsTracker.add(new Object[]{p, d});
            synchronized (instance) {
                //scriviUltimoPunto(new Object[]{p, d});
                instance.setBounds(p.x, p.y, w,h);

                scriviUltimaPosizione(new Object[]{p, d});
            }
        } else {

                int xLast = p.x;
                int wLast = (int) ultimaDimensione().getWidth();
                int hLast = (int) ultimaDimensione().getHeight();
                System.out.printf("DEBUG: Sequenza normale avviata X=%s,W=%S\n",p.x,w);

                if (isFinalResize) {

                    System.out.printf("DEBUG: Sequenza normale in fase READY TO BOUND X=%s,W=%S\n",p.x,w);

                    synchronized (instance) {

                        if (xLast + wLast > w) {
                            p.x = 0;
                            p.y += hLast /*+ instance.MARGIN_Y*/;
                            //p.y = ((Point) pointsTracker.lastElement()[0]).y+10+hLast;
                            System.out.printf("DEBUG: La posizione orizzontale SUPERA le dimensioni dello schermo, quindi riprendo da ZERO; xLast ==> x=%s, y=%s, w=%s, h=%s\n", p.x, p.y, w, h);
                        } else {
                            p.x = xLast + 10  + wLast;
                            //p.y = p.y;
                            System.out.printf("DEBUG: La posizione orizzontale NON supera le dimensioni dello schermo, quindi riprendo dall'ultima posizione; Else ==> x=%s, y=%s, w=%s, h=%s; CNT: %s\n", p.x, p.y, w, h, instance.firstScanAfterStart);
                        }

//                        if (instance.firstScanAfterStart == 1) {
                            System.out.printf("DEBUG: Scrivo l'ultima posizione orizzontale X=%s,W=%S\n",p.x,w);
                            scriviUltimaPosizione(new Object[]{p, d});
//                        }
                        //System.out.printf("Point @ %s; vector size=%s; content=%s\n", p, pointsTracker.size(), pointsTracker);

                        //instance.calculateNextAvailablePositionOnScreen(pointsTracker);
//                        if (instance.firstScanAfterStart == 1) {
                            instance.setBounds(p.x, p.y, w, h);
                            //instance.firstScanAfterStart = 0;
//                            return;
//                        }
                        instance.firstScanAfterStart++;
                    }
                }
                instance.setVisible(true);
                return;
        }

        /*if (instance.firstScanAfterStart==0) {
            instance.setBounds(p.x, p.y, w,h);
            //if (isFinalResize)
                instance.setVisible(true);
            instance.firstScanAfterStart++;
        }*/


    }


/*
    private static void adaptSize(MantraPanelUI instance, boolean isFinalResize) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Double x = screenSize.getWidth();
        Double y = screenSize.getHeight();
        Integer w = instance.UI_WIDTH;
        Integer h = instance.UI_HEIGHT;


        Point p = (pointsTracker.isEmpty()) ? new Point(0,0) : (Point) pointsTracker.lastElement()[0];
        Dimension d = new Dimension(w,h);
        if (isFinalResize) {

            //if (instance.firstScanAfterStart>=2) return;

            //System.out.printf("Resizing terminated at %s; vector size=%s\n", instance.firstScanAfterStart, pointsTracker.size());

            if (pointsTracker.isEmpty()) {
                p.x = p.x;
                p.y = p.y;
            } else {
                //TODO:FIX: da rivedere meccanismo di caching con rilevazione precedenti punti cartesiani
                int xLast = ((Point) pointsTracker.lastElement()[0]).x;
                int wLast = (int)((Dimension) pointsTracker.lastElement()[1]).getWidth();
                int hLast = (int)((Dimension) pointsTracker.lastElement()[1]).getHeight();

                if (p.x>=w) {
                    p.x = 0;
                    //p.y = 200;
                    //p.y = ((Point) pointsTracker.lastElement()[0]).y+10+hLast;
                } else {
                    p.x = xLast + 10 + wLast;
                    p.y = ((Point) pointsTracker.lastElement()[0]).y;
                }
            }
            if (instance.firstScanAfterStart==0) pointsTracker.add(new Object[]{p, d});
            System.out.printf("Point @ %s; vector size=%s; content=%s\n", p, pointsTracker.size(), pointsTracker);
            System.out.printf("DEBUG ==> x=%s, y=%s, w=%s, h=%s\n", p.x, p.y, w, h);
            instance.calculateNextAvailablePositionOnScreen(pointsTracker);
            if (instance.firstScanAfterStart==1) instance.setBounds(((Point) pointsTracker.lastElement()[0]).x, ((Point) pointsTracker.lastElement()[0]).y, w, h);
            instance.firstScanAfterStart++;
            return;
        }
        //this.setPreferredSize(new Dimension(UI_WIDTH,UI_HEIGHT));
        //this.pack();
        //this.setSize(UI_WIDTH,UI_HEIGHT);
        instance.setBounds(p.x, p.y, w,h);
        instance.setVisible(true);

    }
*/
    private void calculateNextAvailablePositionOnScreen(Vector<Object[]> pointsTracker) {

    }

}
