package code.pagerankClean;
import java.net.URI;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.URI;
//import java.io.BufferedReader;
import java.io.*;
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
import java.lang.*;
public class PageRankClean{

	static Configuration conf = new Configuration();

	public static double countScoreDangling (String hdfsPath) throws IOException {
                InputStream in = null;
                FileSystem hdfs = FileSystem.get(URI.create(hdfsPath), conf);
                BufferedReader buff = null;
                Path listFiles = new Path(hdfsPath);
                FileStatus stats[] = hdfs.listStatus(listFiles);
                StringBuffer docContent = new StringBuffer();
		double count = 0.;
                for (int i = 0; i < stats.length; i++) {
                        System.out.println("Directory:" + stats[i].getPath().toString());
                        Path p = new Path(stats[i].getPath().toString());
                        in = hdfs.open(p);
                        buff = new BufferedReader(new InputStreamReader(in));
                        String str = null;
                        while ((str = buff.readLine()) != null) {
				String[] title_list = str.split("\t");
                                if(title_list.length == 1){
					String[] title_score = title_list[0].split("]");
					count += Double.parseDouble(title_score[1]);
				}
				//docContent.append(str);

                        }
                        buff.close();
                        in.close();
                }
                return count;
        }
	public static double countLines (String hdfsPath) throws IOException {
                FileSystem fs = FileSystem.get(conf);
                Path pt = new Path(hdfsPath);
                BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(pt)));
                StringBuffer docContent = new StringBuffer();
                String str;
		double count = 0.;
		while ((str = br.readLine()) != null) {
			count += 1;
                }
		br.close();
                return count;
        }
	public static String findNonExist (String hdfsPath) throws IOException {
                FileSystem fs = FileSystem.get(conf);
                Path pt = new Path(hdfsPath);
		BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(pt)));
                StringBuffer docContent = new StringBuffer();
                String str;
                while ((str = br.readLine()) != null) {
                	String[] title_list = str.split("\t");
                        if(title_list.length == 2){
                               	String list = title_list[1];
				String[] title_score= title_list[0].split("]");
				String title = title_score[0];
				if(list.length() == 1 && list.charAt(0) == '_' ){//non-exist	
                               		docContent.append(title + "]");
                               		//count += 1;
				}
			}
                }
		br.close();
                return  docContent.toString();
        }
	public static void main(String[] args)throws Exception{

		String uri = "hdfs://10.1.114.6:8100/user/100060007/prgraphOutput/part-00000";
		double totLine = countLines(uri);
		double sumDangle = countScoreDangling(uri);
		String[] num_non = findNonExist(uri).split("]");
		//int numNon = Integer.parseInt(num_non[0]);
			
		FileSystem fs = FileSystem.get(conf);
                Path pt = new Path(uri);
                BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(pt)));
                StringBuffer docContent = new StringBuffer();
                String str = null;
                while ((str = br.readLine()) != null) {
                       	String[] title_list = str.split("\t");
                        if(title_list.length == 2){//if non-exist or normal
                                String list = title_list[1];
                                String[] title_score= title_list[0].split("]");
                                String title = title_score[0];

				if(list.length() != 1 || list.charAt(0) != '_' ){//normal
					for(int j = 0; j < num_non.length; j++){ 
						//remove non-exist of that line
						int index = list.indexOf(num_non[j]);
						//System.out.println(index + ",\t" + num_non[j]);
						if(index>=0){//if in the list
							if(num_non[j].length() > 0){
								System.out.println(num_non[j]);
								list = list.replace(num_non[j]+ "]","");
								//System.out.println(list);
							}
						}
                                                	//docContent.append(str + "\n");
					}
					docContent.append(title_list[0] + "\t" + list + "\n");                                        
                                }
                        }
			else//if dangling
				docContent.append(str + "\n");
		}
		br.close();
		Path file = new Path("hdfs://10.1.114.6:8100/user/100060007/prrankInput/input1");
	        BufferedWriter br2=new BufferedWriter(new OutputStreamWriter(fs.create(file,true)));
		br2.write(docContent.toString());
            	br2.close();
		
		Path file2 = new Path("hdfs://10.1.114.6:8100/user/100060007/prrankInput/info.txt");
                BufferedWriter br3=new BufferedWriter(new OutputStreamWriter(fs.create(file2,true)));
                br3.write(Double.toString(sumDangle)+ "\n"+ Double.toString(totLine));
                br3.close();
		
	}
}
