package it.wipidea.cosmicmantra.gui;

import it.wipidea.cosmicmantra.MantraCoreRunner;
import it.wipidea.cosmicmantra.core.MyKeyChecker;
import it.wipidea.cosmicmantra.utils.MantraUtil;
import it.wipidea.cosmicmantra.core.objects.MantraWord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class MantraFrame extends JFrame {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Double wWin = screenSize.getWidth();
    Double hWin = screenSize.getHeight();

    private Long INTERVAL_KEYWORD = 0L;
    private Integer LIMIT_KEYS = 0;
    public int JUMPS = LIMIT_KEYS>0 ? LIMIT_KEYS : 3;
    public int TEXT_JUMP_Y = 30;
    public int UI_WIDTH = 0;//777;
    public int UI_HEIGHT = (TEXT_JUMP_Y *2) * JUMPS;

    public int MARGIN_X = 20;//TODO:DOING:sto cercando di sistemare il margine per il posizionamento della scritta
    public int MARGIN_Y = 20;
    private Font font = new Font(Font.MONOSPACED, Font.PLAIN, 16);

    private BufferedImage img;// = new BufferedImage(UI_WIDTH, UI_HEIGHT, BufferedImage.TYPE_INT_ARGB);
    private Graphics2D imgG2;// = img.createGraphics();

    public MyCanvas canvasBuffer;
    private Vector<MantraWord> sentences;

    // measure mantra sentences off buffer
    // init frame

    public MantraFrame(String fileName, Vector<MantraWord> vMantraWords, Integer lmt, Long ik) {

        if (true) {//USE VAR
            this.setUndecorated(true);
        }

        if (this.isUndecorated()) {
            //MARGIN_Y = 0;
            //this.setVisible(false);
            this.setVisible(true);
        } else {
            this.setVisible(true);
        }

        LIMIT_KEYS = lmt;
        INTERVAL_KEYWORD = ik;
        JUMPS = LIMIT_KEYS>0 ? LIMIT_KEYS : 3;

        this.sentences = vMantraWords;

        img = new BufferedImage(wWin.intValue(), hWin.intValue(), BufferedImage.TYPE_INT_ARGB);
        imgG2 = img.createGraphics();

        this.measureSentences();

        this.initPanel();

        this.addKeyListener(new MyKeyChecker());

        /*SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {*/
                /*try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }*/
                if (MantraCoreRunner.EN_TYPE.isValid()) {
                    MantraFrame.this.setupWindowShape();
//FIXME:
                    MantraFrame.this.setupWindowOpacity();
                }
            /*}
        });*/
    }

    private void setupWindowOpacity() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                // Set the window to 70% translucency, if supported.
                if (MantraCoreRunner.isTranslucencySupported) {
                    MantraFrame.this.setOpacity(0.75f);
                }

                // Display the window.
                //sw.setVisible(true);
            }
        });
    }

    private void setupWindowShape() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                //Ellipse2D el = MantraUtil.buildEllipse(0,0, getWidth(), getHeight());
                Shape s = MantraUtil.buildRoundedRect(0,0, getWidth(), getHeight(), 50, 50);
                MantraFrame.this.setShape( s );
            }
        });
    }

    private void measureSentences() {

        //font = new Font(Font.SERIF, Font.PLAIN, 30);
        Double dX = 0D;
        //Double dY = 0D;
        TEXT_JUMP_Y = 10;
        for (MantraWord sentence : sentences) {
            FontRenderContext fontRenderCtx = imgG2.getFontRenderContext();
            Rectangle2D bounds = font.getStringBounds(sentence.mantraKey, fontRenderCtx);
            //System.out.println("Bounds are " + bounds.getWidth() + " x " + bounds.getHeight());
            if (dX < bounds.getWidth()) {
                dX = MARGIN_X+bounds.getWidth();
            }
            if (TEXT_JUMP_Y < bounds.getHeight()) {
                TEXT_JUMP_Y = /*MARGIN_Y+*/(int)bounds.getHeight()+5;
                //System.out.printf("Text JUMP is %s\n", TEXT_JUMP_Y);
            }
        }
        //dY = TEXT_JUMP_Y + MARGIN_Y
        System.out.println("MAX X="+dX);
        System.out.println("MAX Y="+ TEXT_JUMP_Y );
        UI_WIDTH = MARGIN_X+dX.intValue();
        UI_HEIGHT = MARGIN_Y+(TEXT_JUMP_Y) * (LIMIT_KEYS+1);
        System.out.printf("Message [limited to %s, jump is %s] needs resizing @ %s x %s\n", LIMIT_KEYS, TEXT_JUMP_Y, UI_WIDTH, UI_HEIGHT);

    }

    private void initPanel() {

        this.setPreferredSize(new Dimension(UI_WIDTH,UI_HEIGHT));
        //this.setVisible(false);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setVisible(true);

        JPanel panel = new JPanel();
        //panel.setPreferredSize(new Dimension(UI_WIDTH,UI_HEIGHT));

        canvasBuffer = new MyCanvas(this);

        panel.setBackground(BG_COLOR);

        //label = new JLabel();

        panel.setLayout( new BoxLayout(panel, BoxLayout.Y_AXIS));
        //panel.add(label);
        panel.add(canvasBuffer);

        this.getContentPane().add(panel);
        this.pack();

        //System.out.printf("Init Message Panel @ %s x %s\n", UI_WIDTH, UI_HEIGHT);

        Thread t1 = new Thread() {
            @Override
            public void run() {
                super.run();
                while (true && canvasBuffer!=null) {
                    if (canvasBuffer.isValid())
                        canvasBuffer.repaint();
                    else
                        System.out.println("Canvas Buffer Not Yet Ready!");
                    try {
                        sleep(50L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        t1.start();


    }





    public volatile boolean showStar = false;

    Color randomColor = null;
    public void communicateMessage1(int row, long rows, MantraWord mwCurrent) {

        // mettto il colore di sfondo a random
        //System.out.println("MYROW="+row);
        if (row==1) {
            int min = 0;
            int mycol = 255;
            mycol = (int) ((Math.random() * (mycol - min)) + min);
            canvasBuffer.setBg(MantraUtil.colorFromRgb(mycol,113,156));
        }

        // imposto il controllo di visualizzazione della stella
        if (showStar)
            canvasBuffer.showStar = true;
        else
            canvasBuffer.showStar = false;

        int last = JUMPS;
        int cnt = 0;

        alphaMask+=50;
        if (alphaMask>255)
            alphaMask = 0;// da inserire nel color, da vedere se scala 1.0 o 255

        int totalJumps = 0;

        //System.out.printf("ALL CURRENT MESSAGE[row@%s on total rows %s]: \n==>%s\n==:%s\n", row, rows, mwCurrent.mantraKey, sentences.get( (row-1)>=0 ? row-1 : 0 ));

        int posY = 0;

        String output = "";
        for (MantraWord mw : sentences) {
            if (mw.lang.equals( mwCurrent.lang ) ) {
                if (mw.mantraKey.equals(mwCurrent.mantraKey)) {
                    output += mw.mantraKey+"\n";
                    break;
                }
                output += mw.mantraKey+"\n";
            }
        }

        sentenceBuffer = output /*mwCurrent.mantraKey*/;

        if (this.getExtendedState()==JFrame.MAXIMIZED_BOTH) {
            posXBuffer = (wWin.intValue() / 3) + MARGIN_X;
            posYBuffer = (hWin.intValue() / 2) + MARGIN_Y + posY;
        } else {
            posXBuffer = MARGIN_X;
            posYBuffer = /*MARGIN_Y + */posY;
        }

        //resetMyBuffer= true;//row == rows;
        //if (resetMyBuffer) System.out.printf("RESETTING BUFFER [%s] = %s\n", sentenceBuffer, resetMyBuffer);
        //FIXME: ADEGUARE PER GFX
        /*try {
            Thread.sleep(INTERVAL_KEYWORD);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/

        this.repaint();

        //canvasBuffer.repaint();
    }


    public void communicateMessage2(int row, long rows, MantraWord mw) {

        int last = JUMPS;
        int cnt = 0;

        alphaMask+=50;
        if (alphaMask>255)
            alphaMask = 0;// da inserire nel color, da vedere se scala 1.0 o 255

        int totalJumps = 0;

        //System.out.printf("CURRENT MESSAGE[row@%s on total rows %s]: \n==>%s\n==:%s\n", row, rows, mw.mantraKey, sentences.get( (row-1)>=0 ? row-1 : 0 ));

        int posY = row * TEXT_JUMP_Y;

        sentenceBuffer = mw.mantraKey;

        if (this.getExtendedState()==JFrame.MAXIMIZED_BOTH) {
            posXBuffer = (wWin.intValue() / 3) + MARGIN_X;
            posYBuffer = (hWin.intValue() / 2) + MARGIN_Y + posY;
        } else {
            posXBuffer = MARGIN_X;
            posYBuffer = /*MARGIN_Y + */posY;
        }

        //resetMyBuffer=true;
        try {
            Thread.sleep(INTERVAL_KEYWORD);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        this.repaint();
    }

    public void communicateMessage3(int row, long rows, MantraWord mw) {

        int last = JUMPS;
        int cnt = 0;

        alphaMask+=50;
        if (alphaMask>255)
            alphaMask = 0;// da inserire nel color, da vedere se scala 1.0 o 255

        int totalJumps = 0;

        //System.out.printf("CURRENT MESSAGE[row@%s on total rows %s]: \n==>%s\n==:%s\n", row, rows, mw.mantraKey, sentences.get( (row-1)>=0 ? row-1 : 0 ));

        int posY = row * TEXT_JUMP_Y;

        sentenceBuffer = mw.mantraKey;

        if (this.getExtendedState()==JFrame.MAXIMIZED_BOTH) {
            posXBuffer = (wWin.intValue() / 3) + MARGIN_X;
            posYBuffer = (hWin.intValue() / 2) + MARGIN_Y + posY;
        } else {
            posXBuffer = MARGIN_X;
            posYBuffer = /*MARGIN_Y + */posY;
        }

        resetMyBuffer= row == rows;
        //if (resetMyBuffer) System.out.printf("RESETTING BUFFER [%s] = %s\n", sentenceBuffer, resetMyBuffer);
        try {
            Thread.sleep(INTERVAL_KEYWORD);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        this.repaint();
    }

    boolean resetMyBuffer = false;
    float ALPHA_START = 0.5f;
    float ALPHA_END = 1.0f;
    float ALPHA_LIMIT_END = 1.0f;
    float alpha = ALPHA_START;

    String sentenceBuffer = "";

    public Integer posXBuffer = 0;
    public Integer posYBuffer = 0;

    Color BG_COLOR = Color.WHITE;
    Color FG_COLOR = Color.BLACK;

    private float stroke = 3;

    private Color current = FG_COLOR;
    /*private Color current = Color.BLACK;
            for (int i = current.getGreen(); current.getGreen()<255; i++) {
        current=current.brighter();
        if (i%6==0) break;
    }
            */
    private int alphaMask = 0;

    public void paint(Graphics g) {
        //super.paint(g);
        if (canvasBuffer==null) {
            this.paintMaskAlpha(g);
        } else {
            canvasBuffer.sentenceBuffer = this.sentenceBuffer;
            super.paint(g);
            //canvasBuffer.repaint();
        }
    }

    private void paintMaskAlpha(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        //set the opacity
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setStroke(new BasicStroke(stroke));
        g2d.setColor(current);
        g2d.setFont(font);

        //do the drawing here
        //g2d.drawLine(10, 10, 110, 110);
        //g2d.drawRect(10, 10, 100, 100);

        //int posY = cnt * TEXT_JUMP_Y;

        MantraUtil.drawStringWithNewLine(g2d, sentenceBuffer, posXBuffer, posYBuffer);//g
        //OLD SAFE: g2d.drawString(sentenceBuffer, posXBuffer, posYBuffer);

        /*
        if (this.getExtendedState()==JFrame.MAXIMIZED_BOTH)
            g2d.drawString(sentenceBuffer, posXBuffer, posYBuffer);
        else
            g2d.drawString(sentenceBuffer, MARGIN_X, MARGIN_Y+posYBuffer);
        */

        if (resetMyBuffer) {
            g2d.setColor(BG_COLOR);
            g2d.fillRect(0, 0, wWin.intValue(), hWin.intValue());
            /*repaint();
            try {
                Thread.sleep(40);//INTERVAL_KEYWORD
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;*/
        }

        if (alpha >= ALPHA_END) {
            //System.out.printf("CHECK THIS OUT! %s\n", alpha);
            alpha = ALPHA_START;
            //System.out.printf("CHECK THIS OUT after! %s\n", alpha);
            //repaint();
        } else {
            //System.out.printf("REPAINT THIS OUT! %s\n", alpha);
            //increase the opacity and repaint
            alpha += 0.10f;//0.05f
            if (alpha >= ALPHA_LIMIT_END) {
                alpha = ALPHA_START;
            }
            repaint();
        }
        //repaint();


        //sleep for a bit
        try {
            Thread.sleep(50);//INTERVAL_KEYWORD
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setBg(String bg) {
        String[] colors = bg.split(",");
        BG_COLOR = new Color(Integer.parseInt(colors[0]), Integer.parseInt(colors[1]), Integer.parseInt(colors[2]));
        this.setBackground(BG_COLOR);
    }

    public void setFg(String fg) {
        String[] colors = fg.split(",");
        FG_COLOR = new Color(Integer.parseInt(colors[0]), Integer.parseInt(colors[1]), Integer.parseInt(colors[2]));
    }

}
