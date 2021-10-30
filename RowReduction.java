
public class RowReduction {
	
	static /*
	 * Puts the matrix into reduced row echelon
	 * form. 
	 * input - the matrix to be transformed (m)
	 * output - the matrix in rref (m)
	 */
	
	MatrixGenerator mg = new MatrixGenerator();
	public static int[][] rref(int [][] a){
	    int lead = 0;
	    int[][]m = copyMatrix(a);
	    int rowCount = m.length;
	    int colCount = m[0].length;
	    int index;
	    boolean quit = false;
	    for(int row = 0; row < rowCount && !quit; row++){
	        if(colCount <= lead){
	            quit = true;
	            break;
	        }
	        index=row;
	        while(!quit && m[index][lead] == 0){
	            index++;
	            if(rowCount == index){
	                index=row;
	                lead++;
	                if(colCount == lead){
	                    quit = true;
	                    break;
	                }
	            }
	        }
	        if(!quit){
	            swapRows(m, index, row);
	            for(index = 0; index < rowCount; index++){
	                if(index != row) {
	                    subtractRows(m, m[index][lead], row, index);
	            }
	            }
	        }
	    }
	    return m;
	}

	/*
	 * Swaps two rows within a matrix 
	 * inputs - the matrix (m)
	 * 			the first row (row1)
	 * 			the second row (row2)
	 */
	static void swapRows(int [][] m, int row1, int row2){
	    int [] swap = new int[m[0].length];
	    for(int c1 = 0; c1 < m[0].length; c1++)
	        swap[c1] = m[row1][c1];
	    for(int c1 = 0; c1 < m[0].length; c1++){
	        m[row1][c1] = m[row2][c1];
	        m[row2][c1] = swap[c1];
	    }
	}
	
	/*
	 * Subtracts a scalar multiple of one row from another.
	 * inputs - the matrix for the operation (m)
	 * 			the scalar, indicated as the leading one for each row (s)
	 * 			the row that is multiplied by the scalar (row1)
	 * 			the row that is being subtracted by row1 (row2)
	 */
	static void subtractRows(int [][] m, double s, int row1, int row2){
	    for(int i = 0; i < m[0].length; i++) {
	        m[row2][i] -= s * m[row1][i];
	    		if(m[row2][i]%2==0) {
	    			m[row2][i] = 0;
	    		}else {
	    			m[row2][i]=1;
	    		}
	    }
	}
	
	public static int[][] copyMatrix(int[][] a){
		int[][] copy = new int[a.length][];
		for(int i=0; i<a.length; i++) {
			copy[i] = a[i].clone();
		}
		return copy;
	}
	
}
