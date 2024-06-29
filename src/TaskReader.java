import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class TaskReader {
    private final String tasksFilePath;
    private BufferedReader br;

    public TaskReader(String tasksFilePath) {
        this.tasksFilePath = tasksFilePath;
        initReader();
    }

    private void initReader() {
        try {
            br = new BufferedReader(new FileReader(tasksFilePath));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public HashMap<Integer, List<Task>> readTasks() throws IOException {
        HashMap<Integer, List<Task>> cycleTasks = new HashMap<>();
        int taskID = 1;
        int lastCreationTime = -1;
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
                throw new IllegalArgumentException("Parsing error: Expecting Integer");
            }
        }
        return cycleTasks;
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
}
