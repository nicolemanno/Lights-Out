import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

public class MatrixGenerator {
	
	RowReduction rre = new RowReduction();
	RandomGraph rg = new RandomGraph();
	
	/**
	 * @param a - matrix before in rre form
	 * @return boolean value of the existence of zeros 
	 */
	public boolean rowOfZeros(int[][] a) {
		
		int[][] rreA = RowReduction.rref(a);	
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
	 * @return matrix returned from random graph algorithm
	 */
	public int[][] createMatrix(int n, ArrayList<BigDecimal> graphs){
		ArrayList<Integer> partition = rg.random(n, graphs);
		ArrayList<ArrayList<Integer>> cycle = rg.convertToCycle(rg.convertToConjugacy(partition, n));
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
	
	public void printMatrix(int[][] matrix){
		for (int i = 0; i < matrix.length; i++) {
	         for (int j = 0; j < matrix[i].length; j++) { 
	            System.out.print(matrix[i][j] + " ");
	         }
		}
	}

	
}
