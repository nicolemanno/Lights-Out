import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MatrixGenerator {
	
	RandomGraph rg = new RandomGraph();
	
	/**
	 * @param a - matrix before in rre form
	 * @return boolean value of the existence of zeros 
	 */
	public boolean rowOfZeros(int[][] a) {
		
		int[][] rreA = rref(a);	
		boolean zero = false;
		for (int i=0; i<rreA.length; i++) {
			int count=0;
			for(int j=0; j<rreA[i].length; j++) {
				if(rreA[i][j]==0) {
					count++;
				}		
			}
			if(count==a.length) {
				zero=true;
				break;
			}			
		}
		return zero;
	}

	/**
	 * @param u - node visited 
	 * @param visited - list of nodes visited
	 * @param node - number of nodes in a
	 * @param a - matrix traversing
	 */
	public void traverse(int u, int[] visited, int node, int[][]a) {
		visited[u] = 1;
		for(int v=0; v<=node; v++) {
			if(a[u][v]==1) {
				if(visited[v]==0) {
					traverse(v, visited, node, a);
				}
			}
		}
		
	}
	
	/**
	 * @param a - matrix
	 * @return boolean value for if a is connected
	 */
	public boolean isConnected(int[][]a) {
		boolean connected=true;
		int node = a[0].length-1;
		int[] visited = new int[node+1];	
		traverse(0, visited, node, a);
		for(int i=0; i<=node; i++) {
			if(visited[i]==0) {
				connected=false;
			}
		}
		return connected;
	}
	
	
	/**
	 * @param n - number of vertices in graph
	 * @param graphs - the list of unlabeled graphs corresponding to n vertices
	 * @param zeta - random decimal between 0 and 1
	 * @param map - map used to keep track of partitions and their probabilities
	 * @return matrix returned from random graph algorithm
	 */
	public int[][] createMatrix1(int n, ArrayList<BigDecimal> graphs, BigDecimal zeta, HashMap<ArrayList<Integer>, BigDecimal> map){
		ArrayList<Integer> partition = rg.random(n, graphs, zeta, map);
		ArrayList<ArrayList<Integer>> cycle = rg.convertToCycle(rg.convertToConjugacy(partition, n));
		return createMatrixFromCycle(cycle, n);
	}
	
	
	/**
	 * @param n - number of vertices in graph
	 * @param pi - partition chosen uniformly at random
	 * @return matrix created from random partition
	 */
	public int[][] createMatrix2(int n, ArrayList<Integer> pi){
		ArrayList<ArrayList<Integer>> cycle = rg.convertToCycle(rg.convertToConjugacy(pi, n));
		return createMatrixFromCycle(cycle, n);
	}

	/**
	 * @param cycle - partition in cycle notation
	 * @param n - number of vertices in graph
	 * @return creates matrix by randomly selecting the orbits 
	 */
	
	public int[][] createMatrixFromCycle(ArrayList<ArrayList<Integer>> cycle, int n){
		int[][] a = new int[n][n];
		Random ran = new Random();
		ArrayList<ArrayList<ArrayList<Integer>>> orbs = rg.orbits(cycle);
		ArrayList<ArrayList<Integer>> acceptedOrbs = new ArrayList<>();
		for(ArrayList<ArrayList<Integer>> o: orbs) {
			int r =  ran.nextInt(2);
			if(r==0) {
				for(ArrayList<Integer> sub: o) {
					acceptedOrbs.add(sub);
				}
			}
		}
		for(int i = 0; i<a.length; i++) {
			for(int j=0; j<=i; j++) {
				if(j==i) {
					a[i][j]=1;
				}
			}
		}
		for(ArrayList<Integer> o2: acceptedOrbs) {
			a[o2.get(0)-1][o2.get(1)-1] = 1;
			a[o2.get(1)-1][o2.get(0)-1] = a[o2.get(0)-1][o2.get(1)-1];
		}
		return a;
		
	}
	
	/**
	 * @param a - matrix
	 * @return m - matrix in reduced row echelon form
	 */
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

	
	/**
	 * @param m - matrix
	 * @param row1 - row 1 of matrix
	 * @param row2 - row 2 of matrix
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
	
	
	/**
	 * @param m - matrix
	 * @param s - scalar to multiply to row 1
	 * @param row1 - row 1 of matrix
	 * @param row2 - row 2 of matrix
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
	
	/**
	 * @param a - matrix
	 * @return copy - copy of matrix a
	 */
	public static int[][] copyMatrix(int[][] a){
		int[][] copy = new int[a.length][];
		for(int i=0; i<a.length; i++) {
			copy[i] = a[i].clone();
		}
		return copy;
	}
	
	
	/**
	 * @param matrix - matrix to be printed in console
	 */
	public void printMatrix(int[][] matrix){
		for (int i = 0; i < matrix.length; i++) {
	         for (int j = 0; j < matrix[i].length; j++) { 
	            System.out.print(matrix[i][j] + " ");
	         }
		}
	}

	
}
