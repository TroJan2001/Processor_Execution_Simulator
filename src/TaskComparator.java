import java.util.Comparator;
import java.util.Random;

public class TaskComparator implements Comparator<Task> {
    private final Random random = new Random();

    public int compare(Task o1, Task o2) {
        if (o1.getPRIORITY() < o2.getPRIORITY())
            return 1;
        else if (o1.getPRIORITY() > o2.getPRIORITY()) {
            return -1;
        } else if (o1.getEXECUTION_TIME() < o2.getEXECUTION_TIME()) {
            return 1;
        } else if (o1.getEXECUTION_TIME() > o2.getEXECUTION_TIME()) {
            return -1;
        } else
            return random.nextInt(2) * 2 - 1;
    }
}
