public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java Main <number_of_processors> <total_clock_cycles> <tasks_file_path>");
            return;
        }

        int numberOfProcessors;
        int totalClockCycles;

        try {
            numberOfProcessors = Integer.parseInt(args[0]);
            totalClockCycles = Integer.parseInt(args[1]);

            if (numberOfProcessors <= 0 || totalClockCycles <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Number of processors and total clock cycles must be integers greater than 0.");
            return;
        }

        // Handle this in when reading the file
        String tasksFilePath = args[2];

        Simulator simulator = new Simulator(numberOfProcessors, totalClockCycles, tasksFilePath);
        simulator.run();
    }
}
