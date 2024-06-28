class Clock {
    final private static int CYCLE_TIME = 1000;
    private static int currentCycle = CYCLE_TIME;

    private Clock() {
    }

    public static void tick() {
        currentCycle += currentCycle;
    }

    public static int getCycleTime() {
        return CYCLE_TIME;
    }
}
