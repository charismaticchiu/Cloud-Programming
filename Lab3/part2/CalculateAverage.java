package part2;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.DoubleWritable;

import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;

public class CalculateAverage {
	public static void main(String[] args) throws Exception {
		CalculateAverage aCalculateAverage = new CalculateAverage();
		aCalculateAverage.run(args[0], args[1]); 
	}
	
	public void run(String inputPath, String outputPath) throws IOException {
		/* 
		 * You can lookup usage of these api from this website. =)
		 * http://tool.oschina.net/uploads/apidocs/hadoop/index.html?overview-summary.html
		 */
		 
		JobConf conf = new JobConf(CalculateAverage.class);
		conf.setJobName(“Calc Averg”);
		
		conf.setMapperClass(CalculateAverageMapper.class);
		conf.setReducerClass(CalculateAverageReducer.class);
		// TODO setup your combiner class
		conf.setCombinerClass(CalculateAverageCombiner.class);

		/*
		 * If your mapper output key-value pair is different from final 
		 * output key-value pair, please remember to setup these two APIs.
		 */ 
		conf.setMapOutputKeyClass(Text.class);
		// TODO setup your custom value class
        	conf.setMapOutputValueClass(SumCountPair.class);
		
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(DoubleWritable.class);
		
		FileInputFormat.addInputPath(conf, new Path(inputPath));
		FileOutputFormat.setOutputPath(conf, new Path(outputPath));

		conf.setNumMapTasks(3);
		conf.setNumReduceTasks(2);

		JobClient.runJob(conf);
	}
}
