rm -r class/*
javac -classpath hadoop-core-1.0.3.jar:/opt/hbass/lib/*:/opt/hbase/hbase-0.94.27.jar -d class/ code/query/Query.java
jar -cvf query.jar -C class/ .
/opt/jhadoop/bin/hadoop jar query.jar code.query.Query

