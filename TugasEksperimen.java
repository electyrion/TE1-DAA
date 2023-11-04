import java.util.Arrays;

public class TugasEksperimen {

    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        int[] arr = { -1, -2, -5, 70, 23, 25, 33, 8, 9, 10, 45, 11, 60, 27 };

        // read array from file
        int[] randomArr9 = readArrayFromFile("random512.txt");
        int[] randomArr13 = readArrayFromFile("random8192.txt");
        int[] randomArr16 = readArrayFromFile("random65536.txt");
       
        int[] sortedArr9 = readArrayFromFile("sorted512.txt");
        int[] sortedArr13 = readArrayFromFile("sorted8192.txt");
        int[] sortedArr16 = readArrayFromFile("sorted65536.txt");
       
        int[] reversedArr9 = readArrayFromFile("reversed512.txt");
        int[] reversedArr13 = readArrayFromFile("reversed8192.txt");
        int[] reversedArr16 = readArrayFromFile("reversed65536.txt");

        int[] selectedArr = sortedArr16;

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
        // mergeSort(selectedArr, 0, selectedArr.length - 1);
        twoPivotBlockQuickSort(selectedArr, 0, selectedArr.length - 1);
        long endTime = System.nanoTime();
        long afterUsedMemory = runtime.totalMemory() - runtime.freeMemory();

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

    static void twoPivotBlockQuickSort(int[] arr, int left, int right) {
        if (left < right) {
            int[] pivots = twoPivotBlockPartition(arr, left, right);

            twoPivotBlockQuickSort(arr, left, pivots[0] - 1);
            if (arr[pivots[0]] != arr[pivots[1]]) {
                twoPivotBlockQuickSort(arr, pivots[0] + 1, pivots[1] - 1);
            }
            twoPivotBlockQuickSort(arr, pivots[1] + 1, right);
        }
    }

    public static int[] twoPivotBlockPartition(int[] A, int left, int right) {
        if (A[left] > A[right])
            swap(A, left, right);
        
        int p = A[left];
        int q = A[right];
        int B = 1024; // block size
        int[] block = new int[B];
        int i = left + 1;
        int j = left + 1;
        int k = left + 1;
        int numLessThanP = 0;
        int numLessThanOrEqualQ = 0;

        while (k < right) {
            int t = Math.min(B, right - k);
            for (int c = 0; c < t; c++) {
                block[numLessThanOrEqualQ] = c;
                numLessThanOrEqualQ += (q >= A[k + c] ? 1 : 0);
            }

            for (int c = 0; c < numLessThanOrEqualQ; c++) {
                swap(A, j+c, k+block[c]);
                int temp = A[j + c];
                A[j + c] = A[k + block[c]];
                A[k + block[c]] = temp;
            }
            k += t;

            for (int c = 0; c < numLessThanOrEqualQ; c++) {
                block[numLessThanP] = c;
                numLessThanP += (p > A[j + c] ? 1 : 0);
            }

            for (int c = 0; c < numLessThanP; c++) {
                int temp = A[i];
                A[i] = A[j + block[c]];
                A[j + block[c]] = temp;
                i++;
            }
            j += numLessThanOrEqualQ;
            numLessThanP = 0;
            numLessThanOrEqualQ = 0;
        }

        int temp = A[i - 1];
        A[i - 1] = A[left];
        A[left] = temp;

        temp = A[j];
        A[j] = A[right];
        A[right] = temp;

        int[] result = {i - 1, j};
        return result;
    }
    
    static int[] partition(int[] arr, int left, int right) {
        if (arr[left] > arr[right])
            swap(arr, left, right);

        int p = arr[left];
        int q = arr[right];

        int i = left + 1;
        int j = right - 1;
        int k = left + 1;

        while (k <= j) {
            if (arr[k] < p)
                swap(arr, k, i++);
            else if (arr[k] >= q) {
                while (arr[j] > q && k < j)
                    j--;
                swap(arr, k, j--);
                if (arr[k] < p)
                    swap(arr, k, i++);
            }
            k++;
        }

        i--;
        j++;

        swap(arr, left, i);
        swap(arr, right, j);

        return new int[] { i, j };
    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp; 
    }

    static int[] readArrayFromFile(String filename) {
        try {
            java.io.File file = new java.io.File(filename);
            java.util.Scanner input = new java.util.Scanner(file);
            String[] arrStr = input.nextLine().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
            int[] arr = new int[arrStr.length];
            for (int i = 0; i < arrStr.length; i++)
                arr[i] = Integer.parseInt(arrStr[i]);
            input.close();
            return arr;
        } catch (java.io.IOException ex) {
            System.out.println("File not found.");
            return null;
        }
    }

    static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++)
            if (arr[i] > arr[i + 1])
                return false;
        return true;
    }

    static int[] generateRandomArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++)
            arr[i] = (int) (Math.random() * Math.pow(2, 16));
        return arr;
    }

    static int[] generateSortedArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++)
            arr[i] = i;
        return arr;
    }

    static int[] generateReverseSortedArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++)
            arr[i] = n - i;
        return arr;
    }

}