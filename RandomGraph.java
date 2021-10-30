import java.math.BigDecimal;		
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;

public class RandomGraph {
	
MatrixGenerator mg = new MatrixGenerator();
Partition part = new Partition();

/**
 * 
 * @param n - the number of vertices
 * @param graphs - the number of unlabeled graphs with n vertices
 * @return  partition - uniformly at random partition
 */
@SuppressWarnings("unchecked")
public ArrayList<Integer> random(int n, ArrayList<BigDecimal> graphs) {
	
	int k= 0;
	ArrayList<Integer> pi = new ArrayList<>();
	BigDecimal zeta = new BigDecimal(Math.random(), MathContext.DECIMAL128);
	BigDecimal probabilitySum= new BigDecimal("0");

	while((probabilitySum.max(zeta)==zeta)&&k<=n) {
		
		pi.clear();
		ArrayList<ArrayList<Integer>> totalList = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> partitionWithOnes = part.makePartitionsFromK(n, k);
		
		for(ArrayList<Integer> list: partitionWithOnes) {
			
			BigDecimal prob = computeProbability(list, n, graphs);
			probabilitySum = probabilitySum.add(prob);
			
			if(probabilitySum.max(zeta)==probabilitySum) {
				
				pi = (ArrayList<Integer>) list.clone();
				break;
			}
		}
		k++;
		totalList.clear();
	}
	return pi;
}


/**
 * 
 * @param a
 * @param b
 * @return return - lcm(a,b)
 */
public int lcm(int a,int b) {
	return a*b/gcd(a,b);
}


/**
 * 
 * @param a
 * @param b
 * @return - a mod (b)
 */
public int mod(int a, int b) {
	return ((a%b)+b)%b;
}

/**
 * 
 * @param list - the partition in cycle notation
 * @return - the orbits that are created within the cycles of list
 */
public ArrayList<ArrayList<ArrayList<Integer>>> orbitsFromSelf(ArrayList<ArrayList<Integer>> list){
    ArrayList<ArrayList<ArrayList<Integer>>> returnList = new ArrayList<>();
    for(ArrayList<Integer> l: list) {
        int size = l.size();
        if(size==2) {
            ArrayList<ArrayList<Integer>> set = new ArrayList<>();
            set.add(l);
            returnList.add(set);
        }
        else if(size>2){
        	double value = (Math.floor((size-2)/2))+(1.0);
            for(int i=0; i<value; i++){
                ArrayList<ArrayList<Integer>> set2 = new ArrayList<>();
                int index=size;
                if((2*(i+1)==size)){
                    index=size/2;
                }
                for(int j=0; j<index; j++) {
                    ArrayList<Integer> set1 = new ArrayList<>();
                    int a = mod(j, size);
                    int b = mod(j+i+1, size);
                    set1.add(l.get(a));
                    set1.add(l.get(b));
                    set2.add(set1);
                }
                returnList.add(set2);
            }
        }
    }
    return returnList;
}

/**
 * 
 * @param cycle - partition in cycle notation
 * @return the rest of the orbits from the cycle
 */
@SuppressWarnings("unchecked")
public ArrayList<ArrayList<ArrayList<Integer>>> orbits(ArrayList<ArrayList<Integer>> cycle){
	
    ArrayList<ArrayList<ArrayList<Integer>>> set3 = orbitsFromSelf(cycle);
    ArrayList<ArrayList<Integer>> representative = (ArrayList<ArrayList<Integer>>) cycle.clone(); 
    
    for(ArrayList<Integer>l : cycle) {
    	representative.remove(l);
        if(representative.isEmpty()==true) {
            break;
        }else {
            int lena = l.size();
            for(ArrayList<Integer> r: representative) {
                int lenb = r.size();
                for(int j=0; j<gcd(lena, lenb); j++){
                    ArrayList<ArrayList<Integer>> set2 = new ArrayList<>();
                    for(int i=0; i<lcm(lena, lenb); i++) {
                        ArrayList<Integer> set1 = new ArrayList<>();
                        int a = mod(i, lena);
                        int b = mod(i+j, lenb);
                        set1.add(l.get(a));
                        set1.add(r.get(b));
                        set2.add(set1);
                    }
                    set3.add(set2);
                }
            }
        }
    }

    return set3;
}


/**
 * 
 * @param array - partition corresponding to conjugacy class
 * @return partition in cycle notation
 */
public ArrayList<ArrayList<Integer>> convertToCycle(int[] array){
	int k=1;
	int m=1;
	ArrayList<ArrayList<Integer>> cycle = new ArrayList<ArrayList<Integer>>();
	for(int i=0; i<array.length; i++) {
		for(int j=0; j<array[i]; j++) {
			ArrayList<Integer> subList = new ArrayList<Integer>();
			while(k<=i+m) {
				subList.add(k);
				k++;
			}
			m=k;
			cycle.add(subList);
		}
	}
	return cycle;
}

/**
 * @param partition - partition corresponding to n vertices
 * @param n - number of vertices
 * @param graphs - list of the numbers of unlabeled graphs
 * @return the probability corresponding to the partition 
 */
public BigDecimal computeProbability(ArrayList<Integer> partition, int n, ArrayList<BigDecimal> graphs) {
	int c = numOfCycles(partition, n);
	BigDecimal product = computeProduct(partition, n);
	BigDecimal base = new BigDecimal("2");
	BigDecimal weight = (base.pow(c)).divide(product, MathContext.DECIMAL128);
	BigDecimal g = graphs.get(n);
	BigDecimal prob = weight.divide(g, MathContext.DECIMAL128);
	return prob;
}


/**
 * @param a
 * @param b
 * @return gcd(a,b)
 */
public int gcd(int a, int b) {
	BigInteger b1 = BigInteger.valueOf(a);
	BigInteger b2 = BigInteger.valueOf(b);
	BigInteger gcd = b1.gcd(b2);
	return gcd.intValue();
}

/**
 * @param n 
 * @return the euler phi functional value for int n
 */
public ArrayList<Integer> eulerPhi(int n){
	ArrayList<Integer> euler = new ArrayList<>();
	for (int i=1; i<n+1; i++) {
		int num = 0;
		for(int j=1; j<i+1; j++) {
			if(gcd(i,j)==1) {
				num++;
			}
		}
		euler.add(num);
	}
	return euler;
}

/**
 * @param list - partition corresponding to number of vertices
 * @param n - number of vertices
 * @return conjugacy class of partition
 */
public int[] convertToConjugacy(ArrayList<Integer> list, int n) {
	int[] conjugacy = new int[n];
	for(int i=0; i<conjugacy.length; i++) {
		conjugacy[i]=0;
	}
	for(int i=0; i<list.size();i++) {
		int m = list.get(i);
		conjugacy[m-1] = conjugacy[m-1]+1;
	}
	return conjugacy;
}


/**
 * @param partition
 * @return number of parts of partition that are odd
 */
public int checkForOdd(ArrayList<Integer> partition) {
	int odd=0;
	for(int i=0; i<partition.size(); i++) {
		if(partition.get(i)%2==1) {
			odd++;
		}
	}
	return odd;
}


/**
 * @param partition - partition corresponding to n nodes
 * @param n - number of vertices
 * @return returns the product function in denominator of
 * the weight of the conjugacy class
 */
public BigDecimal computeProduct(ArrayList<Integer> partition, int n) {
	int [] rep = convertToConjugacy(partition, n);
	BigDecimal product = new BigDecimal("1");
	for(int i=0; i<rep.length; i++) {
		if(rep[i]!=0) {
			BigDecimal m = new BigDecimal(i+1);
			BigDecimal num = (m.pow(rep[i])).multiply(factorial(rep[i]));
			product = product.multiply(num);
		}
	}
	return product;
}

/**
 * @param n
 * @return n!
 */
public BigDecimal factorial(int n) {
	BigDecimal fact= new BigDecimal("1");
	for(int i=1; i<=n; i++) {
		BigDecimal newValue = new BigDecimal(i);
		fact = fact.multiply(newValue);
	}
	return fact;
}


/**
 * @param partition
 * @param n   number of vertices
 * @return c(g) defined in Dixon and Wilf 
 */
public int numOfCycles(ArrayList<Integer> partition, int n) {
	int c =0;
	int[] div = new int[n];
	ArrayList<Integer> euler = eulerPhi(n);
	for(int i=0; i<n;i++) {
		div[i]=0;
		for(int j=0; j<partition.size(); j++) {
			if(partition.get(j) % (i+1)==0) {
				div[i] = div[i]+1;
			}
		}
		int d = div[i]*div[i];
		c = c+d*euler.get(i);
	}
	int odd = checkForOdd(partition);
	c = c-odd;
	c = c/2;
	return c;
}

}
