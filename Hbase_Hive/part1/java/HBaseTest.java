import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;

import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Put;

import org.apache.hadoop.hbase.util.Bytes;
import java.io.InputStreamReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
//import java.io.IOException;
public class HBaseTest {
	private static Configuration conf = null;

	// You can lookup usage of these api from this website. =)
	// http://hbase.apache.org/0.94/apidocs/

	static {
		conf = HBaseConfiguration.create();
	}

	public static void createTable(String tableName, String[] colFamilys) throws Exception {
		HBaseAdmin admin = new HBaseAdmin(conf);
		if (admin.tableExists(tableName)) {
			System.out.println("Table already exists!");
		} else {
			// TODO add column familiys to HTableDescriptor instance by for loop
			HTableDescriptor desc = new HTableDescriptor( Bytes.toBytes(tableName));
			for(int i = 0; i < colFamilys.length; i++){
				HColumnDescriptor coldef = new HColumnDescriptor(Bytes.toBytes(colFamilys[i]));
				desc.addFamily(coldef);
			}
			admin.createTable(desc);
			// TODO admin creates table by HTableDescriptor instance
			
			
			System.out.println("Create table " + tableName);
		}
	}

	public static void removeTable(String tableName) throws Exception {
		HBaseAdmin admin = new HBaseAdmin(conf);
		// TODO disable & drop table

		System.out.println("Remove table " + tableName);
	}

	public static void addRecord(String tableName, String rowKey, String colFamily,
			String qualifier, String value) throws Exception {
		HTable table = new HTable(conf, tableName);
		Put p = new Put(Bytes.toBytes(rowKey));
		p.add(Bytes.toBytes(colFamily), Bytes.toBytes(qualifier), Bytes.toBytes(value));
		table.put(p);
		// TODO use Put to wrap information and put it to HTable instance, table.
		
		System.out.println("Insert record " + rowKey + " to table " + tableName + " ok.");
	}
	public static void deleteRecord(String tableName, String rowKey) throws Exception {
		HTable table = new HTable(conf, tableName);
		
		// TODO use Delete to wrap key information and use HTable api to delete it.
		
		System.out.println("Delete record " + rowKey + " from table " + tableName + " ok.");
	}
	public static void getRecord(String tableName, String rowKey) throws Exception {
		HTable table = new HTable(conf, tableName);
	
		Get get = new Get(rowKey.getBytes());
        Result rs = table.get(get);
        for (KeyValue kv : rs.raw()) {
			System.out.print(new String(kv.getRow()) + " ");	
			System.out.print(new String(kv.getFamily()) + ":");
			System.out.print(new String(kv.getQualifier()) + " "); 
			System.out.print(kv.getTimestamp() + " ");
			System.out.println(new String(kv.getValue()));
		}
	}	
	public static void getAllRecord(String tableName) throws Exception {
		HTable table = new HTable(conf, tableName);
		Scan s = new Scan();
		ResultScanner rs = table.getScanner(s);
		for (Result r: rs) {
			for (KeyValue kv : r.raw()) {
				System.out.print(new String(kv.getRow()) + " ");
				System.out.print(new String(kv.getFamily()) + ":");
				System.out.print(new String(kv.getQualifier()) + " ");
				System.out.print(kv.getTimestamp() + " ");
				System.out.println(new String(kv.getValue()));
			}
		}
	}
	public static void main(String[] args) {
		try {
        	// TOOD write your code here =)
			String[] colfamily = {"grade"};
			createTable("100060007_math",colfamily);
			//FileReader file = new FileReader("/home/cloud2015/100060007/Lab5/testdata/math");

			FileInputStream fstream1 = new FileInputStream("/home/cloud2015/100060007/Lab5/testdata/math");
			// Get the object of DataInputStream
			DataInputStream in1 = new DataInputStream(fstream1);
			BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
			String strLine;
			//Read File Line By Line
			while ((strLine = br1.readLine()) != null) {
				// Print the content on the console
				String[] name_score = strLine.split("\t");
				addRecord("100060007_math",name_score[0], "grade","math" , name_score[1] );
				addRecord("100060007_math",name_score[0], "grade","name" , name_score[0] );
			}
			//Close the input stream
			in1.close();	

			createTable("100060007_eng",colfamily);
                        //FileReader file = new FileReader("/home/cloud2015/100060007/Lab5/testdata/math");

                        FileInputStream fstream2 = new FileInputStream("/home/cloud2015/100060007/Lab5/testdata/eng");
                        // Get the object of DataInputStream
                        DataInputStream in2 = new DataInputStream(fstream2);
                        BufferedReader br2 = new BufferedReader(new InputStreamReader(in2));
                       
                        //Read File Line By Line
                        while ((strLine = br2.readLine()) != null) {
                                // Print the content on the console
                                String[] name_score = strLine.split("\t");
                                addRecord("100060007_eng",name_score[0], "grade","eng" , name_score[1] );
				addRecord("100060007_math",name_score[0], "grade","name" , name_score[0] );
                        }
                        //Close the input stream
                        in2.close();
			getAllRecord("100060007_eng");
			getAllRecord("100060007_math");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
