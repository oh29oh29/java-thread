package oh29oh29.study01;

public class StudyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread " + Thread.currentThread().getId() + " is running");
    }
}
