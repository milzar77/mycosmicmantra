package it.wipidea.cosmicmantra.gui;

import it.wipidea.cosmicmantra.MantraCoreRunner;
import it.wipidea.cosmicmantra.core.MantraChannelManager;
import it.wipidea.cosmicmantra.core.MantraCore;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

public class MantraFX {

    public Double x = 0D;
    public Double y = 0D;

    public Double strokeWeight = 3.5D;
    public Double xLen = 20D;
    public Double yLen = 20D;


    private int rotationCounter = 0;//contatore angolatura
    private final int howManyTimeInSameRotation = 12;//quante volte con la stessa rotazione

    private int positionCounter = 0;//contatore posizionamento
    private final int howManyTimeInSamePosition = 500*howManyTimeInSameRotation;//quante volte nella stessa posizione

    private Component component = null;
    public Vector<Double> vecAngles = new Vector<Double>();

    {
        vecAngles.add(0D);
        vecAngles.add(30D);
        vecAngles.add(60D);
        vecAngles.add(90D);
        vecAngles.add(120D);
        vecAngles.add(150D);
        vecAngles.add(180D);
        vecAngles.add(210D);
        vecAngles.add(240D);
        vecAngles.add(270D);
        vecAngles.add(300D);
        vecAngles.add(330D);
        vecAngles.add(360D);
    }

    private static HashMap<String, MantraFX> instances = new HashMap<String, MantraFX>();

    protected MantraFX() {}

    public static void addInstance(String seed, MantraFX instance) {
        instances.put(seed, instance);
    }

    public static MantraFX getInstance(String seed) {
        if ( instances.containsKey(seed) ) {
            return instances.get(seed);
        } else {
            MantraFX mfx = new MantraFX();
            instances.put(seed, mfx);
        }
        return instances.get(seed);
    }


    public void startFairyLights(Graphics2D g2d, Component comp) {
        /*g2d.setColor(Color.YELLOW);
        g2d.fillRect(0, 0, 150, 100);*/

        component = comp;

        Stroke oldStroke = g2d.getStroke();
        Stroke stroke = new BasicStroke(strokeWeight.floatValue(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        g2d.setStroke(stroke);

        g2d.setColor(Color.YELLOW);
        /*Line2D l1 = new Line2D.Double(50,50, 100,100);
        g2d.draw(l1);*/

        //g2d.setColor(Color.GREEN);
        for (Double d : vecAngles) {
            myAngle(g2d, d, 29D);
        }

    }

    private void myAngle(Graphics2D g2d, Double angleDegree, Double angleSoil) {
        if (angleDegree-angleSoil>angleSoil) {
            //Random rand = new Random(angleSoil.longValue());
            //rand.ints(angleDegree.intValue() - angleSoil.intValue(), angleDegree.intValue());
            int min = angleDegree.intValue()-angleSoil.intValue();
            Double oldAngleDegree = angleDegree;
            angleDegree = (double) ((Math.random() * (angleDegree - min)) + min);
            //System.out.printf("==> Calculating random degree [%s] from old one [%s] from a minimum of [%s]...\n", angleDegree.intValue(), oldAngleDegree.intValue(), min);
        }
        double angle = Math.toRadians(angleDegree);//radians
        //double angleDegree = angle * Math.PI / 180;
        //System.out.printf("Rad angle [%s] is [%s] degrees\n", angle, angleDegree);

        if (x==0&&y==0) {
            x = getNextX();
            y = getNextY();
        }

        if (positionCounter > howManyTimeInSamePosition) {
            x = getNextX();
            y = getNextY();
            positionCounter = 0;
        }

        double startX = x;
        double startY = y;
        double endX   = x + xLen * Math.sin(angle);
        double endY   = y + yLen * Math.cos(angle);
        Line2D l1 = new Line2D.Double(startX,startY, endX,endY);
        g2d.draw(l1);
        positionCounter++;
    }

    private double getNextX() {
        int wMax = component.getWidth();
        int xMin = 0;
        Double d1 = (Double) ((Math.random() * (wMax - xMin)) + xMin);
        return d1;
    }

    private double getNextY() {
        int hMax = component.getHeight();
        int yMin = 0;
        Double d1 = (Double) ((Math.random() * (hMax - yMin)) + yMin);
        return d1;
    }

    public static void startFairyLights() {

        if (MantraChannelManager.activeChannel==null) {
            //System.out.println("==> Fairy Lights Yet Not Ready!");
            return;
        }
        //System.out.println("==> Starting * * * fairy * * * lights * * * ...");

        Graphics2D g2d = (Graphics2D) MantraChannelManager.activeChannel.mantraWindow.getGraphics();

        //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.YELLOW);
        g2d.fillRect(0, 0, 320, 320);

        MantraChannelManager.activeChannel.mantraWindow.repaint();
        //MantraChannelManager.activeChannel.mantraWindow.update(g2d);


    }
}
