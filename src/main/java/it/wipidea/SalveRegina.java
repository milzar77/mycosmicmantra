package it.wipidea;

import java.io.IOException;

/**
 * >> Ciò che resta da fare è pregare tutti assieme all'unisono, più per ottenere sollievo morale,
 * >> e più che altro per allietare i nostri animi in momenti così difficili,
 * >> infatti non si sa quanto queste preghiere possano sortire il risultato sperato, ossia è in atto la determinazione di un area di calore molto critica e sopra la media nel bacino del Mediterraneo.
 * >> Poi chissà più la preghiera è arte pura più potrebbe dare anche risultati concreti.
 * >> Vedo un futuro in cui le anime più sensibili alla sorte del pianeta torneranno ad una sorta
 * >> di vita monastica, nel senso di rinascita e non tanto per le privazioni e morigeratezza che solo
 * >> alcuni si possono sentire di provare. Forse da monaci laici faremo meno errori, e soprattutto inquineremo, dato che non sembra essere possibile una purezza totale,
 * >> almeno quel tanto che basta a provvedere ai bisogni primari per esistere in armonia con il creato.
 */
public class SalveRegina extends it.wipidea.MyCosmicMantra {


    public SalveRegina(String arg) throws IOException, InterruptedException {
        super(arg);
    }

    public final static void main(String[] args) throws InterruptedException, IOException {
        SalveRegina myMantraInstance = new SalveRegina(args[0]);
        myMantraInstance.startMantra();
    }


}
