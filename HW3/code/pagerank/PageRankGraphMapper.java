package code.pagerank;

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
		Pattern linkPtn = Pattern.compile("\\[\\[");
		Matcher matchLkn = linkPtn.matcher(textVal);
		StringBuffer linksBuf = new StringBuffer();
		while(matchLkn.find()){
			int offsetStart = matchLkn.end();
			//int offsetEnd = offsetStart;
			// can use indexOf
			Pattern endBrkPtn = Pattern.compile("\\]");
			Matcher endBrk = endBrkPtn.matcher(textVal);
			endBrk.find(offsetStart);
			int offsetEnd = endBrk.start();
/*			while(textVal.charAt(offsetEnd)!=']' )
			{
				if(textVal.charAt(offsetEnd+1)!=']');
					offsetEnd += 1;
			}*/
			linksBuf.append( textVal.substring(offsetStart,offsetEnd) + "@");
			output.collect(new Text(textVal.substring(offsetStart,offsetEnd)),new Text("#"));					
		}

		//String[] links = linksBuf.toString().split(";");
		valueInfo.set(linksBuf.toString());
		keyInfo.set(String.format("%s", title));
		output.collect(keyInfo,valueInfo);
		reporter.incrCounter(PrCounter.NUM_TITLE,1);
        }

}

