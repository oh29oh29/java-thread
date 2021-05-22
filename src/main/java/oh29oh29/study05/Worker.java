package oh29oh29.study05;

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
