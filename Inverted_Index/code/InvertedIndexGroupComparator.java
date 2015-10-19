package code;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class InvertedIndexGroupComparator extends WritableComparator {

	public InvertedIndexGroupComparator() {
		super(Text.class, true);
	}	

	// TODO group by start letter
	public int compare(WritableComparable o1, WritableComparable o2) {
		Text key1 = (Text) o1;
		Text key2 = (Text) o2;
		int t1char = key1.charAt(0); 
		int t2char = key2.charAt(0); 
		if (t1char < t2char) return 1;
		else if (t1char > t2char) return -1;
		else return 0;
	}
}

