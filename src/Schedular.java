import java.util.ArrayList;
import java.util.Queue;

class Schedular {
    private final Queue<Task> tasks;
    private final Queue<Processor> idleProcessors;
    private final ArrayList<Processor> busyProcessors;

    public Schedular(Queue<Task> tasks, Queue<Processor> idleProcessors, ArrayList<Processor> busyProcessors) {
        this.tasks = tasks;
        this.idleProcessors = idleProcessors;
        this.busyProcessors = busyProcessors;
    }

    public void scheduleTasks() {
        while (!tasks.isEmpty() && !idleProcessors.isEmpty()) {
            Task task = tasks.poll();
            if (task != null) {
                Processor idleProcessor = idleProcessors.poll();
                if (idleProcessor != null) {
                    idleProcessor.assignTask(task);
                    busyProcessors.add(idleProcessor);
                    System.out.println(Color.YELLOW + "Task " + task.getTASK_ID() + " assigned to " + idleProcessor.getPid() + Color.RESET);
                }
            }
        }
    }

    public void executeTasks() {
        for (Processor busyProcessor : busyProcessors) {
            if ((busyProcessor.executeTask())) {
                idleProcessors.add(busyProcessor);
            }
        }
        busyProcessors.removeAll(idleProcessors);
    }
}