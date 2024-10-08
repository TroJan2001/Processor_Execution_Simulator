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

    private void completeTask() {
        if (currentTask != null) {
            System.out.println(Color.RED + currentTask.getTASK_ID() + " completed on Processor " + this.PID + Color.RESET);
            this.currentTask = null;
        }
    }


    @Override
    public String toString() {
        return Color.PURPLE + PID + " {" +
                "currentTask=" + (currentTask != null ? currentTask.getTASK_ID() : "Idle") +
                '}' + Color.RESET;
    }
}
