#hadoop dfs -rmr wcOutput
hadoop dfs -rmr retrievalOutput
#hadoop jar Lab1.jar part1.WordCount wcInput wcOutput
hadoop jar HW2_retrieval.jar retrieval.ReadFile indexOutput retrievalOutput 
#hadoop dfs -cat wcOutput/*
hadoop dfs -cat retrievalOutput/*
