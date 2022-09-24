package it.wipidea.cosmicmantra.gui;

import it.wipidea.cosmicmantra.utils.MantraUtil;

import javax.swing.*;
import java.awt.*;

public class MyCanvas extends JPanel {

    boolean resetMyBuffer = false;
    float ALPHA_START = 0.5f;
    float ALPHA_END = 1.0f;
    float ALPHA_LIMIT_END = 1.0f;
    float alpha = ALPHA_START;
    public String sentenceBuffer = "";
    public Integer posXBuffer = 0;
    public Integer posYBuffer = 0;
    private Color BG_COLOR = Color.WHITE;
    private Color FG_COLOR = Color.BLACK;

    public volatile boolean showStar = false;
    private float stroke = 3;

    private float strokeInterlaced = 1;
    private Color current = FG_COLOR;
    private Font font = new Font(Font.MONOSPACED, Font.PLAIN, 16);

    private MantraFrame source;

    public MyCanvas(MantraFrame parSource) {
        super();

        this.source = parSource;

        BG_COLOR = source.BG_COLOR;
        FG_COLOR = source.FG_COLOR;
        current = source.FG_COLOR;

        setBackground(BG_COLOR);

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        /*Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.YELLOW);
        g2d.fillRect(0, 0, 150, 70);*/

        Graphics2D g2d = (Graphics2D) g;
//FIXME:        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .2F));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        //ripristina colore di sfondo, workaround per disegnare un rect come sfondo
        //setBackground(BG_COLOR);
        //g2d.setColor(BG_COLOR);
        //g2d.setColor(Color.YELLOW);
        //g2d.fillRect(0, 0, getWidth(), getHeight());

        /*for (int i = 0; i < getHeight(); i+=2) {
            //g2d.setColor(Color.BLUE);
            g2d.setColor( MantraUtil.colorFromRgb(90,113,156) );
            //rgb(48,70,111) , rgb(90,113,156)
            g2d.drawLine(0,i, getWidth(), i);
        }*/

        if ( showStar )
            MantraFX.getInstance(source.getName()).startFairyLights(g2d, this);


        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        current = FG_COLOR;

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

        //interlaced mask

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .4F));

        g2d.setStroke(new BasicStroke(strokeInterlaced));
        for (int i = 0; i < getHeight(); i+=2) {
            //g2d.setColor(Color.BLUE);
            //g2d.setColor(Color.YELLOW);
            g2d.setColor( MantraUtil.colorFromRgb(90,113,156) );
            //rgb(48,70,111) , rgb(90,113,156)
            g2d.drawLine(0,i, getWidth(), i);
        }


        if (resetMyBuffer) {
            g2d.setColor(BG_COLOR);
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
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
//            repaint();
        }


    }

    public void setBg(String bg) {
        String[] colors = bg.split(",");
        BG_COLOR = new Color(Integer.parseInt(colors[0]), Integer.parseInt(colors[1]), Integer.parseInt(colors[2]));
        this.setBackground(BG_COLOR);
    }

    public void setBg(Color bg) {
        this.setBackground(bg);
    }

    public void setFg(String fg) {
        String[] colors = fg.split(",");
        FG_COLOR = new Color(Integer.parseInt(colors[0]), Integer.parseInt(colors[1]), Integer.parseInt(colors[2]));
    }

}
