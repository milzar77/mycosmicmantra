package it.wipidea;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;

public class MantraRunner {

    public final static short MANTRA_CLASS = 0, MANTRA_CONFIGURATION = 1;

    public final static String STAT_KEY_TOTALS = "totalIterations";

    public final static int FLAG_TEST = -1, FLAG_ALL = 0, FLAG_COSMIC = 1, FLAG_CHRISTIAN = 2, FLAG_BIBLE = 3, FLAG_LATIN = 4, FLAG_ANGELS = 5;
    public final static ConcurrentHashMap STATS = new ConcurrentHashMap();
    {
        STATS.put(STAT_KEY_TOTALS, 0L);
    }

    private Vector<Object[]> runners = new Vector<>();
    List<Runnable> callableTasks = new ArrayList<>();

    public Runnable createTask(MyCosmicMantra my) {
        Runnable callableTask = () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
                my.startMantra();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        return callableTask;
    }

    public void addTask(MyCosmicMantra my) {
        callableTasks.add(this.createTask(my));
    }

    public void runMantraTest() throws IOException, InterruptedException {
        this.addTask(new MyCosmicMantra("/MyCosmicMantra_Configuration.properties"));
        this.addTask(new CosmicInvocation("/SadhGuruChant_Configuration.properties"));
        this.addTask(new CosmicInvocation("/TheMindIsAMine_Configuration.properties"));
    }
    public void runMantraCosmic() throws IOException, InterruptedException {
        this.addTask(new MyCosmicMantra("/MyCosmicMantra_Configuration.properties"));
        this.addTask(new CosmicInvocation("/BluesBrotherContact_Configuration.properties"));
        this.addTask(new MyCosmicMantra("/HopeNeverDie_Configuration.properties"));
        this.addTask(new CosmicInvocation("/SadhGuruChant_Configuration.properties"));
        this.addTask(new CosmicInvocation("/WaitingMessiah_Configuration.properties"));
        this.addTask(new CosmicInvocation("/TheMindIsAMine_Configuration.properties"));
    }

    public void runMantraChristian() throws IOException, InterruptedException {
        this.addTask(new CosmicInvocation("/Osanna_Configuration.properties"));
        this.addTask(new CosmicInvocation("/InvocazioneCristo_Configuration.properties"));
    }

    public void runMantraBible() throws IOException, InterruptedException {
        this.addTask(new CosmicInvocation("/Decalogo_Configuration.properties"));
        this.addTask(new SalveRegina("/SalveReginaConfiguration.properties"));
    }

    public void runMantraLatin() throws IOException, InterruptedException {

        this.addTask(new ProverbiLatini("/MSICS_Configuration.properties"));
        this.addTask(new ProverbiLatini("/IMSV_Configuration.properties"));

    }

    public void runMantraAngels() throws IOException, InterruptedException {

        //this.addTask(new CosmicInvocation("/Osanna_Configuration.properties"));
        this.addTask(new CosmicInvocation("/ArchangelInvocationJOFIEL_Configuration.properties"));

    }

    public Integer detectSetting() {

        String test = System.getenv("PWD");
        File fTest = new File(test);
        if (fTest.exists() && fTest.isDirectory()) {
            ;//System.out.println("CHECKING IF THIS IS THE LAST ELEMENT OF DIR PATH: "+fTest.getName());
        } else {
            System.exit(1);
        }

        String arg = fTest.getName();

        if (arg.equals("Cosmic")) {
            return FLAG_COSMIC;
        } else if (arg.equals("Christian")) {
            return FLAG_CHRISTIAN;
        } else if (arg.equals("Bible")) {
            return FLAG_BIBLE;
        } else if (arg.equals("Latin")) {
            return FLAG_LATIN;
        } else if (arg.equals("Angels")) {
            return FLAG_ANGELS;
        } else if (arg.equals("playground")) {
            return FLAG_TEST;
        } else {
            return FLAG_ALL;
        }
    }


    public MantraRunner() throws IOException, InterruptedException {

        Integer MYSWITCH = detectSetting();

        switch (MYSWITCH) {
            case FLAG_TEST:
                this.runMantraTest();
                break;
            case FLAG_ALL:
                this.runMantraCosmic();
                this.runMantraChristian();
                this.runMantraBible();
                this.runMantraLatin();
                break;
            case FLAG_COSMIC:
                this.runMantraCosmic();
                break;
            case FLAG_CHRISTIAN:
                this.runMantraChristian();
                break;
            case FLAG_BIBLE:
                this.runMantraBible();
                break;
            case FLAG_LATIN:
                this.runMantraLatin();
                break;
            case FLAG_ANGELS:
                this.runMantraAngels();
                break;
        }
/*
        Map map =  System.getenv();

        System.out.println("===ENV===");
        System.out.println(map);
        for (Object o : map.keySet()) {
            System.out.println(String.valueOf(o) + ": "  + map.get(o));
        }
        System.out.println("---ENV---");
*/
        /*
        Runnable callableTask1 = () -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                    new MyCosmicMantra("/MyMantraConfiguration.properties").startMantra();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        };
        callableTasks.add(callableTask1);

        Runnable callableTask2 = () -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(2500);
                    new CosmicInvocation("/InvocazioneCristo_Configuration.properties").startMantra();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        };
        callableTasks.add(callableTask2);
        */

    }

    public static void main(String[] args) {
        try {
            MantraRunner mantras = new MantraRunner();
            mantras.startAppExecutor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startAppExecutor() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, InterruptedException {

        ExecutorService executorService =
                new ThreadPoolExecutor(callableTasks.size(), callableTasks.size(), 0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>());

        //System.out.printf("callableTasks size: %s\n", callableTasks.size());

        //List<Future<>> futures = executorService.invokeAll(callableTasks);

        for (Runnable r : callableTasks) {
            //System.out.printf("Executing callableTask: %s\n", r.toString());
            executorService.execute(r);
        }

    }

    public Object rt = null;
    private void creaIstanzaMantra(Object[] o) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Constructor c = ((Class<?>)o[MANTRA_CLASS]).getDeclaredConstructor(o[MANTRA_CONFIGURATION].getClass());
        Method m = null;
        Class objClass = ((Class<?>)o[MANTRA_CLASS]);
        if (objClass.getSuperclass().getSimpleName().contains("MyCosmicMantra")) {
            m = objClass.getSuperclass().getDeclaredMethod("startMantra");
        } else {
            m = objClass.getDeclaredMethod("startMantra");
        }

        //Method m = ((Class<?>)o[MANTRA_CLASS]).getDeclaredMethod("startMantra");
        Object objInstance = c.newInstance(o[MANTRA_CONFIGURATION]);
        //System.out.printf("This is a valid instance of: %s\n",objInstance);
        MantraRunner.this.rt = m.invoke(objInstance);
        //System.out.printf("Until when loop cycle interrupted this is the return value: %s\n",rt);

    }

}
