package types;

import java.time.LocalTime;

public class TimeEntry {
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
