package it.wipidea.cosmicmantra.controller;

import it.wipidea.cosmicmantra.core.objects.MantraWords;

public interface ISingleController {

    public void prepareMantra(String configurationFileName);

    public void setupChannel();

    public void loadMantra();

    public void loadMantraConfigured();

    public void startMantra() throws InterruptedException;

    public void go(final MantraWords myMantra, final long myStartingMantraInterval) throws InterruptedException;

}
