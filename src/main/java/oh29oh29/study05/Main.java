package oh29oh29.study05;

/**
 * 해당 패키지는 쓰레드의 'BLOCKED' 상태를 확인하기 위해 작성되었다.
 * */
public class Main {
    public static void main(String[] args) {
        Worker worker = new Worker();
        Thread thread1 = new Thread(new StudyThread(worker), "A");
        Thread thread2 = new Thread(new StudyThread(worker), "B");
        thread1.start();
        thread2.start();
    }
}
