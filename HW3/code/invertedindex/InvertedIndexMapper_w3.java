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
  

public class InvertedIndexMapper_w3 extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {

	Text keyInfo = new Text();
        Text valueInfo = new Text();
        public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException{

			//FileSplit fileSplit = (FileSplit)reporter.getInputSplit();
                	//String fileName = fileSplit.getPath().getName();
                	//location.set(fileName);
			InputStream in = new ByteArrayInputStream(value.toString().getBytes("UTF-8"));//get the page and convert it into InputSteam
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			try{
				//Using factory get an instance of document builder
				DocumentBuilder db = dbf.newDocumentBuilder();
				//parse using builder to get DOM representation of the XML file
				Document dom = db.parse( in );
				Element ele = dom.getDocumentElement();//get the root element, which is <page>
				String fileName = null;
			
				NodeList nl = ele.getElementsByTagName("title");
        	                if(nl != null && nl.getLength() > 0) {
                	                Element el = (Element)nl.item(0);
                        	        fileName = el.getFirstChild().getNodeValue();
	                        }
				System.out.println(fileName);
				String textVal = null;
				nl = ele.getElementsByTagName("text");
				if(nl != null && nl.getLength() > 0) {
					Element el = (Element)nl.item(0);
					textVal = el.getFirstChild().getNodeValue();
				}

				String delimiter = " ,?{}[]()':.|\\==\"<>_^+**~@!--;$&\t";
                        
	                        StringTokenizer tokenizer = new StringTokenizer(textVal, delimiter);

	                        while (tokenizer.hasMoreTokens()) {
					String token = tokenizer.nextToken();
					if (token.compareTo(" ")!=0){
						Pattern pattern = Pattern.compile( token );
						Matcher m = pattern.matcher(textVal);
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
			}catch(Exception e){
				e.printStackTrace();
			}
        }
	
}
