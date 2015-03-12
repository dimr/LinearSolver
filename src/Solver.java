import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

public class Solver {

	private final float epsilon = 0.0001f;
	private float[] bounds;
	int[] bPositiveCoeffs;
	private int enteringVariable = -1;
	private int leavingVariable = -1;
	private int col = -1;
	private int row = -1;
	private Dict next;
	private Dict current;

	public Solver(Dict dict) {
		this.next = new Dict(dict);
		current = dict;
		this.bounds = new float[next.getM()];
		chooseColAndRow();
		calcBounds();
		calcMat();
		calcObjectiveValue();
		calcCoeffs();

	}

	public boolean timeToBreak(float[] a) {
		return allNegative(a);
	}

	/**
	 * check if optimization is possible, if all coefficients of the the b
	 * vector are negative, then this is the final dict
	 * 
	 * @param b
	 *            the b vector
	 * @return
	 */
	private boolean allNegative(float[] a) {
		int negatives = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] <= 0) {
				negatives++;
			}
		}
		return negatives == a.length;
	}

	/**
	 * get the **indices** of the positive coefficients of b vector(Array here)
	 * positive.coefs<-which(dict$obj.coefs>0)
	 * 
	 * @param the
	 *            coefficients array
	 * @return ArrayList<Integer>
	 */
	private int[] positiveCoeffs(float[] a) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < a.length; i++) {
			if (a[i] > 0) {
				result.add(i);
			}
		}
		return ArrayUtils.toPrimitive(result.toArray(new Integer[result.size()]));
	}

	/**
	 * Checks if all coefficients of the specific column are ALL >=0, if yes
	 * UNBOUNDED
	 */
	public void positiveMatrixCoeffs(int col) {
		float[] temp = this.next.getA().getCol(col);
		int counter = 0;
		for (int i = 0; i < temp.length; i++) {
			if (temp[i] >= 0) {
				counter++;
			}
		}
		if (counter == temp.length)
			throw new java.lang.RuntimeException("UNBOUNDED \nEntering Variable =" + next.getNonBasic()[col]);

	}

	public void chooseColAndRow() {
		int[] posCoeffs = positiveCoeffs(next.getCoeffs());

		if (posCoeffs.length == 1) {
			this.col = posCoeffs[0];
		} else {
			int minNonBasic = next.getNonBasic()[posCoeffs[0]];
			for (int i = 0; i < posCoeffs.length; i++) {
				if (next.getNonBasic()[posCoeffs[i]] <= minNonBasic) {
					minNonBasic = next.getNonBasic()[posCoeffs[i]];
					this.col = posCoeffs[i];
				}
			}
		}
		positiveMatrixCoeffs(this.col);
		// this.enteringVariable = this.next.getNonBasic()[this.col];

		float[] tempColumn = next.getA().getCol(this.col);
		for (int i = 0; i < tempColumn.length; i++) {
			this.bounds[i] = tempColumn[i] < 0 ? -next.getB()[i] / tempColumn[i] : Float.POSITIVE_INFINITY;
		}

		float bMin = this.bounds[0];
		for (int i = 0; i < this.bounds.length; i++) {
			if ((this.bounds[i] - bMin <= epsilon)) {
				bMin = this.bounds[i];
			}
		}
		ArrayList<Integer> h = new ArrayList<Integer>();
		for (int i = 0; i < this.bounds.length; i++) {
			if ((this.bounds[i] - bMin <= epsilon)) {
				h.add(i);
			}
		}
		if (h.size() == 1) {
			this.row = h.get(0);
			this.leavingVariable = next.getBasic()[this.row];
			pivot();
			return;
		} else if (h.size() == 0 /* ||this.row == -1 */) {
			throw new java.lang.RuntimeException("Something is wrong" + this.row);
		}

		this.row = h.get(0);
		for (int i = 0; i < h.size(); i++) {
			if (this.next.getBasic()[h.get(i)] < this.next.getBasic()[this.row]) {
				this.row = h.get(i);

			}
		}

		pivot();
	}

	public void pivot() {
		int[] copyNonBasic = Arrays.copyOf(this.next.getNonBasic(), this.next.getNonBasic().length);
		int[] copyBasic = Arrays.copyOf(this.next.getBasic(), this.next.getBasic().length);
		int temp = next.getBasic()[this.row];
		copyBasic[this.row] = next.getNonBasic()[col];
		copyNonBasic[this.col] = temp;
		next.setBasic(copyBasic);
		next.setNonBasic(copyNonBasic);
		copyBasic = copyNonBasic = null;

	}

	public int getEnteringVariable() {
		return this.enteringVariable;
	}

	public int getLeavingVariable() {
		return this.leavingVariable;
	}

	public void calcBounds() {
		float[] bs = Arrays.copyOf(this.next.getB(), this.next.getB().length);
		bs[row] = bs[row] / -this.next.getA().getElement(this.row, this.col);
		float[] tempCol = this.next.getA().getCol(this.col);
		for (int i = 0; i < bs.length; i++) {
			if (i != this.row) {
				bs[i] = bs[i] + tempCol[i] * bs[this.row];
			}
		}
		this.next.setB(bs);
		bs = null;
	}

	public void calcMat() {
		Matrix m = new Matrix(this.next.getA());
		float pivotValue = m.getElement(this.row, this.col);
		m.setElement(this.row, this.col, -1);
		float[] tempRow = m.getRow(this.row);
		for (int i = 0; i < tempRow.length; i++) {
			tempRow[i] = tempRow[i] / -pivotValue;
		}
		for (int i = 0; i < m.getM(); i++) {
			if (i != this.row) {
				float rk = m.getElement(i, this.col);
				m.setElement(i, this.col, 0);
				for (int j = 0; j < m.getN(); j++) {
					float vv = rk * tempRow[j] + m.getElement(i, j);
					m.setElement(i, j, vv);
				}
			}
		}

		for (int j = 0; j < m.getN(); j++) {
			m.setElement(this.row, j, tempRow[j]);
		}
		next.setA(m);
		m = null;
	}

	public void calcObjectiveValue() {
		float obj = next.getObjectiveValue() + next.getCoeffs()[this.col] * next.getB()[this.row];
		this.next.setObjectiveValue(obj);
	}

	public void calcCoeffs() {
		float[] c = Arrays.copyOf(this.next.getCoeffs(), this.next.getCoeffs().length);
		float[] matCol = this.next.getA().getRow(this.row);
		float temp = c[this.col];

		for (int i = 0; i < matCol.length; i++) {
			matCol[i] = matCol[i] * temp;
		}
		c[this.col] = 0;
		for (int i = 0; i < matCol.length; i++) {
			if (i != this.col)
				;
			matCol[i] += c[i];
		}

		this.next.setCoeffs(matCol);
		c = null;
	}

	public Dict solve() {
		if (current.equals(this.next))
			throw new java.lang.RuntimeException("Dicts are the same");
		return this.next;
	}

}
