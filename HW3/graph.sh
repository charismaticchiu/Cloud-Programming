rm -r class/*
#javac -classpath hadoop-core-1.0.3.jar -d class part1/*
javac -classpath hadoop-core-1.0.3.jar -d class code/pagerankGraph/*
jar -cvf prgraph.jar -C class/ .
hadoop dfs -rmr prgraphOutput
#hadoop jar Lab1.jar part1.WordCount wcInput wcOutput
hadoop jar prgraph.jar code.pagerankGraph.PageRankGraph /opt/Assignment3/Input/100M/ prgraphOutput


