package code.invertedindex;


import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;

import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;

public class InvertedIndex {
    public static void main(String[] args) throws Exception {
	InvertedIndex ii = new InvertedIndex();
	ii.run(args[0], args[1]);
    }

    public void run(String inputPath, String outputPath) throws IOException {
	/*                                                                                                                                                                 
	 * You can lookup usage of these api from this website. =)                                                                                                         
	 * http://tool.oschina.net/uploads/apidocs/hadoop/index.html?overview-summary.html                                                                                 
	 */

	JobConf conf = new JobConf(InvertedIndex.class);
	conf.setJobName("inverted index");

	conf.setMapperClass(InvertedIndexMapper.class);
	conf.setReducerClass(InvertedIndexReducer.class);

	/*                                                                                                                                                                 
	 * If your mapper output key-value pair is different from final                                                                                                    
	 * output key-value pair, please remember to setup these two APIs.                                                                                                 
	 */
	conf.setMapOutputKeyClass(Text.class);
	conf.setMapOutputValueClass(Text.class);

	conf.setOutputKeyClass(Text.class);
	conf.setOutputValueClass(Text.class);

//	conf.setCombinerClass(InvertedIndexCombiner.class);

//	conf.setOutputKeyComparatorClass(InvertedIndexKeyComparator.class);
	// TODO setup your key comparator class                                                                                                                            
//	conf.setPartitionerClass(InvertedIndexPartitioner.class);
	// TODO setup your partitioner class                       
//	conf.setOutputValueGroupingComparator(InvertedIndexGroupComparator.class);
	// TODO setup your key group comparator class                                                                                                                      

	FileInputFormat.addInputPath(conf, new Path(inputPath));
	FileOutputFormat.setOutputPath(conf, new Path(outputPath));

	conf.setNumMapTasks(24);
	conf.setNumReduceTasks(1);

	JobClient.runJob(conf);
    }
}
