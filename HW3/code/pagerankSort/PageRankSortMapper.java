package code.pagerankSort;

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
  

public class PageRankSortMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
//	static enum PrCounter { NUM_TITLE };
        Text keyInfo = new Text();
        Text valueInfo = new Text();
	double alpha = 0.15;
        public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException{
			
		String[] title_links = value.toString().split("\t");
		String[] title_score = title_links[0].split("]");
		String[] links = null;
		String title = title_score[0];
		//if(key.toString().length() == 5 && key.toString().charAt(4)=='D')
		double score = Double.parseDouble(title_score[1]);
		if(title_links.length > 1){
			output.collect(new Text(title_links[0]), new Text(title_links[1]) );
		}
		else{ 
			output.collect(new Text(title_links[0]),new Text("") );
		}
        }

}

