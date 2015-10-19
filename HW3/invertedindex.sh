rm -r class/*
#javac -classpath hadoop-core-1.0.3.jar -d class part1/*
javac -classpath hadoop-core-1.0.3.jar -d class code/invertedindex/*
jar -cvf invertedindex.jar -C class/ .

hadoop dfs -rmr indexOutput
#hadoop jar Lab1.jar part1.WordCount wcInput wcOutput
hadoop jar invertedindex.jar code.invertedindex.InvertedIndex /opt/Assignment3/Input/100M/ indexOutput
#hadoop dfs -cat wcOutput/*
#hadoop dfs -cat indexOutput/*
