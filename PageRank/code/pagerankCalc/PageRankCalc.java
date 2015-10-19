package code.pagerankCalc;
import java.io.IOException;
import java.io.*;
//import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.*;
//import org.apache.hadoop.fs.Path;
import java.util.Scanner;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;

public class PageRankCalc {
    public static void main(String[] args) throws Exception {
        PageRankCalc pr = new PageRankCalc();
        pr.run(args[0], args[1]);
    }

    public void run(String inputPath, String outputPath) throws IOException {
        /*                                                                                                                                                                 
         * You can lookup usage of these api from this website. =)                                                                                                         
         * http://tool.oschina.net/uploads/apidocs/hadoop/index.html?overview-summary.html                                                                                 
         */
	
            Path pt=new Path("hdfs://10.1.114.6:8100/user/100060007/prrankInput/info.txt");
            FileSystem fs = FileSystem.get(new Configuration());
            BufferedReader br=new BufferedReader(new InputStreamReader(fs.open(pt)));
            String line;
            line=br.readLine();
        //    Scanner sc = new Scanner(line);
          //  sc.next();
            double dangling = Double.parseDouble(line);//)sc.nextDouble();
            line=br.readLine();
           // Scanner sc2 = new Scanner(line);
            //sc2.next();
            double total = Double.parseDouble(line);//sc2.nextDouble();
            Path file = new Path("hdfs://10.1.114.6:8100/user/100060007/sumDangle1.txt");
            BufferedWriter br2=new BufferedWriter(new OutputStreamWriter(fs.create(file,true)));
            System.out.println(Double.toString(dangling/total));
            br2.write(Double.toString(dangling/total));
        br2.close();
	br.close();
        JobConf conf = new JobConf(PageRankCalc.class);
        

        conf.setMapperClass(PageRankCalcMapper.class);
        conf.setReducerClass(PageRankCalcReducer.class);
	conf.setCombinerClass(PageRankCalcCombiner.class);
        /*                                                                                                                                                                 
         * If your mapper output key-value pair is different from final                                                                                                    
         * output key-value pair, please remember to setup these two APIs.                                                                                                 
         */
        conf.setMapOutputKeyClass(Text.class);
        conf.setMapOutputValueClass(Text.class);

        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(Text.class);
	conf.setNumMapTasks(32);
        conf.setNumReduceTasks(1);
//        conf.setCombinerClass(InvertedIndexCombiner.class);

//      conf.setOutputKeyComparatorClass(InvertedIndexKeyComparator.class);
        // TODO setup your key comparator class                                                                                                                            
  //      conf.setPartitionerClass(InvertedIndexPartitioner.class);
        // TODO setup your partitioner class                       
//      conf.setOutputValueGroupingComparator(InvertedIndexGroupComparator.class);
        // TODO setup your key group comparator class                                                                                                                      
	conf.setJobName("PR iteration ");
	FileInputFormat.addInputPath(conf, new Path(inputPath));
        FileOutputFormat.setOutputPath(conf, new Path(outputPath));
       	JobClient.runJob(conf);
    }
}

