package it.wipidea.cosmicmantra.core;

import it.wipidea.cosmicmantra.MantraCoreRunner;
import it.wipidea.cosmicmantra.MantraRunType;
import it.wipidea.cosmicmantra.core.objects.MantraWord;
import it.wipidea.cosmicmantra.core.objects.MantraWords;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Nome progetto: MY COSMIC MANTRA
 * TODO: ricreacre in 3D con Unity
 *
 * TODO: aggiungere code di accodamento numeri primi in base a flusso canale, tipo elenco di tutti i numeri primi rilevati/incontrati di mantra cosmic etc
 */

public class MyCosmicMantraCore {

    public Long MANTRA_INTERVAL_KEYWORD;
    public Long MANTRA_INTERVAL_PRAYER;
    public Integer LIMIT_FOR_MANTRA_KEYWORD;

    public final static String LANG_LOV = "love", LANG_LAT="latin", LANG_ITA = "italiano", LANG_ENG = "english", LANG_FRA = "français", LANG_ESP="espanol", LANG_POR="portugais", LANG_DEU="deutsch", LANG_SWE = "swedish", LANG_CHI = "chinese", LANG_HIN = "hindi";

    private String keywordSep = ",";

    protected MantraChannel mantraChannel;

    public MantraWords myMantraKeywords;

    //public Vector<String> vMantraLines;

    public String fileNamePath;

    private ResourceBundle RB;
    public Properties PROPS;
    //private MantraPanelUI window;


    public void prepareMantra(String fileName) {
        //FV: caricamento del file di configurazione del mantra corrente
        PROPS = new Properties();
        try {
            //PROPS.load( Object.class.getClass().getResource( System.getProperty("inputFile") ).openConnection().getInputStream() );
            PROPS.load( this.getClass().getResource( fileName ).openConnection().getInputStream() );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //toadd: Locale swedishLocale = new Locale("sv", "SE");
        keywordSep = PROPS.getProperty("mantra.interval.separator")!=null?PROPS.getProperty("mantra.interval.separator"):",";
        MANTRA_INTERVAL_PRAYER = Long.parseLong( PROPS.getProperty("mantra.interval.prayer") );
        MANTRA_INTERVAL_KEYWORD = Long.parseLong(PROPS.getProperty("mantra.interval.keyword"));
        if ( PROPS.getProperty("mantra.limit.keywords")!=null ) {
            LIMIT_FOR_MANTRA_KEYWORD = Integer.parseInt(PROPS.getProperty("mantra.limit.keywords"));
        } else {
            String kLang = PROPS.getProperty("mantra.prayer.langs").split(",")[0].trim();
            LIMIT_FOR_MANTRA_KEYWORD = PROPS.getProperty("mantra.prayer." + kLang).split(keywordSep).length;//FIX:utilizzare proprietà di configurazione separatore
        }
        System.out.printf("==> Total keywords in action: %s for [%s]\n", LIMIT_FOR_MANTRA_KEYWORD, fileName);
    }

    private void setupChannel() {
        mantraChannel = new MantraChannel(this);
    }

    public MyCosmicMantraCore(String fileName) throws InterruptedException, IOException {
        //RB = ResourceBundle.getBundle("MantraConfiguration" );
        this.fileNamePath = fileName;
        this.prepareMantra(fileName);
        this.setupChannel();

        //vMantraLines = new Vector<String>();

        //window = new MantraPanelUI(vMantraLines, LIMIT_FOR_MANTRA_KEYWORD, MANTRA_INTERVAL_KEYWORD);

        this.loadMantra();
        /*for (MantraWord mw : this.myMantraKeywords.vector) {
            vMantraLines.add(mw.mantraKey);
        }*/
        mantraChannel.initMantraChannel(fileName, this.myMantraKeywords.vector, LIMIT_FOR_MANTRA_KEYWORD, MANTRA_INTERVAL_KEYWORD);
        //window = new MantraPanelUI(fileName, vMantraLines, LIMIT_FOR_MANTRA_KEYWORD, MANTRA_INTERVAL_KEYWORD);
    }


    protected void loadMantra() {
        if ( PROPS.getProperty("mantra.prayer.ita") == null ) {
            System.out.println("launching prayer DEFAULT...");
            this.loadMantraPeaceHarmonyProsperity();
        } else {
            this.loadMantraConfigured();
        }
    }

    protected void loadMantraConfigured() {

        this.myMantraKeywords = new MantraWords(this.fileNamePath );

        String[] mantraLangs = PROPS.getProperty("mantra.prayer.langs").split(",");
        for (String lang : mantraLangs) {
            System.out.println(lang+"="+ PROPS.getProperty("mantra.prayer."+lang.trim()) );
            String[] mantraWords = PROPS.getProperty("mantra.prayer."+lang.trim()).split(keywordSep);

            for (String word : mantraWords) {
                myMantraKeywords.addMantraDirective(lang, word.trim());
            }

        }
    }

    protected void loadMantraPeaceHarmonyProsperity() {

        this.myMantraKeywords = new MantraWords();

        myMantraKeywords.addMantraDirective(LANG_ITA, "Pace");
        myMantraKeywords.addMantraDirective(LANG_ITA, "Armonia");
        myMantraKeywords.addMantraDirective(LANG_ITA, "Prosperità");

        myMantraKeywords.addMantraDirective(LANG_LAT, "Pax");
        myMantraKeywords.addMantraDirective(LANG_LAT, "Concordia");
        myMantraKeywords.addMantraDirective(LANG_LAT, "Prosperitas");

        myMantraKeywords.addMantraDirective(LANG_ESP, "Paz");
        myMantraKeywords.addMantraDirective(LANG_ESP, "Armonía");
        myMantraKeywords.addMantraDirective(LANG_ESP, "Prosperidad");

        myMantraKeywords.addMantraDirective(LANG_POR, "Paz");
        myMantraKeywords.addMantraDirective(LANG_POR, "Harmonia");
        myMantraKeywords.addMantraDirective(LANG_POR, "Prosperidade");

        myMantraKeywords.addMantraDirective(LANG_ENG, "Peace");
        myMantraKeywords.addMantraDirective(LANG_ENG, "Harmony");
        myMantraKeywords.addMantraDirective(LANG_ENG, "Prosperity");

        myMantraKeywords.addMantraDirective(LANG_FRA, "Paix");
        myMantraKeywords.addMantraDirective(LANG_FRA, "Harmonie");
        myMantraKeywords.addMantraDirective(LANG_FRA, "Prospérité");

        myMantraKeywords.addMantraDirective(LANG_DEU, "Frieden");
        myMantraKeywords.addMantraDirective(LANG_DEU, "Harmonie");
        myMantraKeywords.addMantraDirective(LANG_DEU, "Wohlstand");

        myMantraKeywords.addMantraDirective(LANG_SWE, "Fred");
        myMantraKeywords.addMantraDirective(LANG_SWE, "Harmoni");
        myMantraKeywords.addMantraDirective(LANG_SWE, "Välstånd");


        myMantraKeywords.addMantraDirective(LANG_CHI, "和平");
        myMantraKeywords.addMantraDirective(LANG_CHI, "和谐");
        myMantraKeywords.addMantraDirective(LANG_CHI, "繁荣");

        myMantraKeywords.addMantraDirective(LANG_HIN, "शांति");
        myMantraKeywords.addMantraDirective(LANG_HIN, "सद्भाव");
        myMantraKeywords.addMantraDirective(LANG_HIN, "समृद्धि");

        //myMantraKeys.addMantraDirective(LANG_LOV, "Serenità con amore");

    }

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

            MantraSingularityDetector.detectSingularity(mantraChannel, totalMantraApplied, this.fileNamePath);

            MantraSingularityDetector.detectSingularity(mantraChannel, currentTotal, "GLOBAL");

        }

    }

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

            mantraChannel.communicateMessage(cnt, mk, this.LIMIT_FOR_MANTRA_KEYWORD);

            long intervalMk = MANTRA_INTERVAL_PRAYER;

            if (cnt>=LIMIT_FOR_MANTRA_KEYWORD) {
                cnt=0;
                //vMantraLines.clear();
                //FIXME: System.out.println("================================================================");
            } else {
                intervalMk = mantraKeyInterval;
            }

            if (MantraCoreRunner.EN_TYPE == MantraRunType.CONSOLE) {
                //ONLY FOR CONSOLE
                Thread.sleep(intervalMk);
            } else {
//TODO: sleep spostato da Thread Swing MantraFrame a thread esterno a Swing
                try {
                    Thread.sleep(intervalMk);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }



    }

    public static void main(String[] args) throws InterruptedException, IOException {

        /*
        //System.out.println( MyUtils.hasSameCiphers(111L) );
        System.out.println( MyUtils.isPrime(14L) );
        if (true) return;
        */

        MyCosmicMantraCore myMantraInstance = null;
        if (args.length>0&&args[0] != null) {
            myMantraInstance = new MyCosmicMantraCore(args[0]);

            myMantraInstance.startMantra();
        }

    }


}
