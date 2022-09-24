package it.wipidea.mantra.objs;

import java.util.Objects;

public class MantraWord {

    public String lang;
    public String mantraKey;

    public MantraWord() {
    }

    public MantraWord(String lang, String mantraKey) {
        this.lang = lang;
        this.mantraKey = mantraKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MantraWord mantraKey1 = (MantraWord) o;
        return Objects.equals(lang, mantraKey1.lang) && Objects.equals(mantraKey, mantraKey1.mantraKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lang, mantraKey);
    }

    @Override
    public String toString() {
        return mantraKey;
    }
}
