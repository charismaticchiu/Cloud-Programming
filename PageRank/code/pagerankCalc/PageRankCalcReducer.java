package code.pagerankCalc;

import java.io.*;
//import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
//import org.apache.hadoop.io.Text;
//import org.apache.hadoop.io.IntWritable;
import java.lang.String;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.Counters.Counter;
public class PageRankCalcReducer extends MapReduceBase
        implements Reducer<Text, Text, Text, Text> {
        Text result = new Text();
        public void reduce(Text key, Iterator<Text> values,
                OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
                        
			Path pt = new Path("hdfs://10.1.114.6:8100/user/100060007/prrankInput/info.txt");
                        FileSystem fs = FileSystem.get(new Configuration());
                        BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(pt)));
                        String line;
                        line = br.readLine();
			double sumDangle = Double.parseDouble(line);
			line = br.readLine();
			double numLine = Double.parseDouble(line);
			br.close();
			//String[] D_danglesum = line.split("\t");
			//double dangleSum = Double.parseDouble(D_danglesum[1]);
			double rank = 0.,alpha = 0.15;
			String nameList = null;
			boolean isDangle = false;
			while(values.hasNext()){
			
				String value = values.next().toString();
				if(value.length() == 0) isDangle = true;
				else if (value.charAt(value.length() -1 ) == ']')
					nameList = value;
				else {
                                        rank += Double.parseDouble(value);	
				}
			}
			//int outDegree = nameList.split("]").length;
			// if DDDDD	
			if(key.toString().length() == 5 && key.toString().charAt(4)=='D' ){
				String out = key.toString() + "]"+ Double.toString(rank);
				output.collect(new Text(out), new Text(""));
				//output.collect(key, new Text(Double.toString(rank)));
			}
			else if(  nameList == null){//dangling node
				rank = alpha + (1-alpha)*rank + (1.-alpha)*sumDangle/numLine;//some issue here
				String out = key.toString() + "]"+ Double.toString(rank);
				output.collect(new Text(out), new Text(""));
			}
			else{
				rank = alpha + (1-alpha)*rank + (1.-alpha)*sumDangle/numLine;
				String out = key.toString() + "]"+ Double.toString(rank);
				output.collect(new Text(out), new Text(nameList));
			}
        }
}
