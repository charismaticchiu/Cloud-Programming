package code.pagerankCalc;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class PageRankCalcGroupComparator extends WritableComparator {

        public PageRankCalcGroupComparator() {
                super(Text.class, true);
        }

        // TODO group by start letter
        public int compare(WritableComparable o1, WritableComparable o2) {
                Text key1 = (Text) o1;
                Text key2 = (Text) o2;
		String k1 = key1.toString();
		String k2 = key2.toString();
                if(k1.length() == 5 && k1.charAt(4)=='D'){//key1 == DDDDD
			
		}
		int t1char = key1.charAt(0);
                int t2char = key2.charAt(0);
                if (t1char < t2char) return 1;
                else if (t1char > t2char) return -1;
                else return 0;
        }
}

