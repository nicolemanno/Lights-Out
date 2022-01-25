import java.util.ArrayList;	
import java.util.Iterator;

public class Partition {

	int[] dp = new int[200];

	/**
	 * 
	 * @param idx - index
	 * @return innerList for bigList
	 */
	public ArrayList<Integer> addToList(int idx) {

		ArrayList<Integer> innerList = new ArrayList<>();
		for (int i = 1; i < idx; i++) {
			innerList.add(dp[i]);
		}
		return innerList;

	}

	/**
	 * @param n       - number partitioning
	 * @param maxVal
	 * @param idx
	 * @param count
	 * @param bigList
	 * @return bigList containing all partitions for n
	 */
	public ArrayList<ArrayList<Integer>> uniquePartitions(int n, int maxVal, int idx, int count,
			ArrayList<ArrayList<Integer>> bigList) {

		if (n == 0) {
			bigList.add(addToList(idx));
			count++;
		}
		for (int i = maxVal; i >= 1; i--) {
			if (i > n) {
				continue;
			} else if (i <= n) {
				dp[idx] = i;
				uniquePartitions(n - i, i, idx + 1, count, bigList);
			}
		}
		return bigList;

	}

	/**
	 * @param k - number partitioning
	 * @return partitionsOfK - partitions of k with no parts of size 1
	 */
	public ArrayList<ArrayList<Integer>> partitionWithNoOnes(int k) {
		ArrayList<ArrayList<Integer>> partitionList = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> partitionsOfK = uniquePartitions(k, k, 1, 0, partitionList);

		for (Iterator<ArrayList<Integer>> it = partitionsOfK.iterator(); it.hasNext();) {
			ArrayList<Integer> part = it.next();
			if (part.contains(1)) {
				it.remove();
			}
		}
		return partitionsOfK;
	}

}
