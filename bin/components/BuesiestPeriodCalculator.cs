using Types;

namespace Components
{
    public class BusiestPeriodCalculator
    {
        public static BusiestPeriod CalculateBusiestPeriod(List<TimeEntry> timeEntries)
        {
            DateTime busiestStartTime = DateTime.MinValue;
            DateTime busiestEndTime = DateTime.MinValue;
            int maxDrivers = 0;

            Dictionary<DateTime, int> breakTimeStatistics = new();

            foreach (TimeEntry timeEntry in timeEntries)
            {
                DateTime startTime = timeEntry.StartTime;
                DateTime endTime = timeEntry.EndTime;

                for (DateTime time = startTime; time <= endTime; time = time.AddMinutes(1))
                {
                    if (!breakTimeStatistics.ContainsKey(time))
                    {
                        breakTimeStatistics[time] = 0;
                    }

                    breakTimeStatistics[time]++;

                    if (breakTimeStatistics[time] > maxDrivers)
                    {
                        maxDrivers = breakTimeStatistics[time];
                        busiestStartTime = time;
                        busiestEndTime = endTime;
                    }
                }
            }

            return new BusiestPeriod(busiestStartTime, busiestEndTime, maxDrivers);
        }
    }
}
