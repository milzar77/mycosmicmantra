package it.wipidea.cosmicmantra.controller;

import it.wipidea.cosmicmantra.EnMantraInvocationType;
import it.wipidea.cosmicmantra.MantraRunType;
import it.wipidea.cosmicmantra.core.EnMantraConstants;
import it.wipidea.cosmicmantra.core.MantraChannelManager;
import it.wipidea.cosmicmantra.utils.MantraFileUtil;

import javax.swing.*;
import java.awt.*;
import java.io.*;
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
        try {
            System.out.println("Reading logging configuration...");
            String path = MantraMainController.class
                    .getClassLoader()
                    .getResource("logging-mantra.properties")
                    .getFile();
            System.out.println("Reading logging configuration from: " + path);
            if (path!=null && path.indexOf('!')!=-1) {
                System.out.println("Reading logging configuration from JAR!");
                InputStream is = MantraMainController.class
                        .getClassLoader()
                        .getResourceAsStream("logging-mantra.properties");
                String newLoggingFile = "extraction-logging.properties";
                File f = new File(newLoggingFile);
                System.out.println("NEW LOGGING CONFIGURATION FILE IN: " + f.getAbsolutePath());
                FileWriter fw = new FileWriter(f);
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ( (line = br.readLine()) != null) {
                    //System.out.println("DEBUG: " + line);
                    fw.write(line+"\n");
                }
                fw.flush();
                fw.close();
                System.setProperty("java.util.logging.config.file", f.getAbsolutePath());
            } else {
                System.setProperty("java.util.logging.config.file", path);
            }

            //log = Logger.getLogger(MantraMainController.class.getName());
            logger = Logger.getLogger(EnMantraConstants.LOGGER_SINGLE_NAME);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    public static boolean IS_TRANSLUCENCY_SUPPORTED;

    public static Vector<Object[]> runners = new Vector<>();
    public static List<Runnable> callableTasks = new ArrayList<>();

    public static List<ASingleController> callableMantraSingleControllers = new ArrayList<>();

    public static ExecutorService executorService;
    private boolean skipOtherMantras = false;

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

        if (mantraSwitch == EnMantraInvocationType.Custom) {
            //TODO: aggiungere caricamento mantra a caldo
            try {
                File fBaseDir = new File(".");
                String currentPath = MantraMainController.class.getResource("").getPath();
                File f1 = new File(currentPath);
                if (f1.getAbsolutePath().indexOf('!') != -1) {
                    File f2 = new File(currentPath.substring(0, currentPath.indexOf('!')));
                    logger.info("Loading customized MANTRA from: " + fBaseDir);
                    fBaseDir = f2.getParentFile();
                    logger.info("Loading customized MANTRA@RUNTIME from: " + fBaseDir);
                } else {
                    logger.info("Loading mantra customized@runtime from " + currentPath);
                }

                if (fBaseDir.getPath().indexOf("file:") != -1) {
                    fBaseDir = new File(fBaseDir.getPath().substring("file:".length()));
                }

                File fMantraAtRuntime = new File(fBaseDir.getAbsolutePath() + "/mantraAtRuntime.properties");
                if (fMantraAtRuntime.exists()) {
                    logger.info("Loading mantra customized from: " + fMantraAtRuntime);
                    msc = this.createMantraSingleController(fMantraAtRuntime.getPath());
                    this.addTask(msc);
                    skipOtherMantras = true;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        logger.info("Loading pre-configured mantras [mantraSwitch="  + mantraSwitch+"]");

        if (!skipOtherMantras && !mantraSwitch.equals(EnMantraInvocationType.Custom)) {
            switch (mantraSwitch) {
                case Test:
                    msc = this.createMantraSingleController("/mantras/Angels/AngeloCustodeInvocation_Configuration.properties");
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
                case Islam:
                    //this.runMantraAngels();
                    msc = this.createMantraSingleController("/mantras/IslamInvocation_Configuration.properties");
                    this.addTask(msc);
                    msc = this.createMantraSingleController("/mantras/InshAllahInvocation_Configuration.properties");
                    this.addTask(msc);
                /*msc = this.createMantraSingleController("/mantras/ArchangelInvocationJOFIEL_Configuration.properties");
                this.addTask(msc);*/
                    break;
                case Angels:
                    //this.runMantraAngels();
                    msc = this.createMantraSingleController("/mantras/Angels/AngeloCustodeInvocation_Configuration.properties");
                    this.addTask(msc);
                    msc = this.createMantraSingleController("/mantras/Angels/PreghieraInvocazioneHAAIAH_Configuration.properties");
                    this.addTask(msc);
                    msc = this.createMantraSingleController("/mantras/Angels/ArchangelInvocationSanMiguel_Configuration.properties");
                    this.addTask(msc);
                /*msc = this.createMantraSingleController("/mantras/Angels/ArchangelInvocationJOFIEL_Configuration.properties");
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
    }

    public void startAppExecutor(MantraRunType en) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, InterruptedException {

        System.err.printf("callableTasks is [%s]\n", callableTasks);

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
            System.err.printf("mantraMainController.callableTasks is [%s]\n", callableTasks);
            System.err.printf("switchParam is [%s]\n", switchParam);

            if (callableTasks.size()==0) {
                System.err.println("/!\\ \tNo callable task pre-loaded, running mantra runtime with hot deploy");
                return;
            }

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
