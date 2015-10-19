package code.query;
import java.net.URI;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.io.BufferedReader;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.io.IOUtils;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.KeyValue;
public class Query{
	//static Configuration conf = new Configuration();
	private static Configuration conf = null;
        static Configuration hdfsconf = new Configuration();
        // You can lookup usage of these api from this website. =)
        // http://hbase.apache.org/0.94/apidocs/

        static {
                conf = HBaseConfiguration.create();
        }	
	public static String getRecord(String tableName, String rowKey) throws Exception {
                HTable table = new HTable(conf, tableName);

                Get get = new Get(rowKey.getBytes());
                Result rs = table.get(get);
		StringBuffer returnVal= new StringBuffer();
                for (KeyValue kv : rs.raw()) {
//                        System.out.print(new String(kv.getRow()) + " ");
  //                      System.out.print(new String(kv.getFamily()) + ":");
    //                    System.out.print(new String(kv.getQualifier()) + " ");
      //                  System.out.print(kv.getTimestamp() + " ");
        //                System.out.println(new String(kv.getValue()));
			returnVal.append(new String(kv.getValue()));
                }
		return returnVal.toString();
        }

	public static void main(String[] args)throws Exception{
		while(true){
			System.out.print("\nPlease enter keywords: ");
        	        Scanner scanner = new Scanner(System.in);
	        	String keyword = scanner.next();
			String fileString = getRecord("100060007_invertedindex",keyword);
			//System.out.println("raw string: "+fileString);
			if(fileString.length() == 0 ) {
				System.out.println("What you want does not exist!!!");
				continue;
			}
			int count = 0;
			String[] fileList = fileString.split("]");
			double[] scores = new double[fileList.length];
			for(int i = 0; i < fileList.length; i++){
				String score = getRecord("100060007_pagerank",fileList[i]);
				if (score.length() ==0) continue;//if the link stored in 100060007_pagerank table is chaotically-encoded then skip
				//System.out.print(score+"| "+ fileList[i]);
				scores[i] = Double.parseDouble(score);
				
			}
			//Sort the result
			for(int i = 0; i < scores.length; i ++){
				for(int j = 0; j < scores.length -1; j++){
					if (scores[j] < scores[j+1]){
                                                double tempdouble = scores[j+1];
                                                scores[j+1] = scores[j];
                                                scores[j] = tempdouble;
                                                String tempstr = fileList[j+1];
                                                fileList[j+1] = fileList[j];
                                                fileList[j] = tempstr;
                                        }
				}	
			}
			System.out.println("Your keyword is: "+ keyword + "\n\nResult:\n");
			for(int i =0; i < scores.length; i++){
				System.out.println("Rank:"+ (i+1) );
				System.out.println("\tLink: " + fileList[i] + " Score: "+Double.toString(scores[i]));
				System.out.println("==================");
//				System.out.println
//				System.out.println
			}
		}
	}
}
