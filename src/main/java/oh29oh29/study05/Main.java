package oh29oh29.study05;

/**
 * 해당 패키지는 쓰레드의 'BLOCKED' 상태를 확인하기 위해 작성되었다.
 * */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new StudyThread());
        Thread thread2 = new Thread(new StudyThread());
        thread1.start();
        thread2.start();

        Thread.sleep(1000);

        System.out.println(thread2.getState());
        System.exit(0);
    }
}
