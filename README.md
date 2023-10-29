### Running the Application

To run the application, use the following command in the src folder:

```shell
dotnet run --project TimeAnalyzer.csproj -- "13:15-14:00" "15:30-16:00" "17:00-18:00"
```

To run it with a .txt folder, add an -f tag followed by the .txt file location:

```shell
dotnet run --project TimeAnalyzer.csproj -- -f "C:\Projects\timeAnalyzer\data\times.txt" "10:30-11:35" "10:15-11:15" "11:20-11:50" "10:35-11:40" "10:20-11:20"
```
