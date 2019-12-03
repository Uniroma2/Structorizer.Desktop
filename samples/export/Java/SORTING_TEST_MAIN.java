// Generated by Structorizer 3.30-03 
//  
// Copyright (C) 2019-10-02 Kay Gürtzig 
// License: GPLv3-link 
// GNU General Public License (V 3) 
// https://www.gnu.org/licenses/gpl.html 
// http://www.gnu.de/documents/gpl.de.html 
//  

import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.*;
/**
 * Creates three equal arrays of numbers and has them sorted with different sorting algorithms
 * to allow performance comparison via execution counting ("Collect Runtime Data" should
 * sensibly be switched on).
 * Requested input data are: Number of elements (size) and filing mode.
 */
public class SORTING_TEST_MAIN {



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// =========== START PARALLEL WORKER DEFINITIONS ============ 
		class Worker247e34b9_0 implements Callable<Object[]> {
			private int[] values1;
			public Worker247e34b9_0(int[] values1) {
				this.values1 = values1;
			}
			public Object[] call() throws Exception {
				bubbleSort(values1);
				Object[] results = new Object[]{};
				return results;
			}
		};
		
		class Worker247e34b9_1 implements Callable<Object[]> {
			private ???[] values2;
			private ??? elementCount;
			public Worker247e34b9_1(???[] values2, ??? elementCount) {
				this.values2 = values2;
				this.elementCount = elementCount;
			}
			public Object[] call() throws Exception {
				quickSort(values2, 0, elementCount);
				Object[] results = new Object[]{};
				return results;
			}
		};
		
		class Worker247e34b9_2 implements Callable<Object[]> {
			private ???[] values3;
			public Worker247e34b9_2(???[] values3) {
				this.values3 = values3;
			}
			public Object[] call() throws Exception {
				heapSort(values3);
				Object[] results = new Object[]{};
				return results;
			}
		};
		// ============ END PARALLEL WORKER DEFINITIONS ============= 
		
		// TODO: Check and accomplish variable declarations: 
		???[] values3;
		???[] values2;
		int[] values1;
		??? show;
		boolean ok3;
		boolean ok2;
		boolean ok1;
		??? modus;
		??? elementCount;
		
		
		// TODO: You may have to modify input instructions, 
		//       e.g. by replacing nextLine() with a more suitable call 
		//       according to the variable type, say nextInt(). 
		
		do {
			elementCount = (new Scanner(System.in)).nextLine();
		} while (!(elementCount >= 1));
		do {
			System.out.print("Filling: 1 = random, 2 = increasing, 3 = decreasing"); modus = (new Scanner(System.in)).nextLine();
		} while (!(modus == 1 || modus == 2 || modus == 3));
		for (int i = 0; i <= elementCount-1; i += (1)) {
			switch (modus) {
			case 1:
				values1[i] = random(10000);
				break;
			case 2:
				values1[i] = i;
				break;
			case 3:
				values1[i] = -i;
				break;
			}
		}
		// Copy the array for exact comparability 
		for (int i = 0; i <= elementCount-1; i += (1)) {
			values2[i] = values1[i];
			values3[i] = values1[i];
		}

		// ========================================================== 
		// ================= START PARALLEL SECTION ================= 
		// ========================================================== 
		try {
			ExecutorService pool = Executors.newFixedThreadPool(3);
		
			// ----------------- START THREAD 0 ----------------- 
			Future<Object[]> future247e34b9_0 = pool.submit( new Worker247e34b9_0(values1) );
		
			// ----------------- START THREAD 1 ----------------- 
			Future<Object[]> future247e34b9_1 = pool.submit( new Worker247e34b9_1(values2, elementCount) );
		
			// ----------------- START THREAD 2 ----------------- 
			Future<Object[]> future247e34b9_2 = pool.submit( new Worker247e34b9_2(values3) );
		
			Object[] results;
			// ----------------- AWAIT THREAD 0 ----------------- 
			results = future247e34b9_0.get();
			// ----------------- AWAIT THREAD 1 ----------------- 
			results = future247e34b9_1.get();
			// ----------------- AWAIT THREAD 2 ----------------- 
			results = future247e34b9_2.get();
			pool.shutdown();
		}
		catch (Exception ex) { System.err.println(ex.getMessage()); ex.printStackTrace(); }
		// ========================================================== 
		// ================== END PARALLEL SECTION ================== 
		// ========================================================== 

		ok1 = testSorted(values1);
		ok2 = testSorted(values2);
		ok3 = testSorted(values3);
		if (! ok1 || ! ok2 || ! ok3) {
			for (int i = 0; i <= elementCount-1; i += (1)) {
				if (values1[i] != values2[i] || values1[i] != values3[i]) {
					System.out.println(("Difference at [") + (i) + ("]: ") + (values1[i]) + (" <-> ") + (values2[i]) + (" <-> ") + (values3[i]));
				}
			}
		}
		do {
			System.out.print("Show arrays (yes/no)?"); show = (new Scanner(System.in)).nextLine();
		} while (!(show == "yes" || show == "no"));
		if (show == "yes") {
			for (int i = 0; i <= elementCount - 1; i += (1)) {
				System.out.println(("[") + (i) + ("]:\t") + (values1[i]) + ("\t") + (values2[i]) + ("\t") + (values3[i]));
			}
		}
	}

	/**
	 * Implements the well-known BubbleSort algorithm.
	 * Compares neigbouring elements and swaps them in case of an inversion.
	 * Repeats this while inversions have been found. After every
	 * loop passage at least one element (the largest one out of the
	 * processed subrange) finds its final place at the end of the
	 * subrange.
	 * @param values
	 */
	private static void bubbleSort(??? values) {
		// TODO: Check and accomplish variable declarations: 
		
		??? ende = length(values) - 2;
		do {
			// The index of the most recent swapping (-1 means no swapping done). 
			??? posSwapped = -1;
			for (int i = 0; i <= ende; i += (1)) {
				if (values[i] > values[i+1]) {
					??? temp = values[i];
					values[i] = values[i+1];
					values[i+1] = temp;
					posSwapped = i;
				}
			}
			ende = posSwapped - 1;
		} while (!(posSwapped < 0));
	}

	/**
	 * Given a max-heap 'heap´ with element at index 'i´ possibly
	 * violating the heap property wrt. its subtree upto and including
	 * index range-1, restores heap property in the subtree at index i
	 * again.
	 * @param heap
	 * @param i
	 * @param range
	 */
	private static void maxHeapify(??? heap, ??? i, ??? range) {
		// TODO: Check and accomplish variable declarations: 
		
		// Indices of left and right child of node i 
		??? right = (i+1) * 2;
		??? left = right - 1;
		// Index of the (local) maximum 
		??? max = i;
		if (left < range && heap[left] > heap[i]) {
			max = left;
		}
		if (right < range && heap[right] > heap[max]) {
			max = right;
		}
		if (max != i) {
			??? temp = heap[i];
			heap[i] = heap[max];
			heap[max] = temp;
			maxHeapify(heap, max, range);
		}
	}

	/**
	 * Partitions array values between indices start und stop-1 with
	 * respect to the pivot element initially at index p into smaller
	 * and greater elements.
	 * Returns the new (and final) index of the pivot element (which
	 * separates the sequence of smaller from the sequence of greater
	 * elements).
	 * This is not the most efficient algorithm (about half the swapping
	 * might still be avoided) but it is pretty clear.
	 * @param values
	 * @param start
	 * @param stop
	 * @param p
	 * @return 
	 */
	private static int partition(??? values, ??? start, ??? stop, ??? p) {
		// TODO: Check and accomplish variable declarations: 
		
		??? pivot = values[p];
		// Tausche das Pivot-Element an den start 
		values[p] = values[start];
		values[start] = pivot;
		p = start;
		// Beginning and end of the remaining unknown range 
		start = start + 1;
		stop = stop - 1;
		// Still unseen elements? 
		while (stop >= start) {
			??? seen = values[start];
			if (values[start] <= pivot) {
				// Swap pivot element with start element 
				values[p] = seen;
				values[start] = pivot;
				p = p + 1;
				start = start + 1;
			}
			else {
				// Put the found element to the end of the unknown area 
				values[start] = values[stop];
				values[stop] = seen;
				stop = stop - 1;
			}
		}
		return p;
	}

	/**
	 * Checks whether or not the passed-in array is (ascendingly) sorted.
	 * @param numbers
	 * @return 
	 */
	private static boolean testSorted(??? numbers) {
		// TODO: Check and accomplish variable declarations: 
		boolean isSorted;
		int i;
		
		
		isSorted = true;
		i = 0;
		// As we compare with the following element, we must stop at the penultimate index 
		while (isSorted && (i <= length(numbers)-2)) {
			// Is there an inversion? 
			if (numbers[i] > numbers[i+1]) {
				isSorted = false;
			}
			else {
				i = i + 1;
			}
		}
		return isSorted;
	}

	/**
	 * Runs through the array heap and converts it to a max-heap
	 * in a bottom-up manner, i.e. starts above the "leaf" level
	 * (index >= length(heap) div 2) and goes then up towards
	 * the root.
	 * @param heap
	 * @return 
	 */
	private static int buildMaxHeap(??? heap) {
		// TODO: Check and accomplish variable declarations: 
		int lgth;
		
		
		lgth = length(heap);
		for (int k = lgth / 2 - 1; k >= 0; k += (-1)) {
			maxHeapify(heap, k, lgth);
		}
		
		return 0;
	}

	/**
	 * Recursively sorts a subrange of the given array 'values´. 
	 * start is the first index of the subsequence to be sorted,
	 * stop is the index BEHIND the subsequence to be sorted.
	 * @param values
	 * @param start
	 * @param stop
	 * @return 
	 */
	private static int quickSort(??? values, ??? start, ??? stop) {
		// =========== START PARALLEL WORKER DEFINITIONS ============ 
		class Worker7fed1521_0 implements Callable<Object[]> {
			private ??? values;
			private ??? start;
			private ??? p;
			public Worker7fed1521_0(??? values, ??? start, ??? p) {
				this.values = values;
				this.start = start;
				this.p = p;
			}
			public Object[] call() throws Exception {
				// Sort left (lower) array part 
				quickSort(values, start, p);
				Object[] results = new Object[]{};
				return results;
			}
		};
		
		class Worker7fed1521_1 implements Callable<Object[]> {
			private ??? values;
			private ??? p;
			private ??? stop;
			public Worker7fed1521_1(??? values, ??? p, ??? stop) {
				this.values = values;
				this.p = p;
				this.stop = stop;
			}
			public Object[] call() throws Exception {
				// Sort right (higher) array part 
				quickSort(values, p+1, stop);
				Object[] results = new Object[]{};
				return results;
			}
		};
		// ============ END PARALLEL WORKER DEFINITIONS ============= 
		
		// TODO: Check and accomplish variable declarations: 
		
		// At least 2 elements? (Less don't make sense.) 
		if (stop >= start + 2) {
			// Select a pivot element, be p its index. 
			// (here: randomly chosen element out of start ... stop-1) 
			??? p = random(stop-start) + start;
			// Partition the array into smaller and greater elements 
			// Get the resulting (and final) position of the pivot element 
			// Partition the array into smaller and greater elements 
			// Get the resulting (and final) position of the pivot element 
			p = partition(values, start, stop, p);
			// Sort subsequances separately and independently ... 

			// ========================================================== 
			// ================= START PARALLEL SECTION ================= 
			// ========================================================== 
			try {
				ExecutorService pool = Executors.newFixedThreadPool(2);
			
				// ----------------- START THREAD 0 ----------------- 
				Future<Object[]> future7fed1521_0 = pool.submit( new Worker7fed1521_0(values, start, p) );
			
				// ----------------- START THREAD 1 ----------------- 
				Future<Object[]> future7fed1521_1 = pool.submit( new Worker7fed1521_1(values, p, stop) );
			
				Object[] results;
				// ----------------- AWAIT THREAD 0 ----------------- 
				results = future7fed1521_0.get();
				// ----------------- AWAIT THREAD 1 ----------------- 
				results = future7fed1521_1.get();
				pool.shutdown();
			}
			catch (Exception ex) { System.err.println(ex.getMessage()); ex.printStackTrace(); }
			// ========================================================== 
			// ================== END PARALLEL SECTION ================== 
			// ========================================================== 

		}
		
		return 0;
	}

	/**
	 * Sorts the array 'values´ of numbers according to he heap sort
	 * algorithm
	 * @param values
	 * @return 
	 */
	private static int heapSort(??? values) {
		// TODO: Check and accomplish variable declarations: 
		int heapRange;
		
		
		buildMaxHeap(values);
		heapRange = length(values);
		for (int k = heapRange - 1; k >= 1; k += (-1)) {
			heapRange = heapRange - 1;
			// Swap the maximum value (root of the heap) to the heap end 
			??? maximum = values[0];
			values[0] = values[heapRange];
			values[heapRange] = maximum;
			maxHeapify(values, 0, heapRange);
		}
		
		return 0;
	}

}
