import java.util.ArrayList;

public class Partition {

	int[] dp = new int[88];
	
	/**
	 * 
	 * @param idx - index
	 * @return innerList for bigList
	 */
	public ArrayList<Integer> addToList(int idx){
		
		ArrayList<Integer> innerList = new ArrayList<>();
		for(int i=1; i<idx; i++) {
			innerList.add(dp[i]);
		}
		return innerList;
		
	}
	
	/**
	 * @param n - number partitioning
	 * @param maxVal
	 * @param idx
	 * @param count
	 * @param bigList
	 * @return bigList containing all partitions for n
	 */
	public ArrayList<ArrayList<Integer>> uniquePartitions(int n, int maxVal, int idx, int count, ArrayList<ArrayList<Integer>> bigList ){

		if(n==0) {
			bigList.add(addToList(idx));
			count++;
		}
		for(int i = maxVal; i>=1; i--) {
			if(i>n) {
				continue;
			}
			else if(i <=n) {
				dp[idx]=i;
				uniquePartitions(n-i, i, idx+1, count, bigList);
			}
		}
		return bigList;
		
	}
	/**
	 * @param n - number of nodes
	 * @param k 
	 * @return partition of n with n-k parts of size 1
	 */
	public ArrayList<ArrayList<Integer>> makePartitionsFromK(int n, int k){
		
		ArrayList<ArrayList<Integer>> totalList = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> kParts = uniquePartitions(k, k, 1, 0, totalList);
		ArrayList<ArrayList<Integer>> returnList = new ArrayList<>();
		
		for(ArrayList<Integer> list: kParts) {
			if(sizeOne(list)==true) {
				continue;
			}
			else {
				ArrayList<Integer> allOnes = partitionOfAllOnes(n-k);
				for(int i: list) {
					allOnes.add(i);
				}
				returnList.add(allOnes);
				
			}
		}
		
		return returnList;
	}
	
	/**
	 * @param n
	 * @return partition of n with n parts of size 1
	 */
	public ArrayList<Integer> partitionOfAllOnes(int n){
		ArrayList<Integer> list = new ArrayList<>();
		for(int i=0; i<n; i++) {
			list.add(1);
		}
		return list;
	}

	/**
	 * @param list
	 * @return true if there exists parts of size 1 in list
	 */
	public boolean sizeOne(ArrayList<Integer> list) {
		boolean existenceOfOnes = false;
		for(int i=0; i<list.size(); i++) {
			if(list.get(i)==1) {
				existenceOfOnes= true;
				break;
			}
		}
		return existenceOfOnes;
	}

}
