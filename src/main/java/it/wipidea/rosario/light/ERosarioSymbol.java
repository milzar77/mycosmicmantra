package it.wipidea.rosario.light;

public enum ERosarioSymbol {

    SYM_PATER("\u271D"),//2670=♰ //002b=+ //269a=⚚ //26b5=⚵ //271D=✝ //2795=➕ //2720=✠
    SYM_AVE("\u25CF"),//2022=• //25C9=◉ //25C8=◈ //25CF=● //26AB=⚫ //272A=✪ //2742=❂
    SYM_GLORIA( "\u2600" ),//2055= //2605= //2600=
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
