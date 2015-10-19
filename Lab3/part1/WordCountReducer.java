package part1;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import java.lang.String;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class WordCountReducer extends MapReduceBase
	implements Reducer<Text, IntWritable, Text, IntWritable> {
		
	public void reduce(Text key, Iterator<IntWritable> values,
		OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {	
		int sum = 0;
		while (values.hasNext()) {
			IntWritable value = values.next();
			sum = sum + value.get();
		}
			
		// TODO : You have to modify something for output result
		String outKey = key.toString().charAt(0) + " : ";
		output.collect(new Text (outKey), new IntWritable(sum));
	}
}
