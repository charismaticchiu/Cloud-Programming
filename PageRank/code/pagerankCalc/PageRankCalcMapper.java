package code.pagerankCalc;

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
import javax.xml.parsers.DocumentBuilder;  
import javax.xml.parsers.DocumentBuilderFactory;  
  
import org.w3c.dom.Document;  
import org.w3c.dom.Element;  
import org.w3c.dom.Node;  
import org.w3c.dom.NodeList;  
  

public class PageRankCalcMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
//	static enum PrCounter { NUM_TITLE };
        public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException{
		String[] title_links = value.toString().split("\t");//title]score  link]link]
		String[] title_score = title_links[0].split("]");//title score
		String title = title_score[0];//title
		double score = Double.parseDouble(title_score[1]);//score
		if(title_links.length > 1 ){//enter if normal
			String[] links = title_links[1].split("]");
			for(int i =0; i < links.length; i++){
				double outputValue = score/links.length;//  PR/outDegree
				output.collect(new Text(links[i]), new Text(Double.toString(outputValue)) );
			}
			output.collect(new Text(title), new Text(title_links[1]));
		}
		else{//Dangling node
			if(title.length() !=5 || title.charAt(4) != 'D'){// filter out DDDDD for DDDDD is also a dangling node but its value cannot be accumulated
				output.collect(new Text("DDDDD"),new Text(Double.toString(score)));
				output.collect(new Text(title), new Text("") );
			}
		}
        }

}

