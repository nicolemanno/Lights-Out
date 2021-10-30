import java.io.File;		
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class UnlabeledGraphs {
	
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
	
}
