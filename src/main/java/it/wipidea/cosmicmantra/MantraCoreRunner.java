package it.wipidea.cosmicmantra;


import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import it.wipidea.cosmicmantra.core.MantraChannelManager;
import it.wipidea.cosmicmantra.core.MantraSingularityDetector;
import it.wipidea.cosmicmantra.core.MyCosmicMantraCore;
import it.wipidea.cosmicmantra.gui.MyJFrame;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.*;

public class MantraCoreRunner {

    public static final String STATS_GLOBAL_PATH = "tmp/stats-global.csv";

    public static final String STATS_CHRONO_PATH = "tmp/stats-chrono.csv";

    public static final String STATS_CHRONO_SINGULARITY_PATH = "tmp/stats-chrono-singularity.csv";

    public static final String PRIMES_OUTPUT_PATH = "tmp/primes-output.csv";

    public static final String GFX_PATH = "tmp/desktop-grid-share.tmp";
    //public static final String GFX_PATH_TMP = "/tmp/temp.txt";
    public final static short MANTRA_CLASS = 0, MANTRA_CONFIGURATION = 1;

    public final static int FLAG_TEST = -1, FLAG_ALL = 0, FLAG_COSMIC = 1, FLAG_CHRISTIAN = 2, FLAG_BIBLE = 3, FLAG_LATIN = 4, FLAG_ANGELS = 5, FLAG_SECRET = 6, FLAG_PRECETTI_COSMICI = 7, FLAG_PRECETTI_COSMICI_SINTETICI = 8;

    public final static String STAT_KEY_TOTALS = "totalIterations";

    public final static String STAT_KEY_TOTALS_FOR_INSTANCE = "totalIterationsFor";

    public final static String STAT_KEY_TOTAL_PRIMENUMS = "totalPrimeNums";

    public static final String STAT_KEY_TOTAL_PRIMES_FOR_INSTANCE = "totalPrimesFor";

    public final static String STAT_KEY_LAST1ST_PRIMENUM = "last1stPrimeNum";
    public final static String STAT_KEY_LAST2ND_PRIMENUM = "last2ndPrimeNum";
    public final static String STAT_KEY_LAST3RD_PRIMENUM = "last3rdPrimeNum";


    public final static ConcurrentHashMap STATS = new ConcurrentHashMap();
    private static boolean stopRunning = false;

    public static String DUMMY_TEST_HEADER = String.format("FROM#%s @%s", MyCosmicMantraCore.class.getSimpleName(), java.time.LocalDateTime.now().toString());

    static {
        STATS.put(STAT_KEY_TOTALS, 0L);
        STATS.put(STAT_KEY_TOTAL_PRIMENUMS, 0L);

        STATS.put(STAT_KEY_LAST1ST_PRIMENUM, 0L);
        STATS.put(STAT_KEY_LAST2ND_PRIMENUM, 0L);
        STATS.put(STAT_KEY_LAST3RD_PRIMENUM, 0L);


        //STATS.put(STAT_KEY_TOTALS_FOR_INSTANCE, 0L);
    }

    static { // initializing shared coordinates

        initFile(GFX_PATH, true, true, "");

        initFile(STATS_GLOBAL_PATH, false, false, DUMMY_TEST_HEADER);

        initFile(PRIMES_OUTPUT_PATH, false, false, DUMMY_TEST_HEADER);

    }

    public static MyJFrame windowHook;

    public static void initFile(String fileName, boolean appendFile, boolean deleteOnExit, String dummyWrite) {
        File f1 = new File( fileName );
        if (!f1.exists()) {
            f1.getParentFile().mkdirs();
        } else {
            f1.delete();
        }

        try {
            FileWriter fw = new FileWriter(f1, appendFile);
            if (dummyWrite==null) {
                dummyWrite = "";
            }
            fw.write(dummyWrite);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (deleteOnExit) {f1.deleteOnExit();}
    }

    public static Vector<Object[]> runners = new Vector<>();
    public static List<Runnable> callableTasks = new ArrayList<>();

    public static ExecutorService executorService;

    public static String composeKeyForStat(String statKey, String fileNamePath) {
        return statKey + composeSeedFor(fileNamePath);
    }

    private static String composeKeyFromStat(Object k, String statKeyTotalsForInstance) {
        throw new RuntimeException("Not Yet Implemented!");
    }

    public static String composeSeedFor(String fileNamePath) {
        //return fileNamePath;
        //return ""+fileNamePath.hashCode();
        /*String partial = fileNamePath.substring(1);
        return partial.substring(0, partial.indexOf("_"));*/
        String partial = fileNamePath.replace('/','-');
        if (partial.indexOf("_")==-1)
            return partial;
        return partial.substring(0, partial.indexOf("_"));
    }

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

    public void runMantraTest() throws IOException, InterruptedException {
        this.runMantraTest1();
    }

    public void runMantraTest1() throws IOException, InterruptedException {
        this.addTask(new MyCosmicMantraCore("/MyCosmicMantra_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/BluesBrotherContact_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/HopeNeverDie_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/SadhGuruChant_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/WaitingMessiah_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/TheMindIsAMine_Configuration.properties"));

    }

    public void runMantraTest2() throws IOException, InterruptedException {
        this.addTask(new MyCosmicMantraCore("/MyCosmicMantra_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/BluesBrotherContact_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/HopeNeverDie_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/SadhGuruChant_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/WaitingMessiah_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/TheMindIsAMine_Configuration.properties"));

        this.addTask(new MyCosmicMantraCore("/Osanna_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/InvocazioneCristo_Configuration.properties"));

    }

    public void runMantraTest999() throws IOException, InterruptedException {
        this.addTask(new MyCosmicMantraCore("/MyCosmicMantra_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/BluesBrotherContact_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/HopeNeverDie_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/SadhGuruChant_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/WaitingMessiah_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/TheMindIsAMine_Configuration.properties"));

        this.addTask(new MyCosmicMantraCore("/Osanna_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/InvocazioneCristo_Configuration.properties"));

        this.addTask(new MyCosmicMantraCore("/Decalogo_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/SalveReginaConfiguration.properties"));
    }
    public void runMantraSecret() throws IOException, InterruptedException {
        this.addTask(new MyCosmicMantraCore("/MyCosmicMantra_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/HopeNeverDie_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/SadhGuruChant_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/TheMindIsAMine_Configuration.properties"));
    }

    public void runMantraCosmic() throws IOException, InterruptedException {
        this.addTask(new MyCosmicMantraCore("/BluesBrotherContact_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/WaitingMessiah_Configuration.properties"));
    }

    public void runMantraChristian() throws IOException, InterruptedException {
        this.addTask(new MyCosmicMantraCore("/Osanna_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/InvocazioneCristo_Configuration.properties"));
    }

    public void runMantraBible() throws IOException, InterruptedException {
        this.addTask(new MyCosmicMantraCore("/Decalogo_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/SalveReginaConfiguration.properties"));
    }

    public void runMantraLatin() throws IOException, InterruptedException {

        this.addTask(new MyCosmicMantraCore("/MSICS_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/IMSV_Configuration.properties"));

    }

    public void runMantraAngels() throws IOException, InterruptedException {

        this.addTask(new MyCosmicMantraCore("/ArchangelInvocationSanMiguel_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/ArchangelInvocationJOFIEL_Configuration.properties"));

    }

    public void runMantraPrecetti() throws IOException, InterruptedException {

        this.addTask(new MyCosmicMantraCore("/EptalogoCosmico_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/EptalogoCristico_Configuration.properties"));

    }

    public void runMantraPrecettiSintetici() throws IOException, InterruptedException {

        this.addTask(new MyCosmicMantraCore("/EptalogoCosmicoSintetico_Configuration.properties"));
        this.addTask(new MyCosmicMantraCore("/EptalogoCristicoSintetico_Configuration.properties"));

    }

    public Integer detectSetting() {


        java.util.Map map =  System.getenv();

        System.out.println("===ENV===");
//        System.out.println(map);
        for (Object o : map.keySet()) {
            System.out.println(String.valueOf(o) + ": "  + map.get(o));
        }
        System.out.println("---ENV---");



        String test = System.getenv("PWD");
        if (test==null) {
        	System.out.println("Hello World! You Are Running Cosmic Mantra from Within A WINDOWS");
        	
        	//test = System.getenv("=Z:");
            test = System.getenv("=Z:");
            if (test==null) {
                test = "Z:/tmp/mycosmicmantra/PrecettiCosmiciSintetici";
            }
            System.out.printf("CURRENT DIR is [%s]\n", test);
        	/*
        	System.exit(1);
        	return FLAG_ALL;
        	*/
        } else {
                System.out.printf("PWD is [%s]\n", test);
        }
        File fTest = new File(test);
        if (fTest.exists() && fTest.isDirectory()) {
            ;//System.out.println("CHECKING IF THIS IS THE LAST ELEMENT OF DIR PATH: "+fTest.getName());
        } else {
            System.exit(1);
        }

        String arg = fTest.getName();

        if (arg.equals("Secret")) {
            return FLAG_SECRET;
        } else if (arg.equals("Cosmic")) {
            return FLAG_COSMIC;
        } else if (arg.equals("Christian")) {
            return FLAG_CHRISTIAN;
        } else if (arg.equals("Bible")) {
            return FLAG_BIBLE;
        } else if (arg.equals("Latin")) {
            return FLAG_LATIN;
        } else if (arg.equals("Angels")) {
            return FLAG_ANGELS;
        } else if (arg.equals("PrecettiCosmici")) {
            return FLAG_PRECETTI_COSMICI;
        } else if (arg.equals("PrecettiCosmiciSintetici")) {
            return FLAG_PRECETTI_COSMICI_SINTETICI;
        } else if (arg.equals("playground")) {
            return FLAG_TEST;
        } else {
            return FLAG_ALL;
        }
    }

    public static HashMap<Long, String> elenco_numeri_primi = MantraSingularityDetector.primesHash;

    public MantraCoreRunner() throws IOException, InterruptedException {

        Integer MYSWITCH = detectSetting();

        switch (MYSWITCH) {
            case FLAG_TEST:
                this.runMantraTest();
                break;
            case FLAG_ALL:
                this.runMantraSecret();
                this.runMantraCosmic();
                this.runMantraChristian();
                this.runMantraBible();
                this.runMantraLatin();
                this.runMantraPrecetti();
                break;
            case FLAG_SECRET:
                this.runMantraSecret();
                break;
            case FLAG_COSMIC:
                this.runMantraCosmic();
                break;
            case FLAG_CHRISTIAN:
                this.runMantraChristian();
                break;
            case FLAG_BIBLE:
                this.runMantraBible();
                break;
            case FLAG_LATIN:
                this.runMantraLatin();
                break;
            case FLAG_ANGELS:
                this.runMantraAngels();
                break;
            case FLAG_PRECETTI_COSMICI:
                this.runMantraPrecetti();
                break;
            case FLAG_PRECETTI_COSMICI_SINTETICI:
                this.runMantraPrecettiSintetici();
                break;
        }

        /*
        Runnable callableTask1 = () -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                    new MyCosmicMantra("/MyMantraConfiguration.properties").startMantra();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        };
        callableTasks.add(callableTask1);

        Runnable callableTask2 = () -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(2500);
                    new CosmicInvocation("/InvocazioneCristo_Configuration.properties").startMantra();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        };
        callableTasks.add(callableTask2);
        */

    }

    public static void scriviNumeroPrimo(Long primo) {
        File f = new File(MantraCoreRunner.PRIMES_OUTPUT_PATH);
        try {
            FileWriter fw = new FileWriter(f, true);
            fw.append( String.format("%s;\n", primo) );
            fw.flush();
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void detectStats() {

        //STATS
        //STATS.put(STAT_KEY_TOTALS, 0L);
        //STATS.put(STAT_KEY_TOTAL_PRIMENUMS, 0L);
        //STATS.put(STAT_KEY_TOTALS_FOR_INSTANCE, 0L);

    }

    private static void writeStats() {

        //STATS
        //STATS.put(STAT_KEY_TOTALS, 0L);
        //STATS.put(STAT_KEY_TOTAL_PRIMENUMS, 0L);
        //STATS.put(STAT_KEY_TOTALS_FOR_INSTANCE, 0L);

        scriviStatistica(STATS);


    }

    private static void scriviStatistica(ConcurrentHashMap hash) {
        File f = new File(MantraCoreRunner.STATS_GLOBAL_PATH);
        try {
            FileWriter fw = new FileWriter(f, false);
            //fw.append( String.format( "x=%s;y=%s;w=%s;h=%s", ((Point)objects[0]).x, ((Point)objects[0]).y, ((Dimension)objects[1]).width, ((Dimension)objects[1]).height ));
            fw.append(DUMMY_TEST_HEADER+"\n");
            fw.append( String.format(
                    "ITERAZIONI TOTALI=%s;" +
                    "NUMERI PRIMI TOTALI=%s;" +
                    "\n",
                    hash.get(STAT_KEY_TOTALS),
                    hash.get(STAT_KEY_TOTAL_PRIMENUMS)
            ));

            for (Object k : hash.keySet()) {
                if (k.toString().startsWith(STAT_KEY_TOTALS_FOR_INSTANCE)) {
                    fw.append(
                            String.format("%s=%s;" + "\n", k, hash.get( k ) )
                    );
                    String kSep = STAT_KEY_TOTAL_PRIMES_FOR_INSTANCE+k.toString().substring(k.toString().indexOf("-"));
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

    public static MantraRunType EN_TYPE = null;

    public static boolean isTranslucencySupported;


    public void speak() {
//java.lang.NoClassDefFoundError: com/sun/speech/freetts/VoiceManager
        try {


            if (true)
            {
                System.err.println("Speech synth disabled, cercare motivo per cui non carica correttamente su Windows le voci, mentre su Linux risultano rovinate");
                return;
            }

            //System.setProperty("mbrola.base", "/usr/share/mbrola/");
            System.setProperty("mbrola.base", "/home/goldenplume/tmp/mbrola-voices/");
//            System.setProperty("mbrola.base", "D:/apps/Mbrola Tools/");
            //System.setProperty("mbrola.base", "D:/apps/mbrola-tools/");
            System.setProperty("freetts.voices", "de.dfki.lt.freetts.en.us.MbrolaVoiceDirectory");
            //System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

            VoiceManager voiceManager = VoiceManager.getInstance();

            Voice[] voices = voiceManager.getVoices();
            for (int i = 0; i < voices.length; i++) {
                System.out.println("    " + voices[i].getName()
                        + " (" + voices[i].getDomain() + " domain)");
            }

            final String VOICENAME_kevin =
                //"kevin";
                //"kevin16";
                    "mbrola_it2";
            Voice voice;

            voice = voiceManager.getVoice(VOICENAME_kevin);
            voice.allocate();




            //String newText = "Welcome Back Bro!";
            //String newText = "Bentornato Fratello!";
            String newText = "In medio stat virtus";
            voice.speak(newText);
        } catch (NoClassDefFoundError ex) {
            System.err.println("No speech library detected, disabling FreeTTS support.\n"+
                    ex.getMessage());
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

            isTranslucencySupported = gd.isWindowTranslucencySupported(java.awt.GraphicsDevice.WindowTranslucency.TRANSLUCENT);
            if (!isTranslucencySupported) {
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

            MantraCoreRunner mantras = new MantraCoreRunner();

            mantras.speak();

            if (EN_TYPE != MantraRunType.CONSOLE) {
                mantras.setupStats();
                mantras.startAppExecutor(EN_TYPE);
            }

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupStats() {


        MyJFrame jf = new MyJFrame("Mantra Runner Stats", STATS);

        windowHook = jf;

        Long waitForStats = 2000L;

        Thread tStats = new Thread() {
            @Override
            public void run() {
                super.run();
                while (!MantraCoreRunner.stopRunning) {
                    //System.out.printf("Writing current stats every %s millis!\n", waitForStats);
                    MantraCoreRunner.writeStats();
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
                MantraCoreRunner.stopRunning = true;
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

        jf.getContentPane().add(new JPanel());
        jf.pack();
        jf.setVisible(true);

    }

    public void startAppExecutor(MantraRunType en) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, InterruptedException {

        executorService =
                new ThreadPoolExecutor(callableTasks.size(), callableTasks.size(), 0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>());

        //System.out.printf("callableTasks size: %s\n", callableTasks.size());

        //List<Future<>> futures = executorService.invokeAll(callableTasks);

        for (Runnable r : callableTasks) {
            System.out.printf("Executing callableTask: %s\n", r.toString());
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

    public Object rt = null;
    private void creaIstanzaMantra(Object[] o) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Constructor c = ((Class<?>)o[MANTRA_CLASS]).getDeclaredConstructor(o[MANTRA_CONFIGURATION].getClass());
        Method m = null;
        Class objClass = ((Class<?>)o[MANTRA_CLASS]);
        if (objClass.getSuperclass().getSimpleName().contains("MyCosmicMantra")) {
            m = objClass.getSuperclass().getDeclaredMethod("startMantra");
        } else {
            m = objClass.getDeclaredMethod("startMantra");
        }

        //Method m = ((Class<?>)o[MANTRA_CLASS]).getDeclaredMethod("startMantra");
        Object objInstance = c.newInstance(o[MANTRA_CONFIGURATION]);
        //System.out.printf("This is a valid instance of: %s\n",objInstance);
        MantraCoreRunner.this.rt = m.invoke(objInstance);
        //System.out.printf("Until when loop cycle interrupted this is the return value: %s\n",rt);

    }

}

