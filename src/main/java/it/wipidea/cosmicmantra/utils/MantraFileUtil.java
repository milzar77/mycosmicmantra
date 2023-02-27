package it.wipidea.cosmicmantra.utils;

import it.wipidea.cosmicmantra.EnMantraInvocationType;
import it.wipidea.cosmicmantra.controller.AMainController;
import it.wipidea.cosmicmantra.core.EnMantraConstants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.*;

public final class MantraFileUtil {

    protected final static Logger LOGGER = Logger.getLogger(MantraFileUtil.class.getName());

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter() {
            private static final String format = "==> %s|%s| %s %n";

            @Override
            public synchronized String format(LogRecord lr) {
                return String.format(format,
                        new Date(lr.getMillis()),
                        lr.getLevel().getLocalizedName(),
                        lr.getMessage()
                );
            }
        });
        LOGGER.setUseParentHandlers(false);
        LOGGER.addHandler(handler);

    }

    public final static void statisticaSuFile(ConcurrentHashMap hash, String[] info2print) {
        File f = new File(AMainController.STATS_GLOBAL_PATH);
        try {
            FileWriter fw = new FileWriter(f, false);
            //fw.append( String.format( "x=%s;y=%s;w=%s;h=%s", ((Point)objects[0]).x, ((Point)objects[0]).y, ((Dimension)objects[1]).width, ((Dimension)objects[1]).height ));
            fw.append(info2print[0]+"\n");
            fw.append( String.format(
                    "ITERAZIONI TOTALI=%s;" +
                            "NUMERI PRIMI TOTALI=%s;" +
                            "\n",
                    hash.get(info2print[1]),
                    hash.get(info2print[2])
            ));

            for (Object k : hash.keySet()) {
                if (k.toString().startsWith(info2print[3])) {
                    fw.append(
                            String.format("%s=%s;" + "\n", k, hash.get( k ) )
                    );
                    String kSep = info2print[4]+k.toString().substring(k.toString().indexOf("-"));
                    //System.out.println("DEBUG SEP = " + kSep);
                    if (hash.containsKey(kSep)) {
                        //System.out.println("DEBUG CHECK IT OUT THIS ! ");
                        fw.append(
                                String.format("%s=%s;" + "\n", kSep, hash.get( kSep ) )
                        );
                    }
                    //composeSeedFor
                }
            }

            fw.flush();
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final static EnMantraInvocationType detectSetting() {


        java.util.Map map =  System.getenv();

        LOGGER.config("===ENV===");
//        System.out.println(map);
        for (Object o : map.keySet()) {
            LOGGER.config(String.valueOf(o) + ": "  + map.get(o));
        }
        LOGGER.config("---ENV---");



        String test = System.getenv("PWD");
        if (test==null) {
            LOGGER.info("Hello World! You Are Running Cosmic Mantra from Within A WINDOWS");

            //test = System.getenv("=Z:");
            test = System.getenv("=W:");
            if (test==null) {
                test = "/tmp/mycosmicmantra/PrecettiCosmiciSintetici";
                //test = "Z:/tmp/mycosmicmantra/Angels";
            }
            File fTest1 = new File(test);
            LOGGER.info( String.format("CURRENT DIR is [%s]\n", fTest1.getAbsolutePath()) );
        	/*
        	System.exit(1);
        	return FLAG_ALL;
        	*/
        } else {
            LOGGER.info( String.format("PWD is [%s]\n", test) );
        }
        File fTest = new File(test);
        if (fTest.exists() && fTest.isDirectory()) {
            ;//System.out.println("CHECKING IF THIS IS THE LAST ELEMENT OF DIR PATH: "+fTest.getName());
        } else {
            LOGGER.severe("Error: missing input mantra directory!"+"\n");
            System.exit(1);
        }

        String arg = fTest.getName();

        if (arg.equals("Secret")) {
            return EnMantraInvocationType.Secret;
        } else if (arg.equals("Custom")) {
            return EnMantraInvocationType.Custom;
        } else if (arg.equals("Cosmic")) {
            return EnMantraInvocationType.Cosmic;
        } else if (arg.equals("Christian")) {
            return EnMantraInvocationType.Christian;
        } else if (arg.equals("Bible")) {
            return EnMantraInvocationType.Bible;
        } else if (arg.equals("Latin")) {
            return EnMantraInvocationType.Latin;
        } else if (arg.equals("Angels")) {
            return EnMantraInvocationType.Angels;
        } else if (arg.equals("Islam")) {
            return EnMantraInvocationType.Islam;
        } else if (arg.equals("PrecettiCosmici")) {
            return EnMantraInvocationType.PrecettiCosmici;
        } else if (arg.equals("PrecettiCosmiciSintetici")) {
            return EnMantraInvocationType.PrecettiCosmiciSintetici;
        } else if (arg.equals("playground")) {
            return EnMantraInvocationType.Test;
        } else {
            return EnMantraInvocationType.All;
        }
    }

}
