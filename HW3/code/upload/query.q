drop table math_h;
drop table eng_h;

CREATE EXTERNAL TABLE math_h(key string, value int)
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES ("hbase.columns.mapping" = ":key,grade:math")
TBLPROPERTIES ("hbase.table.name" = "100060007_pagerank");

CREATE EXTERNAL TABLE eng_h(key string, value int)
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES ("hbase.columns.mapping" = ":key,grade:eng")
TBLPROPERTIES ("hbase.table.name" = "100060007_invertedindex");


SELECT  y.key,y.myaverage from (select math_h.key, (math_h.value + eng_h.value)/2.0 as myaverage FROM math_h JOIN eng_h ON (math_h.key = eng_h.key)) y ORDER BY y.myaverage DESC limit 5;



SELECT  count(*) from (select math_h.key, (math_h.value + eng_h.value)/2.0 as myaverage FROM math_h JOIN eng_h ON (math_h.key = eng_h.key)) y WHERE y.myaverage < 60.0;
drop table math_h;
drop table eng_h;
