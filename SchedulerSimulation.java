import java.util.LinkedList;
import java.util.Queue;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

// ANSI Color Codes
class Colors {
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLO = "\u001B[33m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String BLUE = "\u001B[34m";
    public static final String RED = "\u001B[31m";
    public static final String BG_BLUE = "\u001B[44m";
    public static final String BG_GREEN = "\u001B[42m";
    public static final String WHITE = "\u001B[37m";
    public static final String BRIGHT_WHITE = "\u001B[97m";
    public static final String BRIGHT_CYAN = "\u001B[96m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
}

// Process Class
class Process implements Runnable {
    private String name;
    private int burstTime;
    private int timeQuantum;
    private int remainingTime;

    private int priority;
    private long creationTime;
    private long waitingTime = 0;

    public Process(String name, int burstTime, int timeQuantum) {
        this.name = name;
        this.burstTime = burstTime;
        this.timeQuantum = timeQuantum;
        this.remainingTime = burstTime;

        this.priority = (int)(Math.random() * 5) + 1;
        this.creationTime = System.currentTimeMillis();
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        waitingTime += (startTime - creationTime);

        int runTime = Math.min(timeQuantum, remainingTime);

        System.out.println(name + " is running for " + runTime + " ms");

        try {
            Thread.sleep(runTime);
        } catch (InterruptedException e) {
            System.out.println(name + " interrupted");
        }

        remainingTime -= runTime;

        if (remainingTime > 0) {
            System.out.println(name + " not finished, remaining: " + remainingTime);
        } else {
            System.out.println(name + " finished");
        }
    }

    public void runToCompletion() {
        try {
            Thread.sleep(remainingTime);
            remainingTime = 0;
            System.out.println(name + " finished completely");
        } catch (InterruptedException e) {
            System.out.println(name + " interrupted");
        }
    }

    public String getName() {
        return name;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public boolean isFinished() {
        return remainingTime <= 0;
    }

    public int getPriority() {
        return priority;
    }

    public long getWaitingTime() {
        return waitingTime;
    }
}

// Main Class
public class SchedulerSimulation {

    static int contextSwitches = 0;

    public static void main(String[] args) {

        int studentID = 445052045;

        Random random = new Random(studentID);

        int timeQuantum = 2000 + random.nextInt(4) * 1000;
        int numProcesses = 10 + random.nextInt(11);

        Queue<Thread> processQueue = new LinkedList<>();
        Map<Thread, Process> processMap = new HashMap<>();

        for (int i = 1; i <= numProcesses; i++) {
            int burstTime = timeQuantum/2 + random.nextInt(2 * timeQuantum + 1);
            Process process = new Process("P" + i, burstTime, timeQuantum);
            addProcessToQueue(process, processQueue, processMap);
        }

        while (!processQueue.isEmpty()) {

            Thread currentThread = processQueue.poll();

            contextSwitches++;

            currentThread.start();

            try {
                currentThread.join();
            } catch (InterruptedException e) {
                System.out.println("Error");
            }

            Process process = processMap.get(currentThread);

            if (!process.isFinished()) {
                if (!processQueue.isEmpty()) {
                    addProcessToQueue(process, processQueue, processMap);
                } else {
                    process.runToCompletion();
                }
            }
        }

        System.out.println("Total context switches: " + contextSwitches);

        System.out.println("\nWaiting Time Summary:");
        for (Process p : processMap.values()) {
            System.out.println(p.getName() + " Waiting Time: " + p.getWaitingTime());
        }
    }

    public static void addProcessToQueue(Process process, Queue<Thread> processQueue,
                                         Map<Thread, Process> processMap) {

        Thread thread = new Thread(process);

        processQueue.add(thread);
        processMap.put(thread, process);

        System.out.println(process.getName() + " (Priority: " + process.getPriority() + ") added to queue");
    }
}
