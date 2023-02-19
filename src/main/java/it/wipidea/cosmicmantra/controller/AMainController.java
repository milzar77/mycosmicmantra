package it.wipidea.cosmicmantra.controller;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import it.wipidea.cosmicmantra.MantraRunType;

import it.wipidea.cosmicmantra.core.MantraSingularityDetector;
import it.wipidea.cosmicmantra.gui.MyJFrame;
import it.wipidea.cosmicmantra.utils.MantraFileUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class AMainController implements IMainController {

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

    public static String DUMMY_TEST_HEADER = String.format("FROM#%s @%s", ASingleController.class.getSimpleName(), java.time.LocalDateTime.now().toString());

    public final static MyJFrame windowHook;

    protected static boolean stopRunning = false;

    public static MantraRunType EN_TYPE = null;


    static {
        STATS.put(STAT_KEY_TOTALS, 0L);
        STATS.put(STAT_KEY_TOTAL_PRIMENUMS, 0L);

        STATS.put(STAT_KEY_LAST1ST_PRIMENUM, 0L);
        STATS.put(STAT_KEY_LAST2ND_PRIMENUM, 0L);
        STATS.put(STAT_KEY_LAST3RD_PRIMENUM, 0L);

        windowHook = new MyJFrame("Mantra Runner Stats", STATS);
    }

    protected static void writeStats() {

        //STATS
        //STATS.put(STAT_KEY_TOTALS, 0L);
        //STATS.put(STAT_KEY_TOTAL_PRIMENUMS, 0L);

        String[] info2print = {DUMMY_TEST_HEADER, STAT_KEY_TOTALS, STAT_KEY_TOTAL_PRIMENUMS, STAT_KEY_TOTALS_FOR_INSTANCE, STAT_KEY_TOTAL_PRIMES_FOR_INSTANCE};

        MantraFileUtil.statisticaSuFile(STATS, info2print);


    }

    public static MyJFrame getWindowHook(){
        return windowHook;
    }

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

    public static void scriviNumeroPrimo(Long primo) {
        File f = new File(AMainController.PRIMES_OUTPUT_PATH);
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

    }

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

    /*
     *  TODO: Metodo da riportare nella libreria FVLIB
     */
    private static StringBuilder toBinary(int decimal) {
        StringBuilder binary = new StringBuilder();
        while (decimal != 0) {
            String mod = String.valueOf(decimal % 2);
            binary.append(mod);
// Code A
            decimal /= 2;
        }
        return binary.reverse();
    }


    public static HashMap<Long, String> elenco_numeri_primi = MantraSingularityDetector.primesHash;

}
