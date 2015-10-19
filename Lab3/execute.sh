#hadoop dfs -rmr wcOutput
hadoop dfs -rmr caOutput
#hadoop jar Lab1.jar part1.WordCount wcInput wcOutput
hadoop jar Lab1.jar part2.CalculateAverage caInput caOutput 
#hadoop dfs -cat wcOutput/*
hadoop dfs -cat caOutput/*
