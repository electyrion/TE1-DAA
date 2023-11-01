import java.util.Arrays;

public class SortingAlgorithm {

    public static void main(String[] args) {
        int[] arr = new int[] { 30, 50, 40, 29, 32, 13, 31, 45, 12, 1, 47, 2 };

        // test output
        System.out.println("Goodbye World!");
        System.out.println(Arrays.toString(arr));

        // test bubble
        // System.out.println(Arrays.toString(BubbleSort(arr)));

        // test selection
        // System.out.println(Arrays.toString(SelectionSort(arr)));

        // test insertion
        // System.out.println(Arrays.toString(InsertionSort(arr)));

        // test merge

        // test quick
        System.out.println(Arrays.toString(QuickSort(arr)));
    }

    public static int[] BubbleSort(int[] arr) {
        boolean swapped;
        int temp;
        do {
            swapped = false;
            for (int i = 0; i < arr.length - 1; i++) {
                if (arr[i] > arr[i + 1]) {
                    // swap element
                    temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = true;
                }
            }
        } while (swapped);

        return arr;
    }

    public static int[] SelectionSort(int[] arr) {
        int firstUnsorted = 0;
        int minimum;
        int temp;

        // repeat (numOfElement - 1) times
        for (int i = 0; i < arr.length - 1; i++) {

            // set the first unsorted element as the minimum
            minimum = firstUnsorted;

            // for each of the unsorted elements
            for (int j = firstUnsorted; j < arr.length; j++) {
                if (arr[j] < arr[minimum]) {
                    // set element as new minimum
                    minimum = j;
                }
            }

            // swap minimum with first unsorted position
            temp = arr[firstUnsorted];
            arr[firstUnsorted] = arr[minimum];
            arr[minimum] = temp;

            firstUnsorted++;
        }

        return arr;
    }

    public static int[] InsertionSort(int[] arr) {

        int temp;

        // mark first element as sorted
        int sorted = 0;

        // for each unsorted element
        for (int i = 1; i < arr.length; i++) {
            // 'extract' the element
            temp = arr[i];

            // for j = lastSortedIndex down to 0
            for (int j = sorted; j >= 0; j--) {
                if (arr[j] > temp) {
                    // move sorted element to the right by 1
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                } else {
                    break;
                }
            }

            sorted++;
        }

        return arr;
    }

    public static int[] MergeSort(int[] arr, int aLeft, int aRight, int bLeft, int bRight) {

        // split each element into partition of size 1
        // recursively merge adjacent partitions
        // for i = leftPartIdx to rightPartIdx
        // if leftPartHeadValue <= rightPartHeadValue
        // copy leftPartHeadValue
        // else: copy rightPartHeadValue; Increase InvIdx
        // copy elements back to original array

        return arr;
    }

    public static int[] QuickSort(int[] arr) {

        int sorted = 0;
        int temp;
        int pivotIndex;
        int storeIndex;

        // for each (unsorted partition)
        for (int i = sorted; i < arr.length; i++) {
            // set first element as pivot
            pivotIndex = sorted;
            storeIndex = pivotIndex + 1;
            // for i = pivotIndex + 1 to rightmostIndex
            for (int j = storeIndex; j < arr.length; j++) {
                // if ((a[i] < a[pivot]) or (equal but 50% lucky))
                if ((arr[j] <= arr[pivotIndex])) {
                    // swap(i, storeIndex); ++storeIndex
                    temp = arr[i];
                    arr[i] = arr[storeIndex];
                    arr[storeIndex] = temp;
                }

            }
            // swap(pivot, storeIndex - 1)
            temp = arr[pivotIndex];
            arr[pivotIndex] = arr[storeIndex - 1];
            arr[storeIndex - 1] = temp;
        }

        return arr;
    }

    public static int[] CountingSort(int[] arr) {

        // create key (counting) array
        int[] arrCount = new int[1000];
        // for each element in list
        for (int i : arr) {
            arrCount[i]++;
        }

        return arr;
    }
}