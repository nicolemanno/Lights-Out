
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Map.Entry;

public class Demo {
	
	
	/**
	 * 
	 * @param <T>
	 * @param <E>
	 * @param map
	 * @param value
	 * @return
	 */
	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
		for (Entry<T, E> entry : map.entrySet()) {
			if (Objects.equals(value, entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	/**
	 * @return the list of the number of unlabeled graphs with n vertices
	 * @throws FileNotFoundException
	 */
	public ArrayList<BigDecimal> unlabeledGraphs() throws FileNotFoundException{
		
		ArrayList<String> code = new ArrayList<>();
		ArrayList<BigDecimal> vertices = new ArrayList<>();
		Scanner s = new Scanner(new File("vertices.txt"));
		
		while(s.hasNext()) {
			code.add(s.next());
		}
		
		s.close();
		
		for(int i = 0; i<code.size(); i++) {
			if(i % 2!=0) {
				BigDecimal num = new BigDecimal(code.get(i));
				vertices.add(num);
			}
		}
		return vertices;
	}

	public static void main(String[] args) throws FileNotFoundException {
		
		Demo demo = new Demo();
		MatrixGenerator mg = new MatrixGenerator();
		
		HashMap<ArrayList<Integer>, BigDecimal> map = new HashMap<ArrayList<Integer>, BigDecimal>();
		ArrayList<BigDecimal> g = demo.unlabeledGraphs();
		int limit = 100000;
		
		System.out.println("Start");
		System.out.println("Number of Trials: " + limit);
		System.out.println();
		
		for (int n = 1; n <= 100; n++) {
			int connected = 0;
			int connInv = 0;
			int totalGraphs = 0;
			BigDecimal maxZeta = new BigDecimal("0");

			while (connected < limit) {

				totalGraphs++;

				BigDecimal zeta = new BigDecimal(Math.random(), MathContext.DECIMAL128);

				if (zeta.max(maxZeta) == zeta) {
					maxZeta = zeta;
					map.clear();
					int[][] matrix = mg.createMatrix1(n, g, zeta, map);
					if (mg.isConnected(matrix) == true) {
						connected++;
						if (mg.rowOfZeros(matrix) == false) {
							connInv++;
						}
					}

				} else {
					ArrayList<Integer> pi = new ArrayList<Integer>();
					BigDecimal sup = new BigDecimal("2");
					for (BigDecimal value : map.values()) {
						if (value.max(zeta) == value && value.max(sup) == sup) {
							sup = value;
							pi.clear();
							pi.addAll(demo.getKeyByValue(map, value));
						}
					}
					int[][] matrix = mg.createMatrix2(n, pi);
					if (mg.isConnected(matrix) == true) {
						connected++;
						if (mg.rowOfZeros(matrix) == false) {
							connInv++;
						}
					}
				}
			}
			Double d1 = new Double(connInv);
			Double d2 = new Double(totalGraphs);
			Double div = d1 / d2;

			System.out.println("Number of Vertices: " + n);
			System.out.println("Number of Total Graphs: " + totalGraphs);
			System.out.println("Number of Connected Graphs: " + connected);
			System.out.println("Number of Invertible, Connected Graphs: " + connInv);
			System.out.println("Result: " + div);
			System.out.println();
		}
		System.out.println();
		System.out.println("Done");

	}

}
