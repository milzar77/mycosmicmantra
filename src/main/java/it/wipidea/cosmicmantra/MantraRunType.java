package it.wipidea.cosmicmantra;

public enum MantraRunType {

    NORMAL,ARLEQUIN,MONOSEQ,SCROOGE, CONSOLE;

    public boolean isValid()  {
        boolean bool = (this != CONSOLE) && (this != SCROOGE);
        return bool;
    }

}
