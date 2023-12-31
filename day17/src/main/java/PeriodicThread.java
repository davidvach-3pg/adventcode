import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;

public abstract class PeriodicThread extends Thread {
    private static final Logger logger = Logger.getLogger("periodicthread");

    private final long desired_period_ms;
    private final double skew_ms;
    private volatile boolean stopped = false;
    private Object wake_obj;

    public PeriodicThread(long desired_period_ms) {
        this(desired_period_ms, 0.0);
    }

    public PeriodicThread(long desired_period_ms, double skew_ms) {
        wake_obj = new Object();
        this.desired_period_ms = desired_period_ms;
        this.skew_ms = skew_ms;

    }

    public void run() {
        Random rnd = new Random();

        while (!stopped) {
            long start = System.currentTimeMillis();
            long skew = Math.round(skew_ms * rnd.nextGaussian());

            long end_time = start + Math.max(0, desired_period_ms + skew);

            long start_time = System.nanoTime();
            try {
                runPass();
            } catch (Throwable t) {
                logger.log(Level.WARNING, "Periodic thread exception", t);
            }
            synchronized (wake_obj) {
                wake_obj.notifyAll();
            }

            long tm = System.currentTimeMillis();
            long sleep_tm = end_time - tm;

            if (sleep_tm > 0) {
                try {
                    synchronized (wake_obj) {
                        wake_obj.wait(sleep_tm);
                    }
                } catch (InterruptedException e) {
                    logger.log(Level.WARNING, "Periodic thread exception: " + e);
                }
            }
        }
    }


    /**
     * Wake this task and get it to execute if it is sleeping.
     * If it is already running there there no guarantee of an execution starting after the wake() call.
     */
    public void wake() {
        synchronized (wake_obj) {
            wake_obj.notifyAll();
        }
    }

    public void wakeAndWait()
            throws InterruptedException {
        synchronized (wake_obj) {
            wake_obj.notifyAll();
            wake_obj.wait();
        }

    }

    public void halt() {
        stopped = true;
    }

    public abstract void runPass() throws Exception;


}