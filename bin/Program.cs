using Components;
using Types;


namespace TimeAnalyzer
{
    public class App
    {
        public static void Main(string[] args)
        {
            List<TimeEntry> timeEntries = ExtractTimeEntries(args);

            string? filePath = GetFilePath(args);
            Console.WriteLine(filePath);

            if (filePath != null)
            {
                List<TimeEntry> fileTimeEntries = ParseTimeEntriesFromFile(filePath);
                timeEntries.AddRange(fileTimeEntries);
            }


            if (timeEntries.Count == 0)
            {
                Console.WriteLine("No valid time entries found. Exiting.");
                return;
            }

            BusiestPeriod busiestPeriod = BusiestPeriodCalculator.CalculateBusiestPeriod(timeEntries);

            Console.WriteLine("Busiest Time Period: " + busiestPeriod.StartTime + " to " + busiestPeriod.EndTime);
            Console.WriteLine("Number of Drivers: " + busiestPeriod.NumberOfDrivers);
        }

        private static List<TimeEntry> ExtractTimeEntries(string[] args)
        {
            return args.Where(arg => arg != "-f").Select(ParseTimeEntry).Where(entry => entry != null).Select(entry => entry!).ToList();
        }

        private static string? GetFilePath(string[] args)
        {
            int index = Array.IndexOf(args, "-f");
            return index >= 0 && index + 1 < args.Length ? args[index + 1] : null;
        }

        private static TimeEntry? ParseTimeEntry(string input)
        {
            string[] parts = input.Split('-');
            if (parts.Length == 2 && DateTime.TryParse(parts[0], out DateTime startTime) && DateTime.TryParse(parts[1], out DateTime endTime))
            {
                return new TimeEntry(startTime, endTime);
            }
            return null;
        }

        private static List<TimeEntry> ParseTimeEntriesFromFile(string filePath)
        {
            try
            {
                return File.ReadAllLines(filePath)
                           .Select(ParseTimeEntry)
                           .Where(entry => entry != null)
                           .Select(entry => entry!)
                           .ToList();
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error occurred while parsing file: {ex.Message}");
                return new List<TimeEntry>();
            }
        }
    }
}
