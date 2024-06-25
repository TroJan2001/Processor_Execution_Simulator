import java.util.List;
import java.util.Queue;

class Schedular {
    private final Queue<Task> tasks;
    private final List<Processor> processors;

    public Schedular(Queue<Task> tasks, List<Processor> processors) {
        this.tasks = tasks;
        this.processors = processors;
    }

    public void scheduleTasks() {
        for (Processor processor : processors) {
            if (!processor.isBusy() && !tasks.isEmpty()) {
                Task task = tasks.poll();
                if (task != null) {
                    processor.assignTask(task);
                    System.out.println("Task " + task.getTASK_ID() + " assigned to " + processor.getPid());
                }
            }
        }
    }
}
