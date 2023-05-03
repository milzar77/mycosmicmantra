package it.wipidea.rosario.light;

import java.awt.*;

public class RosarioConsts {

    public static final Integer MANTRA_TOTALCOUNT_FORALLOFUS = 5;

    public static final Integer MANTRA_TOTALCOUNT_FORTHEPOPE = 1;

    public static final ERosarioSymbol[] schemeMantraForAllOfUs = {
            ERosarioSymbol.SYM_PATER,
            ERosarioSymbol.SYM_AVE,ERosarioSymbol.SYM_AVE,ERosarioSymbol.SYM_AVE,ERosarioSymbol.SYM_AVE,ERosarioSymbol.SYM_AVE,
            ERosarioSymbol.SYM_AVE,ERosarioSymbol.SYM_AVE,ERosarioSymbol.SYM_AVE,ERosarioSymbol.SYM_AVE,ERosarioSymbol.SYM_AVE,
            ERosarioSymbol.SYM_GLORIA
    };

    public static final ERosarioSymbol[] schemeMantraForThePope = {
            ERosarioSymbol.SYM_PATER,
            ERosarioSymbol.SYM_AVE,ERosarioSymbol.SYM_AVE,ERosarioSymbol.SYM_AVE,
            ERosarioSymbol.SYM_GLORIA
    };

    public static final ERosarioSymbol[] schemeMantraForTheMorningStar = {
            ERosarioSymbol.SYM_SALVE
            //litanie mariane con nomi di Maria
    };

    /*
BG=48,70,111
FG=90,113,156
     */

    public static final Color bgColor1 = new Color(48,70,111);

    public static final Color bgColor2 = new Color(90,113,156);

    public static final Color fgColor1 = new Color(255,205,0);

}
