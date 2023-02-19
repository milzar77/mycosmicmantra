package it.wipidea.cosmicmantra.controller;

import it.wipidea.cosmicmantra.MantraCoreRunner;
import it.wipidea.cosmicmantra.core.EnMantraConstants;
import it.wipidea.cosmicmantra.core.MantraChannel;
import it.wipidea.cosmicmantra.core.MantraSingularityDetector;
import it.wipidea.cosmicmantra.core.objects.MantraWord;
import it.wipidea.cosmicmantra.core.objects.MantraWords;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Logger;

public final class MantraSingleController extends ASingleController {


    protected final static Logger logger = Logger.getLogger(EnMantraConstants.LOGGER_SINGLE_NAME);

    private File configurationFile;

    public final static String LANG_LOV = "love", LANG_LAT="latin", LANG_ITA = "italiano", LANG_ENG = "english", LANG_FRA = "français", LANG_ESP="espanol", LANG_POR="portugais", LANG_DEU="deutsch", LANG_SWE = "swedish", LANG_CHI = "chinese", LANG_HIN = "hindi", LANG_SAN="sanscrito", LANG_YDD = "yddish", LANG_ARA = "arab";

    private String keywordSep = ",";


    /**
     * @param configurationFileName
     */
    public MantraSingleController(String configurationFileName) throws InterruptedException, IOException {
        super(configurationFileName);
        //logger.setLevel(Level.FINE);//RE-SET CUSTOM LOG LEVEL TO

        logger.finest( String.format("Constructing [%s] instance...", MantraSingleController.class.getSimpleName()) );

        /*this.configurationFile = new File(configurationFileName);

        this.prepareMantra(configurationFileName);

        this.setupChannel();*/

        logger.finest( String.format("Finished construction of [%s] instance...", MantraSingleController.class.getSimpleName()) );

    }

    /**
     * @param configurationFileName
     */
    @Override
    public void prepareMantra(String configurationFileName) {
        if (configurationFile==null)
            configurationFile = new File(configurationFileName);

        logger.finer( String.format("Starting mantra preparation...") );
        logger.info( String.format("Reading mantra from file: [%s]", configurationFile.getAbsolutePath()) );
        logger.finest( String.format("Prepare mantra.") );
        //FV: caricamento del file di configurazione del mantra corrente
        super.PROPS = new Properties();
        try {
            //PROPS.load( Object.class.getClass().getResource( System.getProperty("inputFile") ).openConnection().getInputStream() );
            String fileNameAsResource = configurationFile.getCanonicalPath().replace('\\','/');
            if (fileNameAsResource.indexOf(':')==-1)
                super.PROPS.load( this.getClass().getResource( fileNameAsResource ).openConnection().getInputStream() );
            else {
                try {
                    super.PROPS.load(new FileReader(fileNameAsResource));
                } catch (Exception ex1) {
                    //ex1.printStackTrace();
                    if (ex1 instanceof java.io.FileNotFoundException) {
                        fileNameAsResource = fileNameAsResource.substring(fileNameAsResource.lastIndexOf(':')+1);
                        super.PROPS.load( this.getClass().getResource( fileNameAsResource ).openConnection().getInputStream() );
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //toadd: Locale swedishLocale = new Locale("sv", "SE");
        keywordSep = PROPS.getProperty("mantra.interval.separator")!=null?PROPS.getProperty("mantra.interval.separator"):",";
        super.MANTRA_INTERVAL_PRAYER = Long.parseLong( PROPS.getProperty("mantra.interval.prayer") );
        super.MANTRA_INTERVAL_KEYWORD = Long.parseLong(PROPS.getProperty("mantra.interval.keyword"));
        if ( super.PROPS.getProperty("mantra.limit.keywords")!=null ) {
            super.LIMIT_FOR_MANTRA_KEYWORD = Integer.parseInt(PROPS.getProperty("mantra.limit.keywords"));
        } else {
            String kLang = PROPS.getProperty("mantra.prayer.langs").split(",")[0].trim();
            super.LIMIT_FOR_MANTRA_KEYWORD = PROPS.getProperty("mantra.prayer." + kLang).split(keywordSep).length;//FIX:utilizzare proprietà di configurazione separatore
        }
        logger.finest( String.format("==> Total keywords in action: %s for [%s]\n", super.LIMIT_FOR_MANTRA_KEYWORD, configurationFile.getPath()) );

        logger.finer( String.format("Mantra preparation completed!") );
    }

    /**
     *
     */
    @Override
    public void loadMantra() {
        if ( PROPS.getProperty("mantra.prayer.ita") == null ) {
            System.out.println("launching prayer DEFAULT...");
            this.loadMantraPeaceHarmonyProsperity();
        } else {
            this.loadMantraConfigured();
        }
    }


    /**
     * @throws InterruptedException
     */
    @Override
    public void startMantra() throws InterruptedException {

        Long totalMantraApplied = 0L;
        while (true) {
            this.go(this.myMantraKeywords, MANTRA_INTERVAL_KEYWORD);
            totalMantraApplied++;

            Long currentTotal = (Long) MantraCoreRunner.STATS.getOrDefault(MantraCoreRunner.STAT_KEY_TOTALS, Long.valueOf("0"));
            MantraCoreRunner.STATS.put(MantraCoreRunner.STAT_KEY_TOTALS, currentTotal + 1/*totalMantraApplied*/);

            if (!"GLOBAL".equals(this.fileNamePath)) {
                MantraCoreRunner.STATS.put(MantraCoreRunner.composeKeyForStat(MantraCoreRunner.STAT_KEY_TOTALS_FOR_INSTANCE, this.fileNamePath), totalMantraApplied);
            }

            System.err.printf("[%s]:: Total mantra prayer applied: %s ::\n", this.fileNamePath, totalMantraApplied);

            MantraSingularityDetector.detectSingularity(super.mantraChannel, totalMantraApplied, this.fileNamePath);

            MantraSingularityDetector.detectSingularity(super.mantraChannel, currentTotal, "GLOBAL");

        }

    }


    /**
     * @param myMantra
     * @param myStartingMantraInterval
     * @throws InterruptedException
     */
    @Override
    public final void go(final MantraWords myMantra, final long myStartingMantraInterval) throws InterruptedException {

        Random randomInterval = new Random(myStartingMantraInterval);

        boolean firstTimeHappened = false;
        int cnt = 0;

        for (MantraWord mk : myMantra.vector) {

            long mantraKeyInterval = myStartingMantraInterval;
            if (!firstTimeHappened) {
                firstTimeHappened = true;
            } else {
                if (PROPS.getProperty("mantra.interval.random.seed.activate") == null) {
                    mantraKeyInterval = myStartingMantraInterval;
                    //System.out.println("mantraKeyInterval FIXED:"+mantraKeyInterval);
                } else {
                    mantraKeyInterval = (long)randomInterval.nextInt(this.MANTRA_INTERVAL_KEYWORD.intValue());
                    mantraKeyInterval = Math.abs(mantraKeyInterval);
                    //System.out.println("mantraKeyInterval DYNAMIC:"+mantraKeyInterval);
                }
            }

            cnt++;

            super.mantraChannel.communicateMessage(cnt, mk, this.LIMIT_FOR_MANTRA_KEYWORD);

            long intervalMk = MANTRA_INTERVAL_PRAYER;

            if (cnt>=LIMIT_FOR_MANTRA_KEYWORD) {
                cnt=0;
                //vMantraLines.clear();
                //FIXME: System.out.println("================================================================");
            } else {
                intervalMk = mantraKeyInterval;
            }

            /*if (MantraCoreRunner.EN_TYPE == MantraRunType.CONSOLE) {
                //ONLY FOR CONSOLE
                Thread.sleep(intervalMk);
            } else {
//TODO: sleep spostato da Thread Swing MantraFrame a thread esterno a Swing
                try {
                    Thread.sleep(intervalMk);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }*/

            Thread.sleep(intervalMk);

        }



    }
}
