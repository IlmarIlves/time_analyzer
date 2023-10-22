import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class App {
    public static void main(String[] args) throws IOException {
        List<TimeEntry> timeEntries = new ArrayList<>();

        // Process time entries from command-line arguments
        for (String arg : args) {
            if (!arg.equals("-f")) {
                TimeEntry timeEntry = parseTimeEntry(arg);
                if (timeEntry != null) {
                    timeEntries.add(timeEntry);
                }
            }
        }

        // Check if a file path is provided in the command line
        String filePath = getFilePath(args);
        System.out.println(filePath);
        if (filePath != null) {
            // Parse time entries from the text file specified in the command line
            List<TimeEntry> fileTimeEntries = parseTimeEntriesFromFile(filePath);
            System.out.println(fileTimeEntries);
            timeEntries.addAll(fileTimeEntries);
        }

        // Calculate the busiest time period
        BusiestPeriod busiestPeriod = calculateBusiestPeriod(timeEntries);

        // Display the result
        System.out
                .println("Busiest Time Period: " + busiestPeriod.getStartTime() + " to " + busiestPeriod.getEndTime());
        System.out.println("Number of Drivers: " + busiestPeriod.getNumberOfDrivers());
    }

    private static String getFilePath(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-f")) {
                // Check if the next argument exists
                if (i + 1 < args.length) {
                    return args[i + 1];
                }
            }
        }
        return null; // File path not found in arguments
    }

    private static TimeEntry parseTimeEntry(String input) {
        String[] parts = input.split("-");
        if (parts.length == 2) {
            try {
                LocalTime startTime = LocalTime.parse(parts[0]);
                LocalTime endTime = LocalTime.parse(parts[1]);
                return new TimeEntry(startTime, endTime);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    private static List<TimeEntry> parseTimeEntriesFromFile(String filePath) throws IOException {
        List<TimeEntry> timeEntries = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (String line : lines) {
            TimeEntry timeEntry = parseTimeEntry(line);
            if (timeEntry != null) {
                timeEntries.add(timeEntry);
            }
        }
        return timeEntries;
    }

    private static BusiestPeriod calculateBusiestPeriod(List<TimeEntry> timeEntries) {
        Map<LocalTime, Integer> breakTimeStatistics = new TreeMap<>();
        int maxDrivers = 0;
        LocalTime busiestStartTime = LocalTime.MIN;
        LocalTime busiestEndTime = LocalTime.MIN;

        for (TimeEntry timeEntry : timeEntries) {
            LocalTime startTime = timeEntry.getStartTime();
            LocalTime endTime = timeEntry.getEndTime();

            for (LocalTime time = startTime; !time.isAfter(endTime); time = time.plusMinutes(1)) {
                int driversAtTime = breakTimeStatistics.getOrDefault(time, 0) + 1;
                breakTimeStatistics.put(time, driversAtTime);

                if (driversAtTime > maxDrivers) {
                    maxDrivers = driversAtTime;
                    busiestStartTime = time;
                    busiestEndTime = time.plusMinutes(1);
                }
            }
        }

        return new BusiestPeriod(busiestStartTime, busiestEndTime, maxDrivers);
    }

    static class TimeEntry {
        private LocalTime startTime;
        private LocalTime endTime;

        public TimeEntry(LocalTime startTime, LocalTime endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public LocalTime getStartTime() {
            return startTime;
        }

        public LocalTime getEndTime() {
            return endTime;
        }
    }

    static class BusiestPeriod {
        private LocalTime startTime;
        private LocalTime endTime;
        private int numberOfDrivers;

        public BusiestPeriod(LocalTime startTime, LocalTime endTime, int numberOfDrivers) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.numberOfDrivers = numberOfDrivers;
        }

        public LocalTime getStartTime() {
            return startTime;
        }

        public LocalTime getEndTime() {
            return endTime;
        }

        public int getNumberOfDrivers() {
            return numberOfDrivers;
        }
    }
}
