DROP TABLE IF EXISTS 100060007_math;
DROP TABLE IF EXISTS 100060007_eng;
DROP TABLE IF EXISTS 100060007_avg;
CREATE EXTERNAL TABLE 100060007_math(rowKey STRING , name STRING, score INT) STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler' WITH SERDEPROPERTIES ('hbase.columns.mapping' = 'grade:name, grade:math') TBLPROPERTIES ('hbase.table.name' = '100060007_math');

CREATE EXTERNAL TABLE 100060007_eng(rowKey STRING , name STRING, score INT) STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler' WITH SERDEPROPERTIES ('hbase.columns.mapping' = 'grade:name, grade:eng') TBLPROPERTIES ('hbase.table.name' = '100060007_eng');


CREATE TABLE 100060007_avg(Key STRING, math_score INT, eng_score INT, avg INT);

INSERT INTO 100060007_avg(Key, math_score, eng_score, avg) SELECT (100060007_math.name, 100060007_math.score, 100060007_eng.score, (100060007_math.score+ 100060007_eng.score)/2) FROM 100060007_math  JOIN 100060007_eng ON (100060007_math.name = 100060007)eng.name);

DROP TABLE IF EXISTS 100060007_math;
DROP TABLE IF EXISTS 100060007_eng;
DROP TABLE IF EXISTS 100060007_avg;
