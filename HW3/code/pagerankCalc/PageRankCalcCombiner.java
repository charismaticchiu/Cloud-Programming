package code.pagerankCalc;

import java.io.*;
//import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;;
import org.apache.hadoop.fs.*;
//import org.apache.hadoop.io.Text;
//import org.apache.hadoop.io.IntWritable;
import java.lang.String;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.Counters.Counter;
public class PageRankCalcCombiner extends MapReduceBase
        implements Reducer<Text, Text, Text, Text> {
        Text result = new Text();
        public void reduce(Text key, Iterator<Text> values,
                OutputCollector<Text, Text> output, Reporter reporter) throws IOException {

                double sum = 0.,alpha = 0.15;
                String nameList = null;
		boolean isDDDDD = false;
		boolean noName = true,isDangle = false;
		if(key.toString().length() == 5 && key.toString().charAt(4) == 'D'){ 
			isDDDDD = true;
		//	System.out.println("isDDDDD");
		}	
		
                while(values.hasNext()){

                        String value = values.next().toString();
			if(value.length() == 0) isDangle = true;
                        
			else if(value.charAt(value.length() -1 ) == ']'){
                                nameList = value;
				isDangle = false;
			}
			else {
                                sum += Double.parseDouble(value);
                        }

                }
                // if dangleing node DDDDD      
                //if( noName  ){//dangling node or DDDDD
		if(isDDDDD){ //
			output.collect(key, new Text(Double.toString(sum)));
		}
                else if ( nameList == null){   //dangling node
			output.collect(key, new Text(""));
			output.collect(key, new Text(Double.toString(sum)));
		}
		else {//normal node
			output.collect(key, new Text(Double.toString(sum)));
	                output.collect(key, new Text(nameList));
		}
        }

}

