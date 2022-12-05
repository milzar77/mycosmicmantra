package it.wipidea.cosmicmantra.controller;

import it.wipidea.cosmicmantra.MantraRunType;
import it.wipidea.cosmicmantra.core.MyCosmicMantraCore;
import it.wipidea.cosmicmantra.gui.MyJFrame;
import it.wipidea.cosmicmantra.utils.MantraFileUtil;

import java.util.concurrent.ConcurrentHashMap;

public class AMainController implements IMainController {

    public final static String STAT_KEY_TOTALS = "totalIterations";

    public final static String STAT_KEY_TOTALS_FOR_INSTANCE = "totalIterationsFor";

    public final static String STAT_KEY_TOTAL_PRIMENUMS = "totalPrimeNums";

    public static final String STAT_KEY_TOTAL_PRIMES_FOR_INSTANCE = "totalPrimesFor";

    public final static String STAT_KEY_LAST1ST_PRIMENUM = "last1stPrimeNum";
    public final static String STAT_KEY_LAST2ND_PRIMENUM = "last2ndPrimeNum";
    public final static String STAT_KEY_LAST3RD_PRIMENUM = "last3rdPrimeNum";
    public final static ConcurrentHashMap STATS = new ConcurrentHashMap();

    public static String DUMMY_TEST_HEADER = String.format("FROM#%s @%s", ASingleController.class.getSimpleName(), java.time.LocalDateTime.now().toString());

    protected final static MyJFrame windowHook;

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

}
