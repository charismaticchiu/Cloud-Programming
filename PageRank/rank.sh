rm -r class/*
#javac -classpath hadoop-core-1.0.3.jar -d class part1/*
javac -classpath hadoop-core-1.0.3.jar -d class code/pagerankCalc/*
jar -cvf prcalc.jar -C class/ .
rm -r class/*
javac -classpath hadoop-core-1.0.3.jar -d class code/getD/*
jar -cvf getd.jar -C class/ .

hadoop dfs -rmr prrankOutput
hadoop jar prcalc.jar code.pagerankCalc.PageRankCalc prrankInput/input1 prrankOutput
hadoop dfs -cp prrankOutput/part-00000 prrankInput/input2
hadoop jar getd.jar code.getD.GetD prrankInput/input2
hadoop dfs -rmr prrankOutput
hadoop jar prcalc.jar code.pagerankCalc.PageRankCalc prrankInput/input2 prrankOutput
hadoop dfs -cp prrankOutput/part-00000 prrankInput/input3
hadoop jar getd.jar code.getD.GetD prrankInput/input3
hadoop dfs -rmr prrankOutput
hadoop jar prcalc.jar code.pagerankCalc.PageRankCalc prrankInput/input3 prrankOutput
hadoop dfs -cp prrankOutput/part-00000 prrankInput/input4
hadoop jar getd.jar code.getD.GetD prrankInput/input4
hadoop dfs -rmr prrankOutput
hadoop jar prcalc.jar code.pagerankCalc.PageRankCalc prrankInput/input4 prrankOutput
hadoop dfs -cp prrankOutput/part-00000 prrankInput/input5
hadoop jar getd.jar code.getD.GetD prrankInput/input5
hadoop dfs -rmr prrankOutput
hadoop jar prcalc.jar code.pagerankCalc.PageRankCalc prrankInput/input5 prrankOutput
hadoop dfs -cp prrankOutput/part-00000 prrankInput/input6
hadoop jar getd.jar code.getD.GetD prrankInput/input6
hadoop dfs -rmr prrankOutput
hadoop jar prcalc.jar code.pagerankCalc.PageRankCalc prrankInput/input6 prrankOutput
hadoop dfs -cp prrankOutput/part-00000 prrankInput/input7
hadoop jar getd.jar code.getD.GetD prrankInput/input7
hadoop dfs -rmr prrankOutput
hadoop jar prcalc.jar code.pagerankCalc.PageRankCalc prrankInput/input7 prrankOutput
hadoop dfs -cp prrankOutput/part-00000 prrankInput/input8
hadoop jar getd.jar code.getD.GetD prrankInput/input8
hadoop dfs -rmr prrankOutput
hadoop jar prcalc.jar code.pagerankCalc.PageRankCalc prrankInput/input8 prrankOutput
hadoop dfs -cp prrankOutput/part-00000 prrankInput/input9
hadoop jar getd.jar code.getD.GetD prrankInput/input9
hadoop dfs -rmr prrankOutput
hadoop jar prcalc.jar code.pagerankCalc.PageRankCalc prrankInput/input9 prrankOutput
hadoop dfs -cp prrankOutput/part-00000 prrankInput/input10
hadoop jar getd.jar code.getD.GetD prrankInput/input10
hadoop dfs -rmr prrankOutput
hadoop jar prcalc.jar code.pagerankCalc.PageRankCalc prrankInput/input10 prrankOutput
hadoop dfs -cp prrankOutput/part-00000 prrankInput/input11
#hadoop dfs -cat prrankOutput/part-00000'
