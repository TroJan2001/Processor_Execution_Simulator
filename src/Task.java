public class Task {

    final private String TASK_ID;
    final private int CREATION_TIME;
    final private int EXECUTION_TIME;
    final private int PRIORITY;
    private int remainingTime;

    public Task(String taskID, int creationTime, int executionTime, int priority) {
        this.TASK_ID = taskID;
        this.CREATION_TIME = creationTime;
        this.EXECUTION_TIME = executionTime;
        this.remainingTime = executionTime;
        this.PRIORITY = priority;
    }

    public String getTASK_ID() {
        return TASK_ID;
    }

    public int getEXECUTION_TIME() {
        return EXECUTION_TIME;
    }

    public int getPRIORITY() {
        return PRIORITY;
    }

    public boolean isComplete() {
        return remainingTime <= 1;
    }

    public void executeOneCycle() {
        if (remainingTime > 0) {
            remainingTime--;
        }
    }

    @Override
    public String toString() {
        return "Task{" +
                "TASK_ID='" + TASK_ID + '\'' +
                ", creationTime=" + CREATION_TIME +
                ", executionTime=" + EXECUTION_TIME +
                ", priority=" + PRIORITY +
                ", remainingTime=" + remainingTime +
                '}';
    }
}
