package code.pagerankSort;

import java.lang.Character;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import java.lang.Double;
public class PageRankSortKeyComparator extends WritableComparator {
	public PageRankSortKeyComparator() {
		super(Text.class, true);
	}

	// TODO Order by A -> a -> B -> b .... 
	public int compare(WritableComparable o1, WritableComparable o2) {
		Text key1 = (Text) o1;
		Text key2 = (Text) o2;
	
		String[] title_score1 = key1.toString().split("]");
		String[] title_score2 = key2.toString().split("]");
		double score1 = Double.parseDouble(title_score1[1]);
		double score2 = Double.parseDouble(title_score2[1]);
		if (score1 == score2)
			return -1;// if score is the same then the one  with smaller leading character be the first
		else if(score1 > score2)
			return -1;
		else return 1;
/*
		char c1 = key1.toString().charAt(0);
		char c2 = key2.toString().charAt(0);
		if ( ((c1 - c2) == ('A' - 'a')) || ((c1 - c2) == ('a' - 'A')) )
		{
			if (c1 <= c2) return -1;//A,a
			else return 1;
		}	
		else if (c1 == c2) return 0;
		else if (Character.toLowerCase(c1) > Character.toLowerCase(c2))
			return 1;
		else return -1;
*/		
	}
}
