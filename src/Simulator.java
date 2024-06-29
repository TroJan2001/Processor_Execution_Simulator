import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Simulator {
    final private Queue<Task> tasks = new PriorityQueue<>(new TaskComparator());
    final private HashMap<Integer, List<Task>> cycleTasks = new HashMap<>();
    final private int NUMBER_OF_PROCESSEORS;
    final private double SIMULATION_TIME;
    final private String TASKS_FILE_PATH;
    final private Queue<Processor> idleProcessors;
    final private ArrayList<Processor> busyProcessors;
    final private Schedular scheduler;
    private BufferedReader br;


    public Simulator(int numberOfProcessors, double simulationTime, String tasksFilePath) {
        this.NUMBER_OF_PROCESSEORS = numberOfProcessors;
        this.SIMULATION_TIME = simulationTime;
        this.TASKS_FILE_PATH = tasksFilePath;
        this.idleProcessors = new LinkedList<>();
        this.busyProcessors = new ArrayList<>();
        this.scheduler = new Schedular(tasks, idleProcessors, busyProcessors);
        try {
            readTasks();
        } catch (IOException e) {
            System.out.println("IOException while reading tasks file");
        }
    }

    public void run() {
        for (int i = 1; i <= NUMBER_OF_PROCESSEORS; i++) {
            idleProcessors.add(new Processor("P" + i));
        }
        simulate();
    }

    private void readTasks() throws IOException {
        int taskID = 1;
        int lastCreationTime = -1;
        simulatorInit();
        String line;
        System.out.println(Color.PINK + "Scheduling " + br.readLine() + " Tasks" + Color.RESET);
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" ");
            try {
                int creationTime = Integer.parseInt(parts[0]);
                int executionTime = Integer.parseInt(parts[1]);
                int priority = Integer.parseInt(parts[2]);

                // Check if creation time is not lower than the previous creation time
                if (creationTime < lastCreationTime) {
                    System.out.println("Error: Creation time must be greater than or equal to the previous creation time");
                    System.exit(1);
                }

                Task task = new Task("T" + taskID++, creationTime, executionTime, priority);
                cycleTasks.computeIfAbsent(creationTime, k -> new ArrayList<>()).add(task);
                validateParameters(creationTime, executionTime, priority);

                lastCreationTime = creationTime; // Update last creation time
            } catch (NumberFormatException e) {
                System.out.println("Parsing error: Expecting Integer");
                System.exit(1);
            }
        }
    }


    private void simulatorInit() {
        try {
            br = new BufferedReader(new FileReader(TASKS_FILE_PATH));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    private void validateParameters(int creationTime, int executionTime, int priority) {
        if (creationTime < 1) {
            throw new IllegalArgumentException("Creation time must be greater than 0");
        }
        if (executionTime < 1) {
            throw new IllegalArgumentException("Execution time must be greater than 0");
        }
        if (priority != 0 && priority != 1) {
            throw new IllegalArgumentException("Priority must be either 0 or 1");
        }
    }

    private void simulate() {
        System.out.println(Color.PINK + "Simulation started..." + Color.RESET);
        for (int cycle = Clock.getCurrentCycle(); cycle <= SIMULATION_TIME; cycle++) {
            System.out.println(Color.CYAN + " ".repeat(57) + "Cycle " + cycle);
            System.out.println("-".repeat(115) + Color.RESET);

            // Check and add new tasks for the current cycle
            if (cycleTasks.containsKey(cycle)) {
                List<Task> newTasks = cycleTasks.get(cycle);
                for (Task task : newTasks) {
                    tasks.add(task);
                    System.out.println(Color.GREEN + task.toString() + " created and added to the queue" + Color.RESET);
                }
            }

            // Schedule tasks
            scheduler.scheduleTasks();

            // Print processors status
            for (Processor busyProcessor : busyProcessors) {
                System.out.println(busyProcessor);
            }

            for (Processor idleProcessor : idleProcessors) {
                System.out.println(idleProcessor);
            }

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
}