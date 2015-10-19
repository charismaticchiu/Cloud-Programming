rm -r class/*
javac -classpath hadoop-core-1.0.3.jar -d class code/pagerankSort/*
jar -cvf prsort.jar -C class/ .
hadoop dfs -rmr prsortOutput
hadoop jar prsort.jar code.pagerankSort.PageRankSort prrankInput/input2 sorted/iter2
hadoop jar prsort.jar code.pagerankSort.PageRankSort prrankInput/input6 sorted/iter6
hadoop jar prsort.jar code.pagerankSort.PageRankSort prrankInput/input11 sorted/iter11
#'hadoop dfs -cat prsortOutput/*'

