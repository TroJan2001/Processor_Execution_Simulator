import java.io.IOException;
import java.util.*;

class Simulator {
    final private Queue<Task> tasks = new PriorityQueue<>(new TaskComparator());
    final private HashMap<Integer, List<Task>> cycleTasks;
    final private int SIMULATION_TIME;
    final private Queue<Processor> idleProcessors;
    final private ArrayList<Processor> busyProcessors;
    final private Schedular scheduler;

    public Simulator(int numberOfProcessors, int simulationTime, String tasksFilePath) {
        this.SIMULATION_TIME = simulationTime;
        this.idleProcessors = new LinkedList<>();
        this.busyProcessors = new ArrayList<>();
        this.scheduler = new Schedular(tasks, idleProcessors, busyProcessors);
        TaskReader taskReader = new TaskReader(tasksFilePath);
        try {
            this.cycleTasks = taskReader.readTasks();
        } catch (IOException e) {
            System.out.println("IOException while reading tasks file");
            throw new RuntimeException(e);
        }
        for (int i = 1; i <= numberOfProcessors; i++) {
            idleProcessors.add(new Processor("P" + i));
        }
    }

    public void run() {
        simulate();
    }

    private void simulate() {
        System.out.println(Color.PINK + "Simulation started..." + Color.RESET);
        for (int cycle = Clock.getCurrentCycle(); cycle <= SIMULATION_TIME; cycle++) {
            System.out.println(Color.CYAN + " ".repeat(57) + "Cycle " + cycle);
            System.out.println("-".repeat(115) + Color.RESET);

            // Check and add new tasks for the current cycle
            addTasksForCurrentCycle(cycle);
            // Schedule tasks
            scheduler.scheduleTasks();
            printProcessorStatus();
            scheduler.executeTasks();

            try {
                Thread.sleep(Clock.getCycleTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Clock.tick();
            System.out.println(Color.CYAN + "-".repeat(115) + Color.RESET);
        }
        System.out.println(Color.PINK + "Simulation ended." + Color.RESET);
    }


    private void addTasksForCurrentCycle(int cycle) {
        if (cycleTasks.containsKey(cycle)) {
            for (Task task : cycleTasks.get(cycle)) {
                tasks.add(task);
                System.out.println(Color.GREEN + task.toString() + " created and added to the queue" + Color.RESET);
            }
        }
    }

    private void printProcessorStatus() {
        for (Processor busyProcessor : busyProcessors) {
            System.out.println(busyProcessor);
        }
        for (Processor idleProcessor : idleProcessors) {
            System.out.println(idleProcessor);
        }
    }
}

