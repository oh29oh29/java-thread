# Java Thread

멀티 쓰레딩은 CPU 를 최대한 활용하기 위해 애플리케이션의 두 개 이상의 부분을 동시에 실행할 수 있는 Java 기능이다.  
이러한 애플리케이션의 각 부분을 쓰레드라고 하고 프로세스 내의 경량 프로세스이다.

두가지 메커니즘을 사용하여 쓰레드를 만들 수 있다.
1. Thread 클래스 상속
2. Runnable 인터페이스 구현

## Thread 클래스 상속을 통한 쓰레드 생성

`java.lang.Thread` 클래스를 상속하는 클래스를 생성한다.  
이 클래스는 Thread 클래스에서 사용할 수 있는 run() 메서드를 재정의한다.  
쓰레드는 run() 메서드 내에서 수명을 시작한다.  
새 클래스의 객체를 만들고 start() 메서드를 호출하여 쓰레드를 시작한다. start() 메서드는 Thread 객체에서 run() 메서드를 호출한다.  

```java
public class StudyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread " + Thread.currentThread().getId() + " is running");
    }
}
```
```java
public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 8; i++) {
            StudyThread thread = new StudyThread();
            thread.start();
        }
    }
}
```
![thread 상속](images/IMG_thread_01.png)

## Runnable 인터페이스 구현을 통한 쓰레드 생성

`java.lang.Runnable` 인터페이스를 구현하고 run() 메서드를 재정의하는 새 클래스를 생성한다.  
해당 클래스를 인스턴스화하고 이 객체에 대해 start() 메서드를 호출한다.  

```java
public class StudyThread implements Runnable {
    @Override
    public void run() {
        System.out.println("Thread " + Thread.currentThread().getId() + " is running");
    }
}
```
```java
public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 8; i++) {
            Thread thread = new Thread(new StudyThread());
            thread.start();
        }
    }
}
```
![runnable 구현](images/IMG_thread_02.png)

## Thread Class vs Runnable Interface

1. Thread 클래스를 상속하면 Java 가 다중 상속을 지원하지 않으므로 다른 클래스를 상속할 수 없다.  
그러나 Runnable 인터페이스를 구현하면 다른 클래스를 상속할 수 있다.  
2. Runnable 인터페이스에서는 사용할 수 없는 yield(), interrupt() 등과 같은 내장 메서드를 사용할 수 없지만 Thread 클래스를 상속하면 이런 기본 기능을 사용할 수 있다.  
3. Runnable 인터페이스를 구현하면 여러 쓰레드간에 공유할 수 있는 객체를 생성할 수 있다.

## Lifecycle and States

![lifecycle](images/IMG_lifecycle_01.png)

#### New

생성되었지만 아직 시작되지 않은 쓰레드이다.  
start() 메서드를 사용하여 시작할때까지 이 상태를 유지한다.  

```java
public class StudyThread implements Runnable {
    @Override
    public void run() {}
}
```
```java
public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(new StudyThread());
        System.out.println(thread.getState());
    }
}
```
```text
NEW
```

#### Runnable

새로운 쓰레드를 생성하고 이 쓰레드에 대해 start() 메서드를 호출하면 New 상태에서 Runnable 상태로 넘어간다.  
이 상태의 쓰레드는 실행중이거나 실행할 준비가 되어있고 시스템에서 리소스 할당을 기다리고 있는 상태를 의미한다.

멀티쓰레드 환경에서 쓰레드 스케쥴러(JVM 의 일부)는 각 쓰레드에 고정된 시간을 할당한다.  
특정 시간동안 실행된 다음 제어를 다른 Runnable 상태의 쓰레드에 양도한다.  

```java
public class StudyThread implements Runnable {
    @Override
    public void run() {}
}
```
```java
public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(new StudyThread());
        thread.start();
        System.out.println(thread.getState());
    }
}
```
```text
RUNNABLE
```

하지만 위 예제에서 쓰레드의 상태가 항상 'RUNNABLE' 이라는것을 보장할 순 없다.  
쓰레드 스케쥴러에 의해 해당 쓰레드가 즉시 실행되어 'RUNNABLE' 상태를 출력하기 전에 실행이 끝날 수 있다.  
이러한 경우 다른 상태가 출력된다.

#### Blocked

현재 실행할 수 없는 쓰레드의 상태이다.  
Blocked 상태에 대해 이해하려면 우선 Synchronized 에 대한 이해가 필요하다.  

멀티쓰레드 환경에서 두 개 이상의 쓰레드가 변경 가능한 공유 데이터를 동시에 업데이트하려고 하면 경쟁 조건이 발생한다.  
Java 는 공유 데이터에 대한 쓰레드 접근을 동기화하여 경합 상태를 방지하는 메커니즘을 제공한다.  

Synchronized 로 표시된 로직은 동기화된 블럭이 되어 한 번에 하나의 쓰레드만 접근하여 실행할 수 있다.  
Synchronized 블럭을 수행할 때 Java 는 내부적으로 monitor lock 또는 intrinsic lock 이라고 하는 monitor 를 사용하여 동기화를 제공한다.  

모든 객체는 반드시 하나의 monitor 를 가진다. 동일한 객체의 모든 Synchronized 블럭은 동일한 시간에 하나의 쓰레드만 실행할 수 있다.  

```java
public class Worker {
    public synchronized void work() {
        Thread.getAllStackTraces()
                .keySet()
                .stream()
                .filter(it -> it.getName().equals("A") || it.getName().equals("B"))
                .forEach(it -> {
                    if (Thread.currentThread().getId() == it.getId()) {
                        System.out.println("Current Thread: " + it.getName() + ", state: " + it.getState());
                    } else {
                        System.out.println("Other Thread: " + it.getName() + ", state: " + it.getState());
                    }
                });
    }
}
```
```java
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
```
```java
public class Main {
    public static void main(String[] args) {
        Worker worker = new Worker();
        Thread thread1 = new Thread(new StudyThread(worker), "A");
        Thread thread2 = new Thread(new StudyThread(worker), "B");
        thread1.start();
        thread2.start();
    }
}
```
```text
Current Thread: A, state: RUNNABLE
Other Thread: B, state: BLOCKED
Current Thread: B, state: RUNNABLE
```

#### Waiting

쓰레드는 다른 쓰레드가 특정 작업을 수행하기를 기다릴때 'WAITING' 상태가 된다.  
Java 문서에 따르면 모든 쓰레드는 다음 세가지 메서드 중 하나를 호출하면 이 상태로 들어갈 수 있다고 한다.  

1. object.wait()
2. thread.join()
3. LockSupport.park()

```java
public class StudyAThread implements Runnable {

    @Override
    public void run() {
        Thread threadB = new Thread(new StudyBThread(), "B");
        threadB.start();

        try {
            threadB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```
```java
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
```
```java
public class Main {
    public static void main(String[] args) {
        Thread threadA = new Thread(new StudyAThread(), "A");
        threadA.start();
    }
}
```
```text
threadA state: WAITING
```

#### Timed Waiting

지정된 시간 내에 다른 쓰레드가 특정 작업을 수행할 때까지 대기할 때 TIMED_WAITING 상태가 된다.
Java 문서에 따르면 쓰레드를 TIMED_WAITING 상태에 두는 다섯가지 방법이 있다고 한다.

1. thread.sleep(long millis)
2. wait(int timeout) or wait(int timeout, int nanos)
3. thread.join(long millis)
4. LockSupport.parkNanos
5. LockSupport.parkUntil

```java
public class StudyThread implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```
```java
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread(new StudyThread(), "A");
        threadA.start();

        Thread.sleep(1000);
        System.out.println(threadA.getState());
    }
}
```
```text
TIMED_WAITING
```

#### Terminated

실행이 완료되었거나 비정상적으로 종료된 경우 TERMINATED 상태가 된다.  

```java
public class StudyAThread implements Runnable {

    @Override
    public void run() {
        // No processing in this block
    }
}
```
```java
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread(new StudyAThread(), "A");
        threadA.start();

        Thread.sleep(1000);
        System.out.println(threadA.getState());
    }
}
```
```text
TERMINATED
```

## Priority

멀티쓰레딩 환경에서 쓰레드 스케줄러는 쓰레드 우선 순위에 따라 쓰레드에 프로세서를 할당한다.  
쓰레드를 만들 때마다 항상 우선순위가 할당된다.  
우선순위는 쓰레드를 생성하는 동안 JVM 에 의해 제공되거나 프로그래머가 명시적으로 제공할 수 있다.

쓰레드에 대해 허용되는 우선순위 값은 1 에서 10 까지이다.  
우선순위를 위해 Thread 클래스에 정의된 세개의 정적 변수가 있다.  

public static int MIN_PRIORITY
- 쓰레드가 가질수 있는 최소 우선순위 1 이다.

public static int NORM_PRIORITY
- 명시적으로 정의하지 않은 경우 쓰레드의 기본 우선순위 5 이다.

public static int MAX_PRIORITY
- 쓰레드의 최대 우선순위 10 이다.

## Main 쓰레드

Java 애플리케이션이 시작되면 하나의 쓰레드가 즉시 실행된다.  
이것은 애플리케이션이 시작될 때 실행되는 쓰레드이기 때문에 일반적으로 애플리케이션의 메인 쓰레드라고 한다.

![메인쓰레드](images/IMG_mainthread_01.png)

#### How to control Main thread

메인 쓰레드는 애플리케이션이 시작될 때 자동으로 생성된다.  
메인 쓰레드를 제어하려면 우리는 메인 쓰레드에 대한 참조를 얻어야한다.  
Thread 클래스에 있는 currentThread() 메서드를 사용하여 호출된 쓰레드에 대한 참조를 얻을 수 있다.  
메인 쓰레드의 기본 우선순위는 5 이며 나머지 모든 사용자 쓰레드의 우선순위는 부모에서 자식으로 상속된다.  

#### main() 메서드와 메인 쓰레드간의 관계

각 애플리케이션의 메인 쓰레드는 JVM 에 의해 생성된다.  
메인 쓰레드는 먼저 main() 메서드의 존재를 확인한 다음 클래스를 초기화한다.  

## Synchronization

멀티 쓰레드 애플리케이션은 종종 여러 쓰레드가 동일한 리소스에 접근하려고 시도하고 결국 오류 및 예기치 않은 상황이 발생할 수 있다.  
따라서 특정 시점에 하나의 쓰레드만 리소스에 접근할 수 있도록 동기화 방법을 사용하기도 한다.  

Java 는 동기화된 블럭을 사용하여 쓰레드를 생성하고 작업을 동기화하는 방법을 제공한다.  
synchronized 키워드로 동기화된 블럭을 만들 수 있다.  
동일한 객체에서 동기화된 모든 블럭은 한 번에 하나의 쓰레드만 실행할 수 있다.  
동기화된 블럭에 접근하려는 다른 모든 쓰레드는 이미 접근중인 쓰레드가 수행이 종료될때까지 차단된다.  

#### synchronized 키워드

synchronized 키워드는 세 가지의 경우에 쓰일 수 있다.
1. Instance methods
2. Static methods
3. Code blocks

#### Synchronized Instance Methods

메서드 선언에 synchronized 키워드를 추가하기만하면 메서드가 동기화된다.
인스턴스 메서드는 메서드를 소유한 클래스의 인스턴스를 통해 동기화된다. 즉, 클래스의 인스턴스 당 하나의 쓰레드만이 메서드를 실행할 수 있다.  

```java
public synchronized void synchronisedCalculate() {
        // do something
}
```

#### Synchronized Static Methods

static 메서드는 인스턴스 메서드와 마찬가지로 동기화된다.  
이러한 메서드는 인스턴스 수에 관계없이 클래스 당 static synchronized 메서드 내에서 하나의 쓰레드만 실행할 수 있다.

```java
public static synchronized void syncStaticCalculate() {
    // do something
}
```

#### Synchronized Blocks Within Methods

때로는 전체 메서드를 동기화하지 않고 그 안에 있는 일부 명령문만 동기화를 할 수도 있다.  
이는 synchronized 를 블럭 내부에 적용하여 수행할 수 있다.  

```java
public void performSynchronisedTask() {
    synchronized (this) {
        setCount(getCount()+1);
    }
}
```

`this` 매개 변수를 동기화 된 블록에 전달한다.  `this` 는 모니터 객체이며 블럭 내부의 코드는 모니터 객체에서 동기화된다.  
간단히 말해, 모니터 객체 당 하나의 쓰레드만 해당 코드 블럭 내에서 실행할 수 있다.

## Deadlock

데드락이란 두 개 이상의 쓰레드가 다른 쓰레드가 보유한 잠금 또는 자원에 접근하려고 영원히 대기할 때 발생한다.  
결과적으로 데드락에 빠진 쓰레드는 수행될 수 없기 때문에 애플리케이션이 중단될 수 있다.  

'식사하는 철학자들 문제' 는 멀티 쓰레드 환경에서 동기화 문제를 잘 보여주며 데드락의 예로 자주 사용된다.  

#### Avoiding Deadlock

데드락은 Java 에서 일반적인 동시성 문제이다.  
따라서 잠재적인 데드락을 피하기 위해 Java 애플리케이션을 설계해야한다.  

먼저 쓰레드에 대해 여러 잠금을 획득할 필요가 없다.  
쓰레드가 다중 잠금을 필요로 하는 경우에는 잠금 획득에서 순환 종속성을 피하기 위해 각 쓰레드가 동일한 순서로 잠금을 획득하는지 확인해야한다.  

Lock 인터페이스의 tryLock() 메서드와 같은 시간 제한 잠금 시도를 사용하여 잠금을 획득할 수 없는 경우 쓰레드가 무한히 block 되지 않도록 할 수 있다.

<hr>

#### References

> 웹 문서
> - [geeksforgeeks | Multithreading in Java](https://www.geeksforgeeks.org/multithreading-in-java/)
> - [geeksforgeeks | Lifecycle and States of a Thread in Java](https://www.geeksforgeeks.org/lifecycle-and-states-of-a-thread-in-java/)
> - [geeksforgeeks | Main thread in Java](https://www.geeksforgeeks.org/main-thread-java/)
> - [geeksforgeeks | Java-Multithreading Archives](https://www.geeksforgeeks.org/tag/java-multithreading/)
> - [geeksforgeeks | Thread Pools in Java](https://www.geeksforgeeks.org/thread-pools-java/)
> - [geeksforgeeks | Java.lang.Thread class in Java](https://www.geeksforgeeks.org/java-lang-thread-class-java/)
> - [geeksforgeeks | Runnable interface in Java](https://www.geeksforgeeks.org/runnable-interface-in-java/)
> - [geeksforgeeks | Implement Runnable vs Extend Thread in Java](https://www.geeksforgeeks.org/implement-runnable-vs-extend-thread-in-java/)
> - [geeksforgeeks | Java Thread Priority in Multithreading](https://www.geeksforgeeks.org/java-thread-priority-multithreading/)
> - [geeksforgeeks | Synchronized in Java](https://www.geeksforgeeks.org/synchronized-in-java/)
> - [baeldung | Java Concurrency](https://www.baeldung.com/java-concurrency)
> - [baeldung | Life Cycle of a Thread in Java](https://www.baeldung.com/java-thread-lifecycle)
> - [baeldung | How to Start a Thread in Java](https://www.baeldung.com/java-start-thread)
> - [baeldung | Runnable vs. Callable in Java](https://www.baeldung.com/java-runnable-callable)
> - [baeldung | Implementing a Runnable vs Extending a Thread](https://www.baeldung.com/java-runnable-vs-extending-thread)
> - [baeldung | Guide to the Synchronized Keyword in Java](https://www.baeldung.com/java-synchronized)
> - [baeldung | Testing Multi-Threaded Code in Java](https://www.baeldung.com/java-testing-multithreaded)
> - [baeldung | Java Thread Deadlock and Livelock](https://www.baeldung.com/java-deadlock-livelock)
> - [ducmanhphan | How to use CompletableFuture and Callable in Java](https://ducmanhphan.github.io/2020-02-10-How-to-use-CompletableFuture-Callable-in-Java/)
