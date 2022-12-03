package it.wipidea.cosmicmantra.controller;

import it.wipidea.cosmicmantra.EnMantraInvocationType;
import it.wipidea.cosmicmantra.MantraCoreRunner;
import it.wipidea.cosmicmantra.MantraRunType;
import it.wipidea.cosmicmantra.core.EnMantraConstants;
import it.wipidea.cosmicmantra.core.MyCosmicMantraCore;
import it.wipidea.cosmicmantra.utils.MantraFileUtil;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class MantraMainController {


/*
    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT %1$tL] [%4$-7s] %5$s %n");
    }

    {
        Logger root = Logger.getLogger("");
        Level targetLevel = Level.ALL;
        root.setLevel(targetLevel);
        for (Handler handler : root.getHandlers()) {
            handler.setLevel(targetLevel);
        }
    }
*/

    private static Logger logger;

    static {
        String path = MantraMainController.class
                .getClassLoader()
                .getResource("logging-mantra.properties")
                .getFile();
        System.setProperty("java.util.logging.config.file", path);
        //log = Logger.getLogger(MantraMainController.class.getName());
        logger = Logger.getLogger(EnMantraConstants.LOGGER_SINGLE_NAME);
    }



    public static boolean IS_TRANSLUCENCY_SUPPORTED;

    public static MantraRunType EN_TYPE = null;

    public static Vector<Object[]> runners = new Vector<>();
    public static List<Runnable> callableTasks = new ArrayList<>();

    public static ExecutorService executorService;

    public Runnable createTask(MyCosmicMantraCore my) {
        Runnable callableTask = () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
                my.startMantra();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        return callableTask;
    }

    public void addTask(MyCosmicMantraCore my) {
        callableTasks.add(this.createTask(my));
    }

    protected MantraSingleController createMantraSingleController(final String configurationFileName) {
        MantraSingleController mantraSingleController = new MantraSingleController(configurationFileName);
        return mantraSingleController;
    }
    public MantraMainController(EnMantraInvocationType mantraSwitch) {
        logger.finest( String.format("Constructing [%s] instance...", MantraMainController.class.getSimpleName()) );

        if(mantraSwitch==null)
            mantraSwitch = MantraFileUtil.detectSetting();

        switch (mantraSwitch) {
            case Test:
                this.createMantraSingleController("/mantras/AngeloCustodeInvocation_Configuration.properties");
                break;
            case All:
                /*
                this.runMantraSecret();
                this.runMantraCosmic();
                this.runMantraChristian();
                this.runMantraBible();
                this.runMantraLatin();
                this.runMantraPrecetti();
                */
                break;
            case Secret:
                //this.runMantraSecret();
                break;
            case Cosmic:
                //this.runMantraCosmic();
                break;
            case Christian:
                //this.runMantraChristian();
                break;
            case Bible:
                //this.runMantraBible();
                break;
            case Latin:
                //this.runMantraLatin();
                break;
            case Angels:
                //this.runMantraAngels();
                break;
            case PrecettiCosmici:
                //this.runMantraPrecetti();
                break;
            case PrecettiCosmiciSintetici:
                //this.runMantraPrecettiSintetici();
                this.createMantraSingleController("/mantras/EptalogoCosmicoSintetico_Configuration.properties");
                break;
        }

    }


    public static void main(String[] args) {

        try {

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();
            if (!gd.isWindowTranslucencySupported(java.awt.GraphicsDevice.WindowTranslucency.PERPIXEL_TRANSPARENT)) {
                System.err.println("Shaped windows are not supported");
                System.exit(0);
            }

            IS_TRANSLUCENCY_SUPPORTED = gd.isWindowTranslucencySupported(java.awt.GraphicsDevice.WindowTranslucency.TRANSLUCENT);
            if (!IS_TRANSLUCENCY_SUPPORTED) {
                System.out.println("Translucency is not supported, creating an opaque window");
            }

            if (args.length>0)  {
                if (args[0].equals(MantraRunType.ARLEQUIN.name())) {
                    EN_TYPE = MantraRunType.ARLEQUIN;
                } else if (args[0].equals(MantraRunType.MONOSEQ.name())) {
                    EN_TYPE = MantraRunType.MONOSEQ;
                } else if (args[0].equals(MantraRunType.SCROOGE.name())) {
                    EN_TYPE = MantraRunType.SCROOGE;
                } else if (args[0].equals(MantraRunType.CONSOLE.name())) {
                    EN_TYPE = MantraRunType.CONSOLE;
                } else {
                    EN_TYPE = MantraRunType.NORMAL;
                }
            } else {
                EN_TYPE = MantraRunType.NORMAL;
            }

            EnMantraInvocationType switchParam = null;
            if (args.length>0)  {
                switchParam = null;
                if ( MantraRunType.exists(args[0]) ) {
                    if ( args.length>1 )
                        switchParam = EnMantraInvocationType.valueOf(args[1]);
                    else
                        switchParam =null;
                } else {
                    switchParam = EnMantraInvocationType.valueOf(args[0]);
                }
            }

            //MantraCoreRunner mantras = new MantraCoreRunner(switchParam);
            MantraMainController mantraMainController = new MantraMainController(switchParam);

            //mantras.speak();

            if (EN_TYPE != MantraRunType.CONSOLE) {
                //FIXME: mantraMainController.setupStats();
                //FIXME: mantraMainController.startAppExecutor(EN_TYPE);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //System.out.println( "toBinary: " + toBinary(151) );
        }

    }

}
