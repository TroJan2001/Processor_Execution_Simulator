import java.io.*;
import java.util.*;

class Simulator {
    final private Queue<Task> tasks = new PriorityQueue<>(new TaskComparator());
    final private HashMap<Integer,List<Task>> cycleTasks = new HashMap<>();
    final private int NUMBER_OF_PROCESSEORS;
    final private double SIMULATION_TIME;
    final private String TASKS_FILE_PATH;
    final private List<Processor> processors;
    final private Schedular scheduler;
    private BufferedReader br;

    public Simulator(int numberOfProcessors, double simulationTime, String tasksFilePath) {
        this.NUMBER_OF_PROCESSEORS = numberOfProcessors;
        this.SIMULATION_TIME = simulationTime;
        this.TASKS_FILE_PATH = tasksFilePath;
        this.processors = new ArrayList<>();
        this.scheduler = new Schedular(tasks, processors);
        try {
            readTasks();
        } catch (IOException e) {
            System.out.println("IOException while reading tasks file");
        }
    }

    public void simulate() {
        for (int i = 1; i <= NUMBER_OF_PROCESSEORS; i++) {
            processors.add(new Processor("P" + i));
        }
        run();
    }

    public void readTasks() throws IOException {
        int taskID = 1;
        simulatorInit();
        String line;
        System.out.println("Scheduling " + br.readLine() + " Tasks");
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" ");
            int creationTime = Integer.parseInt(parts[0]);
            int executionTime = Integer.parseInt(parts[1]);
            int priority = Integer.parseInt(parts[2]);
            Task task = new Task("T" + taskID++, creationTime, executionTime, priority);
            cycleTasks.computeIfAbsent(creationTime, k -> new ArrayList<>()).add(task);
        }
    }

    public void simulatorInit() {
        try {
            br = new BufferedReader(new FileReader(TASKS_FILE_PATH));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public void run() {
        System.out.println("Simulation started...");
        for (int cycle = 0; cycle <= SIMULATION_TIME; cycle++) {
            System.out.println("Cycle " + cycle);

            // Check and add new tasks for the current cycle
            if (cycleTasks.containsKey(cycle)) {
                List<Task> newTasks = cycleTasks.get(cycle);
                for (Task task : newTasks) {
                    tasks.add(task);
                    System.out.println("Task " + task.getTASK_ID() + " created and added to the queue");
                }
            }


            // Schedule tasks
            scheduler.scheduleTasks();

            // Print processors status
            for (Processor processor : processors) {
                System.out.println(processor);
            }

            // Execute tasks on processors
            for (Processor processor : processors) {
                processor.executeTask();
            }

            try {
                Thread.sleep(Clock.getCycleTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Clock.tick();
        }

        System.out.println("Simulation ended.");
    }
}
