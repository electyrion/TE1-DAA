import java.util.Arrays;

public class TugasEksperimen {

    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        int[] arr = { -1, -2, -5, 70, 23, 25, 33, 8, 9, 10, 45, 11, 60, 27 };
        int[] randomArr = generateRandomArray((int) Math.pow(2, 16));
        int[] sortedArr = generateSortedArray((int) Math.pow(2, 16));
        int[] reverseSortedArr = generateReverseSortedArray((int) Math.pow(2, 16));

        int[] selectedArr = reverseSortedArr;

        // System.out.print("Unsorted: ");
        // System.out.println(Arrays.toString(randomArr));
        // write the initial array to a file
        try {
            java.io.PrintWriter output = new java.io.PrintWriter("input.txt");
            output.println("----------Unsorted----------");
            output.println(Arrays.toString(selectedArr));
            output.close();
        } catch (java.io.IOException ex) {
            System.out.println("File not found.");
        }

        System.gc();
        long beforeUsedMemory = runtime.totalMemory() - runtime.freeMemory();
        long startTime = System.nanoTime();
        mergeSort(selectedArr, 0, selectedArr.length - 1);
        // TwoPivotBlockPartitioning.blockPartition(selectedArr, selectedArr.length);
        long endTime = System.nanoTime();
        long afterUsedMemory = runtime.totalMemory() - runtime.freeMemory();
        
        // System.out.print("Sorted: ");
        // System.out.println(Arrays.toString(selectedArr));

        System.out.println("Time: " + (endTime - startTime) + " ns");
        System.out.println("Time: " + (endTime - startTime) / 1000000 + " ms");
        System.out.println("Memory: " + (afterUsedMemory - beforeUsedMemory) + " bytes");
        System.out.println("Memory: " + (afterUsedMemory - beforeUsedMemory) / 1000 + " KB");

        // write the result to a file
        try {
            java.io.PrintWriter output = new java.io.PrintWriter("output.txt");
            output.println("----------Sorted----------");
            output.println(Arrays.toString(selectedArr));
            output.close();
        } catch (java.io.IOException ex) {
            System.out.println("File not found.");
        }

        System.out.println(selectedArr.length);
        System.out.println("Goodbye World!");
    }

    // write a method that will generate an array of n random integers
    static int[] generateRandomArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++)
            arr[i] = (int) (Math.random() * Math.pow(2, 16));
        return arr;
    }

    // write a method that will generate an array of n sorted integers
    static int[] generateSortedArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++)
            arr[i] = i;
        return arr;
    }

    // write a method that will generate an array of n reverse sorted integers
    static int[] generateReverseSortedArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++)
            arr[i] = n - i;
        return arr;
    }

    static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            merge(arr, left, mid, right);
        }
    }

    static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int leftArr[] = new int[n1];
        int rightArr[] = new int[n2];

        for (int i = 0; i < n1; i++)
            leftArr[i] = arr[left + i];
        for (int j = 0; j < n2; j++)
            rightArr[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2)
            if (leftArr[i] <= rightArr[j])
                arr[k++] = leftArr[i++];
            else
                arr[k++] = rightArr[j++];

        while (i < n1)
            arr[k++] = leftArr[i++];

        while (j < n2)
            arr[k++] = rightArr[j++];
    }

}

class TwoPivotBlockPartitioning {
    private static final int B = 1024; // The block size.

    public static int[] blockPartition(int[] A, int n) {
        // Check if the array has more than one element.
        if (n <= 1) {
            return A;
        }

        // Choose the two pivots.
        int p = A[0];
        int q = A[n - 1];

        // Create an array to store the block indices.
        int[] block = new int[B];

        // Initialize the block indices and the counters for elements less than p and less than or equal to q.
        int i = 2;
        int j = 2;
        int k = 2;
        int numLessThanP = 0;
        int numLessThanOrEqualToQ = 0;

        // Iterate over the array, partitioning it into three blocks: elements less than p, elements less than or equal to q, and elements greater than q.;    
        while (k < n) {
            // Determine the size of the current block.
            int t = Math.min(B, n - k);

            // Iterate over the current block, counting the number of elements less than q and storing the indices of the elements in the block array.
            for (int c = 0; c < t; c++) {
                if (A[k + c] < q) {
                    block[numLessThanOrEqualToQ] = c;
                    numLessThanOrEqualToQ++;
                }
            }

            // Swap the elements less than q with the elements at the beginning of the block.
            for (int c = 0; c < numLessThanOrEqualToQ; c++) {
                swap(A, j + c, k + block[c]);
            }

            // Increment the block index.
            k += t;

            // Iterate over the current block, counting the number of elements less than p and storing the indices of the elements in the block array.
            for (int c = 0; c < numLessThanOrEqualToQ; c++) {
                if (A[j + c] < p) {
                    block[numLessThanP] = c;
                    numLessThanP++;
                }
            }

            // Swap the elements less than p with the elements at the beginning of the block.
            for (int c = 0; c < numLessThanP; c++) {
                swap(A, i, j + block[c]);
            }

            // Increment the block indices.
            i += numLessThanP;
            j += numLessThanOrEqualToQ;

            // Reset the counters for elements less than p and less than or equal to q.
            numLessThanP = 0;
            numLessThanOrEqualToQ = 0;
        }

        // Swap the pivots back to their original positions.
        swap(A, i - 1, 1);
        swap(A, j, n - 1);

        // Return the indices of the two pivots.
        return new int[]{i - 1, j};
    }

    private static void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }
}
