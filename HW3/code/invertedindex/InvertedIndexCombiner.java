package code.invertedindex;
import java.io.IOException;
import java.util.*;

import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.DoubleWritable;
import java.util.Arrays;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class InvertedIndexCombiner extends MapReduceBase
        implements Reducer<Text, Text, Text, Text> {

        Text info = new Text();

        public void reduce(Text key, Iterator<Text> values,
                OutputCollector<Text, Text> output, Reporter reporter) throws IOException {


                int sum = 0;
                int count = 0;
                //StringBuffer offsets = new StringBuffer();
		List<String> offsets = new ArrayList<String>();
                while(values.hasNext()){

                        Text value = values.next();
                        String[] count_offsets = value.toString().split(":");
                        sum += 1;//Integer.valueOf(count_offsets[0]);
                        for(int i = 1 ; i < count_offsets.length;i++){
                                offsets.add(count_offsets[i]);
                        }
                }
		//to be sorted elements
                String[] arr = new String[offsets.size()];
		//List to array
		offsets.toArray(arr);
		// eliminate duplicate
                Set<String> stringSet = new HashSet<String>();
                for (String element : arr) {
                     stringSet.add(element);
                }
		//Set to Array
                String[] str = stringSet.toArray(new String[stringSet.size()]);
		int[] sortednum = new int[stringSet.size()];
		for(int i =0; i < stringSet.size(); i++){
			sortednum[i] = Integer.parseInt(str[i]);
		}
//		offsets.toArray(sorted);		
                Arrays.sort(sortednum);
		//transform to String to output
		StringBuffer finalOffset = new StringBuffer();
		for(int i = 0; i < sortednum.length; i++){
			finalOffset.append(":" + Integer.toString(sortednum[i]));
		}
                String[] items = key.toString().split(";");
		//items[1] is the file name; items[0] is the word itself.
		if (items.length >= 2){// make sure the key is of correct format, which contains word:fileName
                	info.set(String.format("%s:%d%s",items[1],sum,finalOffset.toString()));
	                key.set(items[0]);
        	        output.collect(key,info);
		}
		else System.out.println("No File Name!!!");
        }
}

