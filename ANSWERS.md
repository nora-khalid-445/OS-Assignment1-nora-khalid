## 1. Thread vs Process
A thread is a smaller unit of a process that can run independently, while a process is a complete program in execution. Threads share the same memory space, but processes have separate memory. Threads are faster to create and communicate with each other compared to processes. In this assignment, we used threads because they are more efficient for simulating CPU scheduling. Also, threads allow better performance when handling multiple tasks at the same time.

## 2. Ready Queue Behavior
In Round-Robin scheduling, if a process does not finish within its time quantum, it is moved back to the ready queue. For example, in the output, a process like P1 runs for a limited time and then is re-added to the queue if it still has remaining time. This ensures that all processes get a fair chance to execute. Re-queueing prevents any single process from taking too much CPU time. This improves fairness and responsiveness in the system.

## 3. Thread Lifecycle
A thread starts in the New state when it is created. When Thread.start() is called, it moves to the Runnable state. Then it enters the Running state when the CPU executes it. If the thread pauses using Thread.sleep(), it goes to the Waiting state. Finally, when the process finishes execution, the thread enters the Terminated state. In this assignment, each process goes through these states during the simulation.

## 4. Real-World Applications
One real-world example is a web server, where multiple users send requests at the same time. Threads handle each request efficiently using scheduling. Another example is a mobile application, where background tasks run without affecting the user interface. Round-Robin scheduling is useful because it ensures fairness and responsiveness. These concepts help systems run smoothly and efficiently.
