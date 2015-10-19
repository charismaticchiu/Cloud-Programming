package code.invertedindex;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.StringTokenizer;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
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
  

public class InvertedIndexMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {

	Text keyInfo = new Text();
        Text valueInfo = new Text();
        public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException{

		String fileName = null;
		Pattern titlePtn = Pattern.compile("<title>(.+?)</title>");
		Matcher matchTitle = titlePtn.matcher(value.toString());
		matchTitle.find();
		fileName = matchTitle.group(1);
		System.out.println(fileName);
		
		String textVal = null;
		String delimiter = " ,/?{}[]()%':.|\\==\"<>_^+**~@!--;#$&\t";
                Pattern textPtn = Pattern.compile("<text xml:space=\"preserve\">(.+?)</text>");
		Matcher matchText = textPtn.matcher(value.toString());
		matchText.find();
		textVal = matchText.group(1);//group1 => the text in two parentheses, ()
//		int textStartIndex = matchText.start(1);// the index of the first character in tag <text>
		StringTokenizer tokenizer = new StringTokenizer(textVal, delimiter);
                if(fileName != null && textVal != null){
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				if (token.compareTo(" ")!=0 && token.compareTo("")!=0){
					Pattern pattern = Pattern.compile( token );
					Matcher m = pattern.matcher(textVal);
					StringBuffer offset = new StringBuffer();
          			        output.collect(new Text(token), new Text(fileName));
				}	
        	       	}
		}
       }
	
}
