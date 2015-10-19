package code.pagerankSort;


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
public class PageRankSortReducer extends MapReduceBase
        implements Reducer<Text, Text, Text, Text> {
        Text result = new Text();
        public void reduce(Text key, Iterator<Text> values,
                OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
		
		double rank = 0.,alpha = 0.15;
		String nameList = null;
		
		String[] title_score = key.toString().split("]");
		output.collect( new Text(title_score[0]), new Text(title_score[1]));
        }

}
