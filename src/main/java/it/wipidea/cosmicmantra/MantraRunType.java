package it.wipidea.cosmicmantra;

public enum MantraRunType {

    NORMAL,ARLEQUIN,MONOSEQ,SCROOGE, CONSOLE;

    public boolean isValid()  {
        boolean bool = (this != CONSOLE) && (this != SCROOGE);
        return bool;
    }

    public static boolean exists(String arg) {
        MantraRunType en = null;
        try {
            en = MantraRunType.valueOf(arg);
        } catch (java.lang.IllegalArgumentException ex) {
            //ex.printStackTrace();
            System.err.printf("No window sequencer type provided, using default grid disposition.\n");
        }
        return en != null;
    }
}
