class Processor {
    final private String PID;
    private Task currentTask;

    public Processor(String pid) {
        this.PID = pid;
        this.currentTask = null;
    }

    public String getPid() {
        return PID;
    }

    public void assignTask(Task task) {
        this.currentTask = task;
    }

    public void completeTask() {
        if (currentTask != null) {
            System.out.println("Task " + currentTask.getTASK_ID() + " completed on Processor " + this.PID);
            this.currentTask = null;
        }
    }

    public boolean executeTask() {
        if (currentTask != null) {
            currentTask.executeOneCycle();
            if (currentTask.isComplete()) {
                completeTask();
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() {
        return "Processor{" +
                "PID='" + PID + '\'' +
                ", currentTask=" + (currentTask != null ? currentTask.getTASK_ID() : "Idle") +
                '}';
    }
}
