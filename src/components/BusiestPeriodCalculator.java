package components;

import types.BusiestPeriod;
import types.TimeEntry;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import java.time.LocalTime;

public class BusiestPeriodCalculator {
    public static BusiestPeriod calculateBusiestPeriod(List<TimeEntry> timeEntries) {
        int maxDrivers = 0;
        LocalTime busiestStartTime = LocalTime.MIN;
        LocalTime busiestEndTime = LocalTime.MIN;

        // Use a HashMap for faster access and insertions
        Map<LocalTime, Integer> breakTimeStatistics = new HashMap<>();

        for (TimeEntry timeEntry : timeEntries) {
            LocalTime startTime = timeEntry.getStartTime();
            LocalTime endTime = timeEntry.getEndTime();

            // Optimize by checking only within the time range of interest
            for (LocalTime time = startTime; !time.isAfter(endTime); time = time.plusMinutes(1)) {
                int driversAtTime = breakTimeStatistics.getOrDefault(time, 0) + 1;
                breakTimeStatistics.put(time, driversAtTime);

                if (driversAtTime > maxDrivers) {
                    maxDrivers = driversAtTime;
                    busiestStartTime = time;
                    busiestEndTime = endTime;
                }
            }
        }

        return new BusiestPeriod(busiestStartTime, busiestEndTime, maxDrivers);
    }

}
