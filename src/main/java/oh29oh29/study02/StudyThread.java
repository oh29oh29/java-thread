package oh29oh29.study02;

public class StudyThread implements Runnable {
    @Override
    public void run() {
        System.out.println("Thread " + Thread.currentThread().getId() + " is running");
    }
}
