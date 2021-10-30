import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Demo {
	public static void main(String[] args) throws FileNotFoundException {
		MatrixGenerator mg = new MatrixGenerator();
		UnlabeledGraphs un = new UnlabeledGraphs();
		ArrayList<BigDecimal> g = un.unlabeledGraphs();
		int limit=1000000;
		System.out.println("Start");
		System.out.println("Number of Trials: "+limit);
		System.out.println();
		for(int n=3; n<=19; n++) {	
		int connected = 0;
		int connInv=0;
		int totalGraphs=0;
		while(connected<limit) {
			totalGraphs++;
		int [][] matrix = mg.createMatrix(n, g);

		if(mg.isConnected(matrix)==true) {
			connected++;
			if(mg.rowOfZeros(matrix)==false) {
				connInv++;
			}
		}
		}
		Double d1 = new Double(connInv);
		Double d2 = new Double(totalGraphs);
		Double div = d1/d2;
		
		System.out.println("Number of Vertices: "+n);
		System.out.println("Number of Total Graphs: "+totalGraphs);
		System.out.println("Number of Connected Graphs: "+connected);
		System.out.println("Number of Invertible, Connected Graphs: "+connInv);
		System.out.println("Result: "+div);
		System.out.println();
	}
		System.out.println();
		System.out.println("Done");

	}
}
