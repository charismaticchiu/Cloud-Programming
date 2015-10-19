package code.pagerankGraph;

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
//input:  
//output: 

public class PageRankGraphMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
	static enum PrCounter { NUM_TITLE };
        Text keyInfo = new Text();
        Text valueInfo = new Text();
        public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException{
		
			//Get Title
                Pattern titlePtn = Pattern.compile("<title>(.+?)</title>");
		Matcher matchTitle = titlePtn.matcher(value.toString());
		matchTitle.find();
		String title = matchTitle.group(1);
		//Get Text
		String textVal = null;
	        String delimiter = " ,?{}#/[]()%':.|\\==\"<>_^+**~@!--;$&\t";
        	Pattern textPtn = Pattern.compile("<text xml:space=\"preserve\">(.+?)</text>");
                Matcher matchText = textPtn.matcher(value.toString());
	        matchText.find();
        	textVal = matchText.group(1);//group1 => the text in two parentheses, ()
		//Get links in Text
		int start = 0; //count the number of links
		while(textVal.indexOf("[[",start)>=0){//if not dangling noel
			start = textVal.indexOf("[[",start);
			int end = textVal.indexOf("]]",start);
			if(end < 0) break;
			//System.out.println(start + ",\t" + end);
			output.collect(new Text(title), new Text( textVal.substring(start+2,end) ));
			output.collect(new Text(textVal.substring(start+2,end)),new Text("#"));//emit (link,#)		
			start = end+2;
		}
		if (start == 0)//dangling node
		{
			output.collect(new Text(title), new Text("_") );			
		}
		reporter.incrCounter(PrCounter.NUM_TITLE,1);
        }

}

