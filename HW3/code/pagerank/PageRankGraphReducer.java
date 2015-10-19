package code.pagerank;


import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import java.lang.String;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.Counters.Counter;
public class PageRankGraphReducer extends MapReduceBase
        implements Reducer<Text, Text, Text, Text> {
        Text result = new Text();
        public void reduce(Text key, Iterator<Text> values,
                OutputCollector<Text, Text> output, Reporter reporter) throws IOException {

                double iniRatio = 0.;
                boolean first = true;
                StringBuffer fileList = new StringBuffer();
                while(values.hasNext()){                        
                        fileList.append(values.next().toString());
                }
		
                result.set(fileList.toString());
		//Counter ctr = reporter.getCounter(NUM_TITLE);
		//iniRatio = 1./ctr.getCounter();
                String out = key.toString() + "@" + Double.toString(1.);
                output.collect( new Text(out),result);
                /*StringBuilder toReturn = new StringBuilder();
                
                while (values.hasNext()) {
                        if (!first)
                                toReturn.append(", ");
                        first=false;
                        toReturn.append(values.next().toString());
                }

                output.collect(key, new Text(toReturn.toString()));*/
        }

}
