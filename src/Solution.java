import java.util.ArrayList;

public class Solution {

	private Solver solver;
	private Dict finalDict;
	private int pivots;

	public Solution(Dict initialDict, int maxIterations) {

		solver = new Solver(initialDict);
		Dict dict1 = solver.solve();

		if (timeToBreak(dict1.getCoeffs())) {
			pivots++;
			finalDict = dict1;
			return;
		}

		Dict[] dicts = new Dict[maxIterations];
		dicts[0] = dict1;
		for (pivots = 1; pivots < maxIterations; pivots++) {
			dicts[pivots] = new Solver(dicts[pivots - 1]).solve();
			if (timeToBreak(dicts[pivots].getCoeffs())) {
				break;
			}
		}
		pivots++;

		for (int i = 0; i < maxIterations; i++) {
			if (dicts[i] == null) {
				finalDict = dicts[i - 1];
				break;
			}
		}
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

	@Override
	public String toString() {
		return "Solution [OBJECTIVE = " + finalDict.getObjectiveValue()
				+ ", pivots=" + pivots + "] ";
	}

}
