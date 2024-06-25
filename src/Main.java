public class Main {
    public static void main(String[] args) {

        if (args.length != 3) {
            System.out.println("Usage: java Main <number_of_processors> <total_clock_cycles> <tasks_file_path>");
            return;
        }

        int numberOfProcessors = Integer.parseInt(args[0]);
        int totalClockCycles = Integer.parseInt(args[1]);
        String tasksFilePath = args[2];

        Simulator simulator = new Simulator(numberOfProcessors, totalClockCycles, tasksFilePath);
        simulator.simulate();
    }
}