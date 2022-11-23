package it.wipidea.cosmicmantra.core.objects;


import java.util.Vector;

public class MantraWords {

    private String name;
    public MantraWords(){
        this.name = "Generic Mantra Sequencer";
    }

    public MantraWords(String pName){
        this.name = pName;
    }

    public Vector<MantraWord> vector = new Vector<MantraWord>();

    public void add(MantraWord mk){
        if (!vector.contains(mk)) vector.add(mk);
    }

    public void addMantraDirective(String lang, String mantraKey){
        //if (!vector.contains(mk)) vector.add(mk);
        MantraWord mk = new MantraWord(lang, mantraKey);
        vector.add(mk);
    }

}

