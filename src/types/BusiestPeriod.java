package types;

import java.time.LocalTime;

public class BusiestPeriod {
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
