package code;


import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import java.lang.String;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class InvertedIndexReducer extends MapReduceBase
        implements Reducer<Text, Text, Text, Text> {
	Text result = new Text();
        public void reduce(Text key, Iterator<Text> values,
                OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
                int df = 0;
		boolean first = true;
		StringBuffer fileList = new StringBuffer();
		while(values.hasNext()){
			df += 1;
			fileList.append(String.format("%s;",values.next()));
		}	
		result.set(fileList.toString());
		String out = key.toString() + ":" + Integer.toString(df);
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


