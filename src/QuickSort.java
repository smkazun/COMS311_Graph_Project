import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author Sebastian Kazun
 *
 */
public class QuickSort {
	
	public QuickSort() {
		
	}
	
	public void quicksort(List<CommunicationTriple> list) {
		quickSortRec(list, 0,  list.size() - 1);
	}
	
	
	private void quickSortRec(List<CommunicationTriple> list, int start, int end) {
		
		if(start >= end) {
			return;
		}
		
		int p = randomizedPartition(list, start, end);
		quickSortRec(list, start, p - 1);
		quickSortRec(list, p + 1, end);
		
	}
	
	/**
	 * Rearranges the array based on non-decreasing timestamp
	 * 
	 * @param list the list to partition
	 * @param start the first index of the list
	 * @param end the last index of the list
	 * @return a pivot value
	 */
	private int partition(List<CommunicationTriple> list, int start, int end) {
		
		int pivot = list.get(end).getTimestamp();
		int i = start - 1;
		
		for(int j = start; j < end; j++) {
			
			if(list.get(j).getTimestamp() <= pivot) {
				i++;
				Collections.swap(list, i, j);
			}
			
		}
		Collections.swap(list, i+1, end);
		
		return i + 1;
		
	}
	
	private int randomizedPartition(List<CommunicationTriple> list, int start, int end) {
		
		Random rand = new Random();
		int r = start + rand.nextInt(end - start + 1);
		
		Collections.swap(list, end, r);
		return partition(list, start, end);
		
	}
	
	

}
