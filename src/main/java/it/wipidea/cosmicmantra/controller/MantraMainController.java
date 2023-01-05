package it.wipidea.cosmicmantra.controller;

import it.wipidea.cosmicmantra.EnMantraInvocationType;
import it.wipidea.cosmicmantra.MantraRunType;
import it.wipidea.cosmicmantra.core.EnMantraConstants;
import it.wipidea.cosmicmantra.core.MantraChannelManager;
import it.wipidea.cosmicmantra.core.MyCosmicMantraCore;
import it.wipidea.cosmicmantra.utils.MantraFileUtil;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class MantraMainController extends AMainController {


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

    public static Vector<Object[]> runners = new Vector<>();
    public static List<Runnable> callableTasks = new ArrayList<>();

    public static ExecutorService executorService;

    public Runnable createTask(MantraSingleController my) {
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

    public void addTask(MantraSingleController my) {
        callableTasks.add(this.createTask(my));
    }

    protected MantraSingleController createMantraSingleController(final String configurationFileName)  throws InterruptedException, IOException {
        MantraSingleController mantraSingleController = new MantraSingleController(configurationFileName);
        return mantraSingleController;
    }
    public MantraMainController(EnMantraInvocationType mantraSwitch)  throws InterruptedException, IOException {
        logger.finest( String.format("Constructing [%s] instance...", MantraMainController.class.getSimpleName()) );


        MantraSingleController msc = null;

        if(mantraSwitch==null)
            mantraSwitch = MantraFileUtil.detectSetting();

        logger.info("mantraSwitch: "  + mantraSwitch);

        switch (mantraSwitch) {
            case Test:
                msc = this.createMantraSingleController("/mantras/AngeloCustodeInvocation_Configuration.properties");
                this.addTask(msc);
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
                msc = this.createMantraSingleController("/mantras/MyCosmicMantra_Configuration.properties");
                this.addTask(msc);
                msc = this.createMantraSingleController("/mantras/HopeNeverDie_Configuration.properties");
                this.addTask(msc);
                msc = this.createMantraSingleController("/mantras/SadhGuruChant_Configuration.properties");
                this.addTask(msc);
                msc = this.createMantraSingleController("/mantras/TheMindIsAMine_Configuration.properties");
                this.addTask(msc);
                break;
            case Cosmic:
                //this.runMantraCosmic();
                msc = this.createMantraSingleController("/mantras/BluesBrotherContact_Configuration.properties");
                this.addTask(msc);
                msc = this.createMantraSingleController("/mantras/WaitingMessiah_Configuration.properties");
                this.addTask(msc);
                break;
            case Christian:
                //this.runMantraChristian();
                msc = this.createMantraSingleController("/mantras/Osanna_Configuration.properties");
                this.addTask(msc);
                msc = this.createMantraSingleController("/mantras/InvocazioneCristo_Configuration.properties");
                this.addTask(msc);
                msc = this.createMantraSingleController("/mantras/PreghieraGesù_Configuration.properties");
                this.addTask(msc);
                msc = this.createMantraSingleController("/mantras/PreghieraLambertNoben_Configuration.properties");
                this.addTask(msc);
                break;
            case Bible:
                //this.runMantraBible();
                msc = this.createMantraSingleController("/mantras/Decalogo_Configuration.properties");
                this.addTask(msc);
                msc = this.createMantraSingleController("/mantras/SalveReginaConfiguration.properties");
                this.addTask(msc);
                break;
            case Latin:
                //this.runMantraLatin();
                msc = this.createMantraSingleController("/mantras/MSICS_Configuration.properties");
                this.addTask(msc);
                msc = this.createMantraSingleController("/mantras/IMSV_Configuration.properties");
                this.addTask(msc);
                break;
            case Angels:
                //this.runMantraAngels();
                msc = this.createMantraSingleController("/mantras/AngeloCustodeInvocation_Configuration.properties");
                this.addTask(msc);
                msc = this.createMantraSingleController("/mantras/PreghieraInvocazioneHAAIAH_Configuration.properties");
                this.addTask(msc);
                msc = this.createMantraSingleController("/mantras/ArchangelInvocationSanMiguel_Configuration.properties");
                this.addTask(msc);
                /*msc = this.createMantraSingleController("/mantras/ArchangelInvocationJOFIEL_Configuration.properties");
                this.addTask(msc);*/
                break;
            case PrecettiCosmici:
                //this.runMantraPrecetti();
                msc = this.createMantraSingleController("/mantras/EptalogoCosmico_Configuration.properties");
                this.addTask(msc);
                msc = this.createMantraSingleController("/mantras/EptalogoCristico_Configuration.properties");
                this.addTask(msc);
                break;
            case PrecettiCosmiciSintetici:
                //this.runMantraPrecettiSintetici();
                msc = this.createMantraSingleController("/mantras/EptalogoCosmicoSintetico_Configuration.properties");
                this.addTask(msc);
                msc = this.createMantraSingleController("/mantras/EptalogoCristicoSintetico_Configuration.properties");
                this.addTask(msc);
                break;
        }

    }

    public void startAppExecutor(MantraRunType en) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, InterruptedException {

        executorService =
                new ThreadPoolExecutor(callableTasks.size(), callableTasks.size(), 0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>());

        //System.out.printf("callableTasks size: %s\n", callableTasks.size());

        //List<Future<>> futures = executorService.invokeAll(callableTasks);

        for (Runnable r : callableTasks) {
            logger.info( String.format("Executing callableTask: %s\n", r.toString()) );
            executorService.execute(r);
        }

        /*SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {*/
        //System.out.println("==> updateMantraDesktopPosition HERE");
        if (en == MantraRunType.ARLEQUIN) {
            MantraChannelManager.updateMantraDesktopPosition();
            MantraChannelManager.updateMantraDesktopArlequinSequence();
        } else if (en == MantraRunType.MONOSEQ) {

        } else if (en == MantraRunType.SCROOGE) {
            MantraChannelManager.updateMantraDesktopPosition();
            MantraChannelManager.updateMantraDesktopScroogeSequence();
            //FIXME: never reached because call above run recursively, to detach
//                    MantraFX.startFairyLights();
        } else if (en == MantraRunType.CONSOLE) {
            //MantraChannelManager.updateMantraDesktopPosition();
            //MantraChannelManager.updateMantraDesktopScroogeSequence();
            // TODO: run only in console mode
            MantraChannelManager.updateMantraDesktopPosition();
        } else {
            MantraChannelManager.updateMantraDesktopPosition();
        }

        //updateMantraDesktopPosition
            /*}
        });*/

    }

    private void setupStats() {


        /*MyJFrame jf = new MyJFrame("Mantra Runner Stats", STATS);

        windowHook = jf;*/

        Long waitForStats = 2000L;

        Thread tStats = new Thread() {
            @Override
            public void run() {
                super.run();
                while (!AMainController.stopRunning) {
                    //System.out.printf("Writing current stats every %s millis!\n", waitForStats);
                    AMainController.writeStats();
                    try {
                        sleep(waitForStats);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("END OF current stats!");
            }
        };

        Thread tExit = new Thread() {
            @Override
            public void run() {
                super.run();
                AMainController.stopRunning = true;
                try {
                    sleep(waitForStats+200L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Writing final stats!");
            }
        };

        tStats.start();

        Runtime.getRuntime().addShutdownHook(tExit);

        windowHook.getContentPane().add(new JPanel());
        windowHook.pack();
        windowHook.setVisible(true);

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
                //FIXME:
                mantraMainController.setupStats();
                //FIXME:
                mantraMainController.startAppExecutor(EN_TYPE);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //System.out.println( "toBinary: " + toBinary(151) );
        }

    }

}
