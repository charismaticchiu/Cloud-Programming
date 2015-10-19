rm -r class/*
javac -classpath hadoop-core-1.0.3.jar -d class code/pureScore/*
jar -cvf pure.jar -C class/ .
hadoop dfs -rmr 
hadoop jar pure.jar code.pureScore.PureScore prrankInput/input2 pureScore/iter2
hadoop jar pure.jar code.pureScore.PureScore prrankInput/input3 pureScore/iter3
hadoop jar pure.jar code.pureScore.PureScore prrankInput/input4 pureScore/iter4
hadoop jar pure.jar code.pureScore.PureScore prrankInput/input5 pureScore/iter5

hadoop jar pure.jar code.pureScore.PureScore prrankInput/input6 pureScore/iter6
hadoop jar pure.jar code.pureScore.PureScore prrankInput/input7 pureScore/iter7
hadoop jar pure.jar code.pureScore.PureScore prrankInput/input8 pureScore/iter8
hadoop jar pure.jar code.pureScore.PureScore prrankInput/input9 pureScore/iter9
hadoop jar pure.jar code.pureScore.PureScore prrankInput/input10 pureScore/iter10

hadoop jar pure.jar code.pureScore.PureScore prrankInput/input11 pureScore/iter11

