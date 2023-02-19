package it.wipidea.cosmicmantra.core;

import it.wipidea.cosmicmantra.controller.AMainController;
import it.wipidea.cosmicmantra.controller.ASingleController;
import it.wipidea.cosmicmantra.utils.MantraUtil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Vector;

public class MantraSingularityDetector {

    public static Vector<Long> vSingularity;

    static {
        vSingularity = new Vector<>();

        LocalDate localDate = LocalDate.of(1977, 7, 31);
        Vector<Long> v1 = MantraUtil.myDateExtractor(localDate);
        for (Long l : v1) {
            vSingularity.add(l);
        }

        LocalDate localDate2 = LocalDate.of(1994, 5, 22);
        Vector<Long> v2 = MantraUtil.myDateExtractor(localDate2);
        for (Long l : v2) {
            vSingularity.add(l);
        }

        LocalDate localDate3 = LocalDate.of(1995, 11, 30);
        Vector<Long> v3 = MantraUtil.myDateExtractor(localDate3);
        for (Long l : v3) {
            vSingularity.add(l);
        }


    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    //public static Vector primesHash = new Vector();
    public static HashMap<Long, String> primesHash = new HashMap<Long, String>();

    public static void detectSingularity(MantraChannel mantraChannel, Long totalMantraApplied, String seedName) {

        if ( ciclaIlMondo(totalMantraApplied) ) {//fare con regexp su vector.toString
            //number in vSingularity
            System.err.printf("[%s]==> ZUPER SINGULARITY It's a mantra cipher singularity because the cipher of [%s] is a special number for me\n", seedName, totalMantraApplied);
        }

        if (MantraUtil.hasSameCiphers(totalMantraApplied)) {
            System.err.printf("[%s]==> It's a mantra cipher singularity because all ciphers of [%s] are the SAME\n", seedName, totalMantraApplied);
        }
        if (MantraUtil.isPrime(totalMantraApplied)) {

            mantraChannel.mantraWindow.showStar = true;

            primesHash.put(totalMantraApplied, "WOW! this is a prime number.\n" +
                    "INSERIRE QUANTE MEDESIME OCCORRENZE TROVATE IN COMPLESSIVAMENTE");

            if (!"GLOBAL".equals(seedName)) {
                Long currentTotalPrimesFor = (Long) AMainController.STATS.getOrDefault(AMainController.composeKeyForStat(AMainController.STAT_KEY_TOTAL_PRIMES_FOR_INSTANCE, seedName), Long.valueOf("0"));
                AMainController.STATS.put(AMainController.composeKeyForStat(AMainController.STAT_KEY_TOTAL_PRIMES_FOR_INSTANCE, seedName), currentTotalPrimesFor+1);
            }

            //STAT_KEY_LAST1ST_PRIMENUM: da vedere se usare coda lifo pop stack etc
            Long current1stPrime = (Long) AMainController.STATS.getOrDefault(AMainController.STAT_KEY_LAST1ST_PRIMENUM, Long.valueOf("0"));
            if (current1stPrime<totalMantraApplied)
                AMainController.STATS.put(AMainController.STAT_KEY_LAST1ST_PRIMENUM, totalMantraApplied);

            //if (primesHash.contains(totalMantraApplied))
            if (primesHash.containsKey(totalMantraApplied))
                return;

            Long currentTotalPrimes = (Long) AMainController.STATS.getOrDefault(AMainController.STAT_KEY_TOTAL_PRIMENUMS, Long.valueOf("0"));
            if (currentTotalPrimes<totalMantraApplied)
                AMainController.STATS.put(AMainController.STAT_KEY_TOTAL_PRIMENUMS, currentTotalPrimes+1);



            System.err.printf("[%s]==> It's a mantra number singularity because [%s] is a PRIME number\n", seedName, totalMantraApplied);

            //if (primesHash.contains(totalMantraApplied))
            if (primesHash.containsKey(totalMantraApplied))
                return;
            AMainController.scriviNumeroPrimo(totalMantraApplied);



        } else {
            mantraChannel.mantraWindow.showStar = false;
        }
        /*if () {
                System.out.println("==> It's a mantra cipher singularity because the COUNT of ciphers it is a SPECIAL/PRIME number");
        }*/

    }

    private static boolean ciclaIlMondo(Long test) {
        boolean isFound = false;
        for (Long l : vSingularity) {
            if (test==l) { isFound = true; break; }
        }
        return isFound;
    }
}
