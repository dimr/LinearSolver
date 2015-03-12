//import Dict;
//import DictReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Arrays;

public class Dict {

	private int M;
	private int N;
	private int[] basic;
	private int[] nonBasic;
	private float[] b;
	private Matrix A;
	private float objectiveValue;
	private float[] coeffs;
	private String[] lines;
	private int count = 0;

	public Dict() {

	}

	public Dict(int M, int N, int[] basic, int[] nonBasic, float[] b, Matrix A,	float objectiveValue, float[] coeffs) {
		this.N = N;
		this.M = M;

		this.basic = new int[DictReader.getBasic().length];
		// for (int i=0; i<DictReader.getBasic().length; i++){
		// basic[i] = DictReader.getBasic()[i];
		// }
		this.basic = basic;
		this.nonBasic = new int[DictReader.getNonBasic().length];
		this.nonBasic = nonBasic;
		this.A = A;
		this.b = b;
		this.objectiveValue = objectiveValue;
		this.coeffs = coeffs;

	}

	/**
	 * COPY CONTRUCTOR
	 * 
	 * @param previous
	 *            Dict
	 */
	public Dict(Dict previous) {
		this(previous.getM(), previous.getN(), previous.getBasic(), previous
				.getNonBasic(), previous.getB(), previous.getA(), previous
				.getObjectiveValue(), previous.getCoeffs());

	}

	public void setM(int M) {
		this.M = M;
	}

	public int getM() {
		return M;
	}

	public void setN(int N) {
		this.N = N;
	}

	public int getN() {
		return N;
	}

	public void setBasic(int[] basic) {
		this.basic = basic;
	}

	public int[] getBasic() {
		return basic;
	}

	public void setNonBasic(int[] nonBasic) {
		this.nonBasic = nonBasic;
	}

	public int[] getNonBasic() {
		return nonBasic;
	}

	public void setB(float[] b) {
		this.b = b;
	}

	public float[] getB() {
		return b;
	}

	public void setA(Matrix A) {
		this.A = A;
	}

	public Matrix getA() {
		return A;
	}

	public void setObjectiveValue(float objectiveValue) {
		this.objectiveValue = objectiveValue;
	}

	public float getObjectiveValue() {
		return objectiveValue;
	}

	public void setCoeffs(float[] coeffs) {
		this.coeffs = coeffs;
	}

	public float[] getCoeffs() {
		return coeffs;
	}



	@Override
	public String toString() {
		this.A.show();
		return "Dict = [M=" + M + ", N=" + N + ", basic="
				+ Arrays.toString(this.basic) + ", nonBasic="
				+ Arrays.toString(nonBasic) + ", b=" + Arrays.toString(b)
				+ ", objectiveValue=" + objectiveValue + ", coeffs="
				+ Arrays.toString(coeffs) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((A == null) ? 0 : A.hashCode());
		result = prime * result + M;
		result = prime * result + N;
		result = prime * result + Arrays.hashCode(b);
		result = prime * result + Arrays.hashCode(basic);
		result = prime * result + Arrays.hashCode(coeffs);
		result = prime * result + count;
		result = prime * result + Arrays.hashCode(lines);
		result = prime * result + Arrays.hashCode(nonBasic);
		result = prime * result + Float.floatToIntBits(objectiveValue);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Dict))
			return false;
		Dict other = (Dict) obj;
		if (A == null) {
			if (other.A != null)
				return false;
		} else if (!A.equals(other.A))
			return false;
		if (M != other.M)
			return false;
		if (N != other.N)
			return false;
		if (!Arrays.equals(b, other.b))
			return false;
		if (!Arrays.equals(basic, other.basic))
			return false;
		if (!Arrays.equals(coeffs, other.coeffs))
			return false;
		if (count != other.count)
			return false;
		if (!Arrays.equals(lines, other.lines))
			return false;
		if (!Arrays.equals(nonBasic, other.nonBasic))
			return false;
		if (Float.floatToIntBits(objectiveValue) != Float
				.floatToIntBits(other.objectiveValue))
			return false;
		return true;
	}

}
