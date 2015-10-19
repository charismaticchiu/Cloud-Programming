package code.pagerankGraph;


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
		int countHash = 0, countUnder = 0, countLn = 0;
                StringBuffer fileList = new StringBuffer();
                while(values.hasNext()){                        
			String value = values.next().toString();
			if(value.charAt(0) == '_'){
				countUnder++;
			}
			else if(value.compareTo("#")==0){
				countHash++;
			}
			else{
				countLn++;
				fileList.append( value+ "]");
				/*if(first){
					first = false;
                        		fileList.append(value);
				}else fileList.append("]"+value);*/
			}
                }
		if(countUnder > 0){//dead-end
			String out = key.toString() + "]" + Double.toString(1.);
			output.collect( new Text(out), new Text(""));
		}
		else if(countLn == 0){//non-exist
			String out = key.toString() + "]" + Double.toString(1.);
			output.collect(new Text(out), new Text("_"));
		}
		else{
                	result.set(fileList.toString());
                	String out = key.toString() + "]" + Double.toString(1.);
                	output.collect( new Text(out),result);
		}
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
