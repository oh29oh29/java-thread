package oh29oh29.study05;

public class StudyThread implements Runnable {
    @Override
    public void run() {
        commonResource();
    }

    public static synchronized void commonResource() {
        while (true) {}
    }
}
