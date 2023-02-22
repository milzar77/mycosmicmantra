package it.wipidea.cosmicmantra.controller;

import it.wipidea.cosmicmantra.core.MantraChannel;
import it.wipidea.cosmicmantra.core.objects.MantraWords;

import java.io.IOException;
import java.util.Properties;

public abstract class ASingleController implements ISingleController {

    public Long MANTRA_INTERVAL_KEYWORD;
    public Long MANTRA_INTERVAL_PRAYER;
    public Integer LIMIT_FOR_MANTRA_KEYWORD;

    public final static String LANG_LOV = "love", LANG_LAT="latin", LANG_ITA = "italiano", LANG_ENG = "english", LANG_FRA = "français", LANG_ESP="espanol", LANG_POR="portugais", LANG_DEU="deutsch", LANG_SWE = "swedish", LANG_CHI = "chinese", LANG_CHILAT = "chinese latin", LANG_HIN = "hindi";

    protected String keywordSep = ",";

    protected MantraChannel mantraChannel;

    public MantraWords myMantraKeywords;

    //public Vector<String> vMantraLines;

    public String fileNamePath;

    public /*static*/ Properties PROPS = new Properties();

    /*static {
        //FV: caricamento del file di configurazione del mantra corrente
        PROPS = new Properties();
    }*/

    public abstract void prepareMantra(String fileName);

    /*public void prepareMantra(String fileName) {
        try {
            //PROPS.load( Object.class.getClass().getResource( System.getProperty("inputFile") ).openConnection().getInputStream() );
            PROPS.load( ASingleController.class.getResource( fileName ).openConnection().getInputStream() );
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
    }*/

    public void setupChannel() {
        //TODO:FIXME: adeguare a nuova classe
        mantraChannel = new MantraChannel(this);
    }

    public ASingleController(String fileName) throws InterruptedException, IOException {
        //RB = ResourceBundle.getBundle("MantraConfiguration" );
        this.fileNamePath = fileName;
        MantraMainController.callableMantraSingleControllers.add(this);
        //PROPS = new Properties();
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

    public void loadMantraConfigured() {

        this.myMantraKeywords = new MantraWords(this.fileNamePath );

        String[] mantraLangs = PROPS.getProperty("mantra.prayer.langs").split(",");
        for (String lang : mantraLangs) {
            System.out.println(lang+"="+ PROPS.getProperty("mantra.prayer."+lang.trim()) );
            String mantraSentence = PROPS.getProperty("mantra.prayer."+lang.trim());
            String[] mantraWords = {};
            if (mantraSentence.indexOf("\n")!=-1)
                mantraWords = mantraSentence.split(/*keywordSep*/PROPS.getProperty("mantra.interval.separator"));
            else
                mantraWords = new String[]{mantraSentence};

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

}
