package oh29oh29.study02;

/**
 * 해당 패키지는 java.lang.Runnable 인터페이스 구현을 통한 쓰레드 생성을 확인하기 위해 작성되었다.
 * */
public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 8; i++) {
            Thread thread = new Thread(new StudyThread());
            thread.start();
        }
    }
}
