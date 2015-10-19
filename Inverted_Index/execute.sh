#hadoop dfs -rmr wcOutput
hadoop dfs -rmr indexOutput
#hadoop jar Lab1.jar part1.WordCount wcInput wcOutput
hadoop jar HW2.jar code.InvertedIndex indexInput indexOutput 
#hadoop dfs -cat wcOutput/*
hadoop dfs -cat indexOutput/*
