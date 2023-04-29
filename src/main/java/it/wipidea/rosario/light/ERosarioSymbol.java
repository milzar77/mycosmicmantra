package it.wipidea.rosario.light;

public enum ERosarioSymbol {

    SYM_PATER("\u002b"),
    SYM_AVE("\u2600"),
    SYM_GLORIA("\u2605"),
    SYM_SALVE("\u2665");

    public String textSymbol;
    public String textPrayerTooltip;

    ERosarioSymbol(String s) {
        this.textSymbol = s;
        this.textPrayerTooltip = "Aut";
        switch (this.name()) {
            case "SYM_PATER":
                this.textPrayerTooltip = "Pater Noster";
                break;
            case "SYM_AVE":
                this.textPrayerTooltip = "Ave Mater";
                break;
            case "SYM_GLORIA":
                this.textPrayerTooltip = "Gloria";
                break;
            case "SYM_SALVE":
                this.textPrayerTooltip = "Salve Regina";
                break;
            default:
                throw new IllegalArgumentException("Invalid enum type!");
        }
    }
}
