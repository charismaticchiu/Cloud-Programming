package part2;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;


public class SumCountPair implements Writable {
	private int sum;
	private int count;

	public SumCountPair(){
		this.sum = 0;
		this.count = 0;
	}

	public SumCountPair(int sum, int count) {
		this.sum = sum;
		this.count = count;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(sum);
		out.writeInt(count);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		sum = in.readInt();
		count = in.readInt();		
	}
	
	@Override
	public String toString(){						
		return sum + "\t" + count;
	}
	
	
	public int getSum(){
		return this.sum;
	}
	
	public int getCount(){
		return this.count;
	}
}

