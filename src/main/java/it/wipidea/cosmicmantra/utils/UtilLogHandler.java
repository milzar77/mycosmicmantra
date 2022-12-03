package it.wipidea.cosmicmantra.utils;

import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

public class UtilLogHandler extends StreamHandler {

    @Override
    public void publish(LogRecord record) {
        //add own logic to publish
        super.publish(record);
    }


    @Override
    public void flush() {
        super.flush();
    }


    @Override
    public void close() throws SecurityException {
        super.close();
    }


}
