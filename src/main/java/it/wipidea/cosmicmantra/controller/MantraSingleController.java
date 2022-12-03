package it.wipidea.cosmicmantra.controller;

import it.wipidea.cosmicmantra.core.EnMantraConstants;
import it.wipidea.cosmicmantra.core.MantraChannel;
import it.wipidea.cosmicmantra.core.objects.MantraWords;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import java.util.logging.Logger;

public final class MantraSingleController implements ISingleController {


    protected final static Logger logger = Logger.getLogger(EnMantraConstants.LOGGER_SINGLE_NAME);

    private File configurationFile;

    public Properties PROPS;
    public Long MANTRA_INTERVAL_KEYWORD;
    public Long MANTRA_INTERVAL_PRAYER;
    public Integer LIMIT_FOR_MANTRA_KEYWORD;

    public final static String LANG_LOV = "love", LANG_LAT="latin", LANG_ITA = "italiano", LANG_ENG = "english", LANG_FRA = "français", LANG_ESP="espanol", LANG_POR="portugais", LANG_DEU="deutsch", LANG_SWE = "swedish", LANG_CHI = "chinese", LANG_HIN = "hindi";

    private String keywordSep = ",";


    protected MantraChannel mantraChannel;


    /**
     * @param configurationFileName
     */
    public MantraSingleController(String configurationFileName) {
        //logger.setLevel(Level.FINE);//RE-SET CUSTOM LOG LEVEL TO

        logger.finest( String.format("Constructing [%s] instance...", MantraSingleController.class.getSimpleName()) );

        this.configurationFile = new File(configurationFileName);

        this.prepareMantra(configurationFileName);

        this.setupChannel();

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
        PROPS = new Properties();
        try {
            //PROPS.load( Object.class.getClass().getResource( System.getProperty("inputFile") ).openConnection().getInputStream() );
            String fileNameAsResource = configurationFile.getCanonicalPath().replace('\\','/');
            if (fileNameAsResource.indexOf(':')==-1)
                PROPS.load( this.getClass().getResource( fileNameAsResource ).openConnection().getInputStream() );
            else {
                try {
                    PROPS.load(new FileReader(fileNameAsResource));
                } catch (Exception ex1) {
                    //ex1.printStackTrace();
                    if (ex1 instanceof java.io.FileNotFoundException) {
                        fileNameAsResource = fileNameAsResource.substring(fileNameAsResource.lastIndexOf(':')+1);
                        PROPS.load( this.getClass().getResource( fileNameAsResource ).openConnection().getInputStream() );
                    }
                }
            }
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
        logger.finest( String.format("==> Total keywords in action: %s for [%s]\n", LIMIT_FOR_MANTRA_KEYWORD, configurationFile.getPath()) );

        logger.finer( String.format("Mantra preparation completed!") );
    }

    /**
     *
     */
    @Override
    public void setupChannel() {
        //TODO:FIXME: adeguare a nuova classe
        // mantraChannel = new MantraChannel(this);
    }

    /**
     *
     */
    @Override
    public void loadMantra() {

    }

    /**
     *
     */
    @Override
    public void loadMantraConfigured() {

    }

    /**
     * @throws InterruptedException
     */
    @Override
    public void startMantra() throws InterruptedException {

    }

    /**
     * @param myMantra
     * @param myStartingMantraInterval
     * @throws InterruptedException
     */
    @Override
    public void go(MantraWords myMantra, long myStartingMantraInterval) throws InterruptedException {

    }
}
