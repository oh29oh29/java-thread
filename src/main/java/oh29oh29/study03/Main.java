package oh29oh29.study03;

/**
 * 해당 패키지는 쓰레드의 'NEW' 상태를 확인하기 위해 작성되었다.
 * */
public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(new StudyThread());
        System.out.println(thread.getState());
    }
}
