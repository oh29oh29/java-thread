package oh29oh29.study06;

public class StudyBThread implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread threadA = Thread.getAllStackTraces()
                .keySet()
                .stream()
                .filter(it -> it.getName().equals("A"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("A thread 가 존재하지 않습니다."));

        System.out.println("threadA state: " + threadA.getState());
    }
}
