package it.wipidea.cosmicmantra.utils;

import it.wipidea.cosmicmantra.MantraCoreRunner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class MantraFileUtil {

    public static void statisticaSuFile(ConcurrentHashMap hash, String[] info2print) {
        File f = new File(MantraCoreRunner.STATS_GLOBAL_PATH);
        try {
            FileWriter fw = new FileWriter(f, false);
            //fw.append( String.format( "x=%s;y=%s;w=%s;h=%s", ((Point)objects[0]).x, ((Point)objects[0]).y, ((Dimension)objects[1]).width, ((Dimension)objects[1]).height ));
            fw.append(info2print[0]+"\n");
            fw.append( String.format(
                    "ITERAZIONI TOTALI=%s;" +
                            "NUMERI PRIMI TOTALI=%s;" +
                            "\n",
                    hash.get(info2print[1]),
                    hash.get(info2print[2])
            ));

            for (Object k : hash.keySet()) {
                if (k.toString().startsWith(info2print[3])) {
                    fw.append(
                            String.format("%s=%s;" + "\n", k, hash.get( k ) )
                    );
                    String kSep = info2print[4]+k.toString().substring(k.toString().indexOf("-"));
                    //System.out.println("DEBUG SEP = " + kSep);
                    if (hash.containsKey(kSep)) {
                        //System.out.println("DEBUG CHECK IT OUT THIS ! ");
                        fw.append(
                                String.format("%s=%s;" + "\n", kSep, hash.get( kSep ) )
                        );
                    }
                    //composeSeedFor
                }
            }

            fw.flush();
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
