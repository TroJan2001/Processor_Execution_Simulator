class Clock {
    final private static int CYCLE_TIME = 1000;
    private static int currentCycle = 0;

    private Clock() {
    }

    public static void tick() {
        currentCycle += currentCycle;
    }

    public static int getCurrentCycle() {
        return currentCycle;
    }

    public static int getCycleTime() {
        return CYCLE_TIME;
    }
}