package code.getD;
import java.net.URI;
import java.io.*;
//import java.io.InputStreamReader;
import java.net.URI;
//import java.io.BufferedReader;
//import java.io.IOException;
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
public class GetD{
	
	static Configuration conf = new Configuration();
        public static String findD(String hdfsPath) throws IOException {
                InputStream in = null;
                FileSystem hdfs = FileSystem.get(URI.create(hdfsPath), conf);
                BufferedReader buff = null;
                Path listFiles = new Path(hdfsPath);
                FileStatus stats[] = hdfs.listStatus(listFiles);
                StringBuffer docContent = new StringBuffer();
                for (int i = 0; i < stats.length; i++) {
                        System.out.println("Directory:" + stats[i].getPath().toString());
                        Path p = new Path(stats[i].getPath().toString());
                        in = hdfs.open(p);
                        buff = new BufferedReader(new InputStreamReader(in));
                        String str = null;
                        //StringBuffer docContent = new StringBuffer();
                        while ((str = buff.readLine()) != null) {
				String[] title_list = str.split("\t");
				String[] title_score= title_list[0].split("]");
				String title = title_score[0];
                                if(title.length() ==5 && title.charAt(4)=='D')
					docContent.append(str);

                        }
                        buff.close();
                        in.close();
                        //return docContent.toString();
                }
                return docContent.toString();
        }


	public static void main(String[] args)throws Exception{
		//get new DDDDD: the sum of all dangling links
		String uri = "hdfs://10.1.114.6:8100/user/100060007/" + args[0];
		System.out.println(uri);
		String D5 = findD(uri);
		String[] title_list = D5.split("\t");
		String[] title_score= title_list[0].split("]");
		String sc = title_score[1];
		FileSystem fs = FileSystem.get(conf);
		//read the total page number
		Path pt=new Path("hdfs://10.1.114.6:8100/user/100060007/prrankInput/info.txt");
            	BufferedReader br=new BufferedReader(new InputStreamReader(fs.open(pt)));
            	String line;
            	line=br.readLine();//the first one is the old DDDDD
		String pageNum = br.readLine();//this one is the total page numbers
		br.close();
		//Overwrite the info.txt
		Path file = new Path("hdfs://10.1.114.6:8100/user/100060007/prrankInput/info.txt");
            	BufferedWriter br2=new BufferedWriter(new OutputStreamWriter(fs.create(file,true)));
            	//System.out.println(Double.toString(dangling/total));
            	br2.write( sc + "\n" + pageNum);
        	br2.close();


	}


}
