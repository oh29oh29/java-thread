package oh29oh29.study06;

/**
 * 해당 패키지는 쓰레드의 'WAITING' 상태를 확인하기 위해 작성되었다.
 * */
public class Main {
    public static void main(String[] args) {
        Thread threadA = new Thread(new StudyAThread());
        threadA.setName("A");
        threadA.start();
    }
}
