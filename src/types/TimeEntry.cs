namespace Types
{
    public class TimeEntry
    {
        public DateTime StartTime { get; }
        public DateTime EndTime { get; }

        public TimeEntry(DateTime startTime, DateTime endTime)
        {
            StartTime = startTime;
            EndTime = endTime;
        }
    }
}
