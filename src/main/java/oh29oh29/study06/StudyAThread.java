package oh29oh29.study06;

public class StudyAThread implements Runnable {

    @Override
    public void run() {
        Thread threadB = new Thread(new StudyBThread());
        threadB.setName("B");
        threadB.start();

        try {
            threadB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
