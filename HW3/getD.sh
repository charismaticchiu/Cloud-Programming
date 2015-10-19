rm -r class/*
javac -classpath hadoop-core-1.0.3.jar -d class code/getD/*
jar -cvf getd.jar -C class/ .
hadoop jar getd.jar code.getD.GetD prrankInput/input3
#hadoop dfs -cat prgraphOutput/*

