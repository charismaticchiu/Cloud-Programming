rm -r class/*
#javac -classpath hadoop-core-1.0.3.jar -d class part1/*
javac -classpath hadoop-core-1.0.3.jar -d class code/pagerankClean/*
jar -cvf prclean.jar -C class/ .
#hadoop dfs -rmr prgraphOutput
#hadoop jar Lab1.jar part1.WordCount wcInput wcOutput
hadoop jar prclean.jar code.pagerankClean.PageRankClean 
#hadoop dfs -cat prgraphOutput/*

