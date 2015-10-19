package code;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.StringTokenizer;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;

import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
public class InvertedIndexMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {

	Text keyInfo = new Text();
        Text valueInfo = new Text();
        public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException{

			FileSplit fileSplit = (FileSplit)reporter.getInputSplit();
                	String fileName = fileSplit.getPath().getName();
                	//location.set(fileName);
                        String delimiter = " ,?[]()':.|!--;$&\t";
                        String line = value.toString();
                        StringTokenizer tokenizer = new StringTokenizer(line, delimiter);

                        while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				Pattern pattern = Pattern.compile( token );
				Matcher m = pattern.matcher(line);
				StringBuffer offset = new StringBuffer();
				while(m.find()){
					offset.append(":" + Integer.toString( (int) (long) key.get() + m.start() ) );
				}
                                keyInfo.set(String.format("%s:%s", token ,fileName));
				//System.out.println(offset.toString());
				valueInfo.set( "1" + offset.toString());
                                output.collect(keyInfo, valueInfo);
                        }
        }
	
}
