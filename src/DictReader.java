import java.util.Arrays;

import processing.core.PApplet;


public class DictReader {

	
	private static int M;
	private static int N;
	private static int [] basic;
	private static int [] nonBasic;
	private static float [] b;
	private static float [] [] data;
	private static float objectiveValue;
	private static float [] coeffs;
    private static String [] lines;
	private static PApplet pa;
	private static Matrix A;

	
	private  DictReader(String fileName){
		pa= new PApplet();
		lines = pa.loadStrings(fileName);
		cleanLines();
		M = Integer.parseInt(lines[0].trim().split("\\s+")[0]);
		N = Integer.parseInt(lines[0].trim().split("\\s+")[1]);

		basic = parseInts(lines[1].trim().split("\\s+"));
		nonBasic = parseInts(lines[2].trim().split("\\s+"));
		b = parseFloats(lines[3].trim().split("\\s+"));
		data = new float [M][N];
		this.A = new Matrix(data);
		int counter =0;
		for (int i=4; i<4+M; i++){
			String [] temp = lines[i].trim().split("\\s+");
			for (int j=0; j<N;j++){
				data[counter][j]=Float.parseFloat(temp[j]);

			}
			counter++;
		}
		String lastLine = lines[lines.length-1];
		objectiveValue = Float.parseFloat(lastLine.trim().split("\\s+")[0]);
		coeffs = new float [N];
		for (int i=1; i<N+1; i++){
			coeffs[i-1] = Float.parseFloat(lastLine.trim().split("\\s+")[i]);
		}
		//prettyPrint(A);
		
	}
	/**
	 * Constructor only for First Dict
	 * @param file
	 */
	
	public static void loadDict(String fileName){
		PApplet pa= new PApplet();
		lines = pa.loadStrings(fileName);
		cleanLines();
		M = Integer.parseInt(lines[0].trim().split("\\s+")[0]);
		N = Integer.parseInt(lines[0].trim().split("\\s+")[1]);
		basic = parseInts(lines[1].trim().split("\\s+"));
		nonBasic = parseInts(lines[2].trim().split("\\s+"));
		b = parseFloats(lines[3].trim().split("\\s+"));
		data = new float [M][N];
		
		
		int counter =0;
		for (int i=4; i<4+M; i++){
			String [] temp = lines[i].trim().split("\\s+");
			for (int j=0; j<N;j++){
				data[counter][j]=Float.parseFloat(temp[j]);

			}
			counter++;
		}
		//A.setA(data);
		A = new Matrix(data);
		String lastLine = lines[lines.length-1];
		objectiveValue = Float.parseFloat(lastLine.trim().split("\\s+")[0]);
		coeffs = new float [N];
		for (int i=1; i<N+1; i++){
			coeffs[i-1] = Float.parseFloat(lastLine.trim().split("\\s+")[i]);
		}
	}
	
	public static Dict initialiize(String file){
		loadDict(file);
		return new Dict( M, N, basic,nonBasic,b,A,objectiveValue,coeffs);
	}
	public static void cleanLines(){
		for (int i=0; i<lines.length; i++){
			lines[i].trim();
		}
	}

	private static int [] parseInts(String [] a){
		int [] result = new int [a.length];
		for (int i=0; i<a.length; i++){
			result[i] = Integer.parseInt(a[i]);
		}
		return result;
	}
	
	private static float [] parseFloats(String [] a){
		float [] result = new float [a.length];
		for (int i=0; i<a.length; i++){
			result[i] = Float.parseFloat(a[i]);
		}
		return result;
	}


	public static int getM() {
		return M;
	}

	public static int getN() {
		return N;
	}

	public static int[] getBasic() {
		return basic;
	}

	public static int[] getNonBasic() {
		return nonBasic;
	}

	public static float [] getB() {
		return b;
	}

	public static Matrix  getA() {
		return A;
	}

	public static float getObjectiveValue() {
		return objectiveValue;
	}

	public static  float[] getCoeffs() {
		return coeffs;
	}

	

//	public void prettyPrint(){
//		for(int i=0; i<M; i++){
//			for(int j=0; j<N; j++){
//				System.out.print(String.format("%20s", this.A[i][j]));
//			}
//			System.out.println("");
//		}
//	}
//
//	@Override
//	public String toString() {
//		prettyPrint();
//		return "Dict = [M=" + M + ", N=" + N + ", basic="
//				+ Arrays.toString(basic) + ", nonBasic="
//				+ Arrays.toString(nonBasic) + ", b=" + Arrays.toString(b)
//				+  ", objectiveValue="
//				+ objectiveValue + ", coeffs=" + Arrays.toString(coeffs) + "]";
//	}
}
