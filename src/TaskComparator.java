import java.util.Comparator;
import java.util.Random;

public class TaskComparator implements Comparator<Task> {
    final private Random random = new Random();

    public int compare(Task o1, Task o2) {
        int priorityDiff = o2.getPRIORITY() - o1.getPRIORITY();
        if (priorityDiff != 0) {
            return priorityDiff;
        }

        int executionTimeDiff = o2.getEXECUTION_TIME() - o1.getEXECUTION_TIME();
        if (executionTimeDiff != 0) {
            return executionTimeDiff;
        }

        return random.nextInt(2) * 2 - 1;
    }
}
