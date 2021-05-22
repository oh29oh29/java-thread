package oh29oh29.study05;

public class StudyThread implements Runnable {

    private final Worker worker;

    public StudyThread(Worker worker) {
        this.worker = worker;
    }

    @Override
    public void run() {
        worker.work();
    }
}
