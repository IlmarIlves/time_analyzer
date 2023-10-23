import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import components.BusiestPeriodCalculator;
import types.BusiestPeriod;
import types.TimeEntry;

public class App {
    public static void main(String[] args) throws IOException {
        List<TimeEntry> timeEntries = new ArrayList<>();

        for (String arg : args) {
            if (!arg.equals("-f")) {
                TimeEntry timeEntry = parseTimeEntry(arg);
                if (timeEntry != null) {
                    timeEntries.add(timeEntry);
                }
            }
        }

        String filePath = getFilePath(args);
        if (filePath != null) {
            List<TimeEntry> fileTimeEntries = parseTimeEntriesFromFile(filePath);
            timeEntries.addAll(fileTimeEntries);
        }

        BusiestPeriod busiestPeriod = BusiestPeriodCalculator.calculateBusiestPeriod(timeEntries);

        System.out
                .println("Busiest Time Period: " + busiestPeriod.getStartTime() + " to " + busiestPeriod.getEndTime());
        System.out.println("Number of Drivers: " + busiestPeriod.getNumberOfDrivers());
    }

    private static String getFilePath(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-f")) {
                if (i + 1 < args.length) {
                    return args[i + 1];
                }
            }
        }
        return null;
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
}
