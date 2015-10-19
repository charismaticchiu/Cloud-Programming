package code.invertedindex;

import java.util.*;


import java.io.IOException;
import java.util.Iterator;
import java.util.Arrays;
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
			//df += 1;
			String value = values.next().toString();
			if(value.compareTo(" ")!=0 && value.compareTo("")!=0)
				fileList.append(value + "]");
		}
		String[] arr = fileList.toString().split("]");	
		Set<String> stringSet = new HashSet<String>();
                for (String element : arr) {
			if(element.compareTo(" ")!=0 && element.compareTo("")!=0)
	                     stringSet.add(element);
                }
                //Set to Array
                String[] str = stringSet.toArray(new String[stringSet.size()]);
		StringBuffer fileNames= new StringBuffer();
		for(int i = 0; i < str.length; i++){
                        fileNames.append(str[i] + "]" );
                }	
	
		result.set(fileNames.toString());
		
		output.collect( key , result);
    	}

}


