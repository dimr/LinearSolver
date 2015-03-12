
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;

public class BlandTable implements Comparable<BlandTable> {

	private int row;
	private int col;
	private float value;
	// public static final Comparator<BlandTable> BY_VALUE = new byValue();

	public static final Comparator<BlandTable> BY_BLANDS_RULE = new byBlandsRule();

	public BlandTable(int row, int col, float value) {
		this.value = value;
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public float getValue() {
		return value;
	}

	// @Override
	public int compareTo(BlandTable that) {
		return this.value < that.value ? -1 : this.value == that.value ? 0 : 1;
	}

	public static class byBlandsRule implements Comparator<BlandTable> {

		@Override
		public int compare(BlandTable o1, BlandTable o2) {
			Float a = new Float(o1.value);
			Float b = new Float(o2.value);
			int result = a.compareTo(b);
			if (result == 0)
				result = o1.row < o2.row ? -1 : o1.row == o2.row ? 0 : 1;
			if (result == 0)
				result = o1.col < o2.col ? -1 : o1.col == o2.col ? 0 : 1;
			return result;
		}
	}

	@Override
	public String toString() {
		return "BlandTable [row=" + row + ", col=" + col + ", value=" + value
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + row;
		result = prime * result + Float.floatToIntBits(value);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BlandTable))
			return false;
		BlandTable other = (BlandTable) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		if (Float.floatToIntBits(value) != Float.floatToIntBits(other.value))
			return false;
		return true;
	}

	public static void main(String[] args) {
		ArrayList<BlandTable> v = new ArrayList<BlandTable>();
		
		int size = 20;
		PriorityQueue<BlandTable> pq = new PriorityQueue<BlandTable>(size,
				BlandTable.BY_BLANDS_RULE);
		for (int i = 0; i < size; i++) {
			int c = randInt(1, 9);
			int b = randInt(2, 3);
			int d = randInt(30, 31);
			v.add(new BlandTable(b, c, d));
			pq.add(new BlandTable(b, c, d));
		}
		
		 
		System.out.println("----------------------------");
		Collections.sort(v, BlandTable.BY_BLANDS_RULE);
		for (BlandTable t : v)
			System.out.println(t + " ");
		System.out.println("----------------------------");
		System.out.println(pq.peek());

		assert (Collections.min(v) == v.get(0) && v.get(0) == pq.peek());
	}

	public static int randInt(int min, int max) {
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
	}
	// public static void sort(ArrayList<BlandTable> a,Comparator<BlandTable>
	// c){
	// int N = a.size();
	// for (int i=0; i<N; i++)
	// for (int j=i; j<0 && less(c,a.get(j),a.get(i-1)); j--)
	// exchange(a,j,j-1);
	//
	// }
	//
	// public static boolean less(Comparator <BlandTable> c, BlandTable
	// v,BlandTable w){
	// return c.compare(v, w)<0;
	// }
	// public static void exchange(ArrayList<BlandTable> a,int i,int j){
	// BlandTable t = a.get(i);
	// a.add(i, a.get(j));
	// a.add(j, t);
	// }

}
