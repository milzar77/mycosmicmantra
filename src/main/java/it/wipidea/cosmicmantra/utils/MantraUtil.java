package it.wipidea.cosmicmantra.utils;

import it.wipidea.cosmicmantra.controller.ASingleController;
import it.wipidea.cosmicmantra.controller.MantraMainController;
import it.wipidea.cosmicmantra.core.MantraChannelManager;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalField;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class MantraUtil {

    public static final void addWithoutDuplicate(Vector<Long> v, Long l){
        if (v.contains(l))
            return;
        else
            v.add(l);
    }

    public static final Vector<Long> myDateExtractor(LocalDate dt) {

        Vector<Long> composedNumbers = new Vector<>();

        ZonedDateTime zdt = ZonedDateTime.of(dt.atTime(0,0), ZoneId.systemDefault());
        addWithoutDuplicate(composedNumbers, zdt.toInstant().toEpochMilli() );

        addWithoutDuplicate(composedNumbers, composeLong( "%s",
                dt.getDayOfMonth()
        ));

        addWithoutDuplicate(composedNumbers, composeLong( "%s",
                dt.getMonthValue()
        ));

        /*
        // non serve in quanto viene generato un intero che vale sempre il numero ad 1 digit, vedi 07 sempre uguale a 7
        addWithoutDuplicate(composedNumbers, composeLong( "%02d",
                dt.getMonthValue()
        ));*/

        addWithoutDuplicate(composedNumbers, composeLong( "%s",
                dt.getYear()
        ));

        addWithoutDuplicate(composedNumbers, composeLong( "%s",
                Integer.parseInt( String.valueOf(dt.getYear()).substring(2) )
        ));

        addWithoutDuplicate(composedNumbers, composeLong( "%s%s%s",
                dt.getDayOfMonth(),
                dt.getMonthValue(),
                dt.getYear()
        ));
        addWithoutDuplicate(composedNumbers, composeLong( "%s%02d%s",
                dt.getDayOfMonth(),
                dt.getMonthValue(),
                dt.getYear()
        ));

        addWithoutDuplicate(composedNumbers, composeLong( "%s%s%s",
                dt.getYear(),
                dt.getMonthValue(),
                dt.getDayOfMonth()
        ));
        addWithoutDuplicate(composedNumbers, composeLong( "%s%02d%s",
                dt.getYear(),
                dt.getMonthValue(),
                dt.getDayOfMonth()
        ));

        addWithoutDuplicate(composedNumbers, composeLong( "%s%s",
                dt.getDayOfMonth(),
                dt.getMonthValue()
        ));

        addWithoutDuplicate(composedNumbers, composeLong( "%s%02d",
                dt.getDayOfMonth(),
                dt.getMonthValue()
        ));

        addWithoutDuplicate(composedNumbers, composeLong( "%s%s",
                dt.getYear(),
                dt.getMonthValue()
        ));

        addWithoutDuplicate(composedNumbers, composeLong( "%s%02d",
                dt.getYear(),
                dt.getMonthValue()
        ));

        addWithoutDuplicate(composedNumbers, composeLong( "%s%s",
                Integer.parseInt( String.valueOf(dt.getYear()).substring(2) ),
                dt.getMonthValue()
        ));

        addWithoutDuplicate(composedNumbers, composeLong( "%s%02d",
                Integer.parseInt( String.valueOf(dt.getYear()).substring(2) ),
                dt.getMonthValue()
        ));

        addWithoutDuplicate(composedNumbers, composeLong( "%s%s%s",
                dt.getDayOfMonth(),
                Integer.parseInt( String.valueOf(dt.getYear()).substring(2) ),
                dt.getMonthValue()
        ));

        addWithoutDuplicate(composedNumbers, composeLong( "%s%02d%s",
                dt.getDayOfMonth(),
                dt.getMonthValue(),
                Integer.parseInt( String.valueOf(dt.getYear()).substring(2) )
        ));

        addWithoutDuplicate(composedNumbers, composeLong( "%s%s%s",
                dt.getDayOfMonth(),
                dt.getMonthValue(),
                dt.getYear()
        ));

        addWithoutDuplicate(composedNumbers, composeLong( "%s%02d%s",
                dt.getDayOfMonth(),
                dt.getMonthValue(),
                dt.getYear()
        ));

        return composedNumbers;
    }

    public static final Long composeLong(String fmt, Integer...ints) {
        String theComposedLong = String.format(fmt, ints );
        //System.out.printf("Composed Long is %s\n", theComposedLong);
        return Long.parseLong( theComposedLong );
    }

    /*
    public static final Long composeLongFromDate(Calendar cal, int...intArgs) {

        Long.parseLong( String.format("%s", cal.get(Calendar.DAY_OF_MONTH) ) );
        //Long.parseLong( String.format("%s", cal.get(Calendar.DAY_OF_MONTH) ) );

        return 0L;
    }
    */

    public static final boolean isPrime(long input) {
        boolean isPossiblePrime = false;

        double d = input / 2D;
        if (d - Math.abs(d) != 0.5D) {

            boolean prevVal = false;
            for (long l = (long)input; l>0; l--) {


                double d1 = (double)input / l;

                double checkD1 = d1 - Math.round(d1);

                //System.out.printf("Divide by %s, resulting %s\n", l, checkD1);

                if (checkD1==0 && input!=l && l!=1) {
                    //System.out.printf("Not a possible prime!\n");
                    isPossiblePrime = false;
                    break;
                } else {
                    isPossiblePrime = true;
                }

            }


            /*
            if(true) {
                boolean previousDivision = false;
                for (long l = (long)input; l>0; l--) {
                    System.out.printf("DIV= %s : %s is %s | %s" + "\n", input, l, ( (double)input / l ) , input==l );
                    double d1 = (double)input / l;
                    if (d1 - Math.abs(d1) < 1.0D) {
                        System.out.println("Possible prime division by " + l);
                        if (input!=l) isPossiblePrime = true;
                        //else
                    } else {
                        if (input==l)
                            isPossiblePrime = true;
                        else {
                            isPossiblePrime = false;
                            System.out.println("Stopped prime division @ " + l);
                            break;
                        }
                    }
                }
            }
            */
            return isPossiblePrime;
        }
        //TODO: go on with prime number checks, if it is dividable only by itself
        return false;
    }

    public static final boolean hasSameCiphers(long input) {
        String s = String.valueOf(input);
        boolean isSameCipher = false;
        char previousCipher = 'X';
        for (byte b : s.getBytes()) {
            char c  = (char)b;
            //System.out.println("PART#: " + String.valueOf(c));
            if (c==previousCipher)
                isSameCipher=true;
            else {
                if (previousCipher != 'X') {
                    isSameCipher=false;
                    break;
                }
            }
            previousCipher = c;
        }
        return isSameCipher;
    }

    public static final boolean isPalindromeCiphers() {
        return false;
    }

    public static final boolean ciphersSumEqualTo(long input, long equalTo) {
        return false;
    }

    /**
     * La cifra contiene dei miei numeri "magici", tipo 31777 contiene la mia data di nascita senza "double digit format", 310777 contiene la mia data di nascita con "double digit format"
     * @return
     */
    public static final boolean ciphersContainsMyMagicNumbers() {
        return false;
    }

    public static void drawStringWithNewLine(Graphics g, String text, int x, int y, boolean isStraightLine) {
        Font font = new Font(Font.MONOSPACED, Font.PLAIN, MantraUtil.getDefaultFontSize());
        g.setFont(font);
        if (isStraightLine) {
            g.drawString(text, x, y += g.getFontMetrics().getHeight() + 4);
        } else {
            for (String line : text.split("\n"))
                g.drawString(line, x, y += g.getFontMetrics().getHeight() + 4);
        }

    }

    public static Integer getDefaultFontSize() {
        //FIXME: sistemare indice istanza corrente
        String sFntSize = MantraMainController.callableMantraSingleControllers.get(0).PROPS.getOrDefault("FNT.SIZE", Integer.valueOf(16)).toString();
        //String sFntSize = ASingleController.PROPS.getOrDefault("FNT.SIZE", Integer.valueOf(16)).toString();
        //String sFntSize = "35";//.PROPS.getOrDefault("FNT.SIZE", Integer.valueOf(16)).toString();
        Integer iFntSize = Integer.parseInt(sFntSize.toString());
        return iFntSize;
    }


    public static Shape buildRoundedRect(double x, double y, double w, double h, double arcRadX, double arcRadY) {
        RoundRectangle2D s = new RoundRectangle2D.Double(x, y, w, h, arcRadX, arcRadY);
        return s;
    }

    public static Shape buildEllipse(double x, double y, double w, double h) {
        Rectangle2D rect = new Rectangle2D.Double(x,y,w,h);
        Ellipse2D ellipse = new Ellipse2D.Double();
        ellipse.setFrame(rect);

        double centerX = rect.getCenterX();
        double centerY = rect.getCenterY();
        double radiusX = w/2;
        double radiusY = h/1;

        ellipse.setFrameFromCenter(centerX, centerY, centerX + radiusX, centerY + radiusY);

        return ellipse;
    }


    public static Color colorFromRgb(String fg) {
        String[] colors = fg.split(",");
        Color col = colorFromRgb(Integer.parseInt(colors[0]), Integer.parseInt(colors[1]), Integer.parseInt(colors[2]));
        return col;
    }
    public static Color colorFromRgb(int r, int g, int b) {
        Color col = new Color(r, g, b);
        return col;
    }


}

