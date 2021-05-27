package oh29oh29.study08;

/**
 * 해당 패키지는 쓰레드의 'TERMINATED' 상태를 확인하기 위해 작성되었다.
 * */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread(new StudyAThread(), "A");
        threadA.start();

        Thread.sleep(1000);
        System.out.println(threadA.getState());
    }
}
