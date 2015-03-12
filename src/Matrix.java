import java.util.Arrays;


public class Matrix {

	private final int M;
	private final int N;
	private float data [][];

	public Matrix(float [][] data){
		this.M = data.length;
		this.N = data[0].length;
		this.data = new float [M][N];
		for (int i = 0; i < M; i++){
			for (int j = 0; j < N; j++){
				this.data[i][j] = data[i][j];
			}
		}
	}

	public Matrix (int M,int N){
		this.N = N;
		this.M = M;
		this.data = new float[M][N];
		for (int i=0; i<M; i++){
			for (int j=0; j<N; j++){
				this.data[i][j] = data[i][j];
			}
		}
	}
	
	public void setA(float [][] data ){
		this.data = data;
	}
	
	/**
	 * COPY CONSTRUCTOR 
	 * @param A Matrix 
	 */

	public Matrix(Matrix A){
		this(A.data);
	}
	

	public float [] getCol(int col){
		if (col > this.N-1)
			throw new java.lang.RuntimeException("Col Number ="+col+" does not exist, Matrix Dimension = ["+this.M+","+this.N+"]");
		float [] temp = new float [this.M];
		for (int j=0;j<this.M; j++ ){
			temp[j] = this.data[j][col];
		}
		return temp;

	}
	
	public float [] getRow(int row){
		if (row> this.M-1)
			throw new java.lang.RuntimeException("ROW Number ="+row+" does not exist, Matrix Dimension = ["+this.M+","+this.N+"]");
		float [] temp = new float [this.N];
		for (int i=0; i<this.data[0].length; i++){
			temp[i] = this.data[row][i];
		}
		return temp;
	}
	
	public float [][] getData	(){
		return this.data;
	}
	
	public void setElement(int row,int col,float value){
		this.data[row][col]= value;
	}
	
	public float getElement(int row,int col){
		return this.data[row][col];
	}
	
	
	public void show() {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) 
                System.out.printf("%9.4f ", data[i][j]);
            System.out.println();
        }
    }
	
	public int getM(){
		return this.M;
	}
	
	public int getN(){
		return this.N;
	}
	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + M;
		result = prime * result + N;
		result = prime * result + Arrays.hashCode(data);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Matrix))
			return false;
		Matrix other = (Matrix) obj;
		if (M != other.M)
			return false;
		if (N != other.N)
			return false;
		if (!Arrays.deepEquals(data, other.data))
			return false;
		return true;
	}

}
