namespace Types
{
    public class BusiestPeriod
    {
        private readonly DateTime startTime;
        private readonly DateTime endTime;
        private readonly int numberOfDrivers;

        public BusiestPeriod(DateTime startTime, DateTime endTime, int numberOfDrivers)
        {
            this.startTime = startTime;
            this.endTime = endTime;
            this.numberOfDrivers = numberOfDrivers;
        }

        public DateTime StartTime => startTime;

        public DateTime EndTime => endTime;

        public int NumberOfDrivers => numberOfDrivers;

        public TimeSpan Duration => endTime - startTime;
    }
}
