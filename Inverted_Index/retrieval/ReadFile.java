package retrieval;
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

public class ReadFile{

        static Configuration conf = new Configuration();
	public static String readInvertedIndexAll(String hdfsPath) throws IOException {
                InputStream in = null;
                FileSystem hdfs = FileSystem.get(URI.create(hdfsPath), conf);
                BufferedReader buff = null;
                Path listFiles = new Path(hdfsPath);
                FileStatus stats[] = hdfs.listStatus(listFiles);
		StringBuffer docContent = new StringBuffer();
                for (int i = 0; i < stats.length; i++) {
                    if (stats[i].isDir()) {
                        readInvertedIndexAll(stats[i].getPath().toString());
                    }
                    else {
                        System.out.println("Directory:" + stats[i].getPath().toString());
                        Path p = new Path(stats[i].getPath().toString());
                        in = hdfs.open(p);
                        buff = new BufferedReader(new InputStreamReader(in));
                        String str = null;
			//StringBuffer docContent = new StringBuffer();
                        while ((str = buff.readLine()) != null) {
				docContent.append(str);                             
                                
                        }
                        buff.close();
                        in.close();
			//return docContent.toString();
                    }
                }
		return docContent.toString();
        }

	public static String searchInvertedIndex(String hdfsPath, String keyword) throws IOException {
	        InputStream in = null;
        	FileSystem hdfs = FileSystem.get(URI.create(hdfsPath), conf);
	        BufferedReader buff = null;
        	Path listFiles = new Path(hdfsPath);
	        FileStatus stats[] = hdfs.listStatus(listFiles);
		String str = null;
		int count =0;
		StringBuffer strbuf = new StringBuffer() ;
        	for (int i = 0; i < stats.length; i++) {
	            if (stats[i].isDir()) {
                	searchInvertedIndex(stats[i].getPath().toString(),keyword);
        	    }
	            else {
        	        System.out.println("Directory:" + stats[i].getPath().toString());
		        Path p = new Path(stats[i].getPath().toString());
        	        in = hdfs.open(p);
        		buff = new BufferedReader(new InputStreamReader(in));
	                str = null;
			//int count = 0;
                	while ((str = buff.readLine()) != null) {
				String[] word_file = str.split("\t");
				String[] word_df = word_file[0].split(":");
				if (word_df[0].compareTo(keyword) == 0 )
				{ 	
					count +=1;	
					buff.close();
                        		in.close();
					return str;
					/*strbuf.append(str);
					count ++;*/
				}
			/*	else if (word_df[0].toLowerCase().compareTo(keyword.toLowerCase()) == 0 )
				{
					strbuf.append(word_file[1]);
				}*/
	                }
                	buff.close();
                	in.close();
            	    }	
        	}
		if (count == 0 ) return "n";
		return str;
    	}
	public static void seekFile(String hdfsPath, int offset) throws IOException {
                FSDataInputStream in = null;
                FileSystem hdfs = FileSystem.get(URI.create(hdfsPath), conf);
                //BufferedReader buff = null;
                Path listFiles = new Path(hdfsPath);
                FileStatus stats[] = hdfs.listStatus(listFiles);
                //String str = null ;
                for (int i = 0; i < stats.length; i++) {
                    if (stats[i].isDir()) {
                        seekFile(stats[i].getPath().toString(), offset);
                    }
                    else {
                        //System.out.println("Directory:" + stats[i].getPath().toString());
                        Path p = new Path(stats[i].getPath().toString());
                        in = hdfs.open(p);
			in.seek(offset-10);
			IOUtils.copyBytes(in, System.out,25 ,40, false);  
                        in.close();
                    }
                }
                
        }



	public static void main(String[] args)throws Exception{
		boolean flag = true;
		boolean validinput = true;
		while(flag){
		System.out.print("\nPlease enter keywords: ");
		Scanner scanner = new Scanner(System.in);
		String keywordString = scanner.next();
		String[] keywords = keywordString.split(",");	
		//System.out.println(keywords[0]);
		//System.out.println(args[0]);
		String uri = "hdfs://10.1.114.6:8100/user/100060007/indexOutput/part-00000";
		String uri2= "hdfs://10.1.114.6:8100/user/100060007/indexOutput/part-00001";
		List<String> rawOutput = new ArrayList<String>();
		for(int i =0; i <keywords.length; i++){
			try{	
				if (keywords[i].charAt(0)>='A' && keywords[i].charAt(0) <= 'G' ){
					String out = searchInvertedIndex(uri,keywords[i]);
					if (out.compareTo("n") == 0){ validinput = false;
						System.out.println("Wrong or Invalid Input!!");                              	
						break;
					}
					else { rawOutput.add( out );validinput = true;}
				}
				else if (keywords[i].charAt(0)>='a' && keywords[i].charAt(0) <= 'g' ){
					//rawOutput.add( searchInvertedIndex(uri,keywords[i]) );
					String out = searchInvertedIndex(uri,keywords[i]);
                                        if (out.compareTo("n") == 0){ validinput = false;
                                                System.out.println("Wrong or Invalid Input!!");	
						break;
                                        }
                                        else{ rawOutput.add( out );validinput = true;}
				}
				else{ 
					String out =  searchInvertedIndex(uri2,keywords[i]) ;
					if (out.compareTo("n") == 0){ validinput = false;
                                                System.out.println("Wrong or Invalid Input!!");
						break;
                                        }
                                        else{ rawOutput.add( out );validinput = true;}
				}
				//validinput = true;
			}catch(NullPointerException ex){
				validinput = false;
				System.out.println("Wrong or Invalid Input!!");
			}
		}
		if (validinput){
		//rawString is the collection of the lines we want. words and files and offsets. could be multiple elements
		String[] rawString = new String[rawOutput.size()];
		rawOutput.toArray(rawString);
			
		//for each line, each keyword, we do the same operation.
		for(int i = 0; i< keywords.length; i++){
			String[] word_file = rawString[i].split("\t");
			//word_file[0] will contain word:dc, 
			//word_file[1] will contain filename:tf:offsets;filename2:tf2:offsets2
			String[] word_dc = word_file[0].split(":");
			//word_df[0] is the word
			//word_df[1] is the df
			double dc = Double.parseDouble(word_dc[1]);//document frequency
			
			String[] tc_offsets = word_file[1].split(";");
			//each element of tf_offsets is the filename, tf and the offsets
			
			Double[] tfidf = new Double[tc_offsets.length];
			String[] docRank = new String[tc_offsets.length];
			//Initializing tfidf and docRank serially
			for(int j = 0; j < tc_offsets.length; j++){
				String[] doc_tc_offsets = tc_offsets[j].split(":");
				//doc_tc_offsets contains filename:tf:offsets
				String docName = doc_tc_offsets[0];
				String docUri = "hdfs://10.1.114.6:8100/user/100060007/indexInput/" + docName;
				docRank[j] = docName;
				double tc = Double.parseDouble(doc_tc_offsets[1]);
				String docContent = readInvertedIndexAll(docUri);
				String delimiter = " ,?[]()':.|!--;$&\t";
	                        StringTokenizer tokenizer = new StringTokenizer(docContent, delimiter);
				double docWordNum = 0.0;
				while(tokenizer.hasMoreTokens()) {
					String token = tokenizer.nextToken();
					docWordNum += 1.0;
				}
			 	tfidf[j] = tc/docWordNum * Math.log(24/dc);	
				/*System.out.println(tfidf[j]);
				for(int k = 2; k< doc_tc_offsets.length; k++){
					seekFile(docUri, Integer.parseInt(doc_tc_offsets[k]));
					System.out.println();
				}*/
			}
			//Sort tfidf and docRank Bubble Sort
			for(int j = 0; j< docRank.length; j++){
				for(int k = 0; k < docRank.length-1; k++){
					if (tfidf[k] < tfidf[k+1]){
						double tempdouble = tfidf[k+1];
						tfidf[k+1] = tfidf[k];
						tfidf[k] = tempdouble;
						String tempstr = docRank[k+1];
						docRank[k+1] = docRank[k];
						docRank[k] = tempstr;
					}
				}
			}
			for(int j = 0; j < tc_offsets.length ; j++){
				String[] doc_tc_offsets = null;
				// Search for the file :tc:offsetsi
				String filename =null;
				/*for(int k = 0; k < 24; k++){
					if (fileNames[k].compareTo(docRank[j]) == 0)
                                                filename = docRank[j];
				}*/
				for(int k = 0; k < tc_offsets.length; k++){
					String[] temp =  tc_offsets[k].split(":");
					if (temp[0].compareTo(docRank[j]) == 0)
						doc_tc_offsets = tc_offsets[k].split(":");
				}
                                String docUri = "hdfs://10.1.114.6:8100/user/100060007/indexInput/" + docRank[j];
				System.out.println("\n\nFilename:"+docRank[j]);
				System.out.println("Rank "+ Integer.toString(j+1));
                                System.out.println("Score:"+ Double.toString(tfidf[j]));
				System.out.println("Content:  ");	
				for(int k = 2; k< doc_tc_offsets.length; k++){
					System.out.print("  ");
                                        seekFile(docUri, Integer.parseInt(doc_tc_offsets[k]));
                                        if (k!=(doc_tc_offsets.length-1))System.out.print("\n\nNext:");
					else System.out.println();
                                }
			}
		}}
		}
	}


}
