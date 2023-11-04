import java.util.Arrays;

public class DatasetGenerator {
    public static void main(String[] args) {
        int[] sizes = { (int) Math.pow(2, 9),
                        (int) Math.pow(2, 13),
                        (int) Math.pow(2, 16), };
        for (int size : sizes) {
            
            int[] randomArr = generateRandomArray(size);
            int[] sortedArr = generateSortedArray(size);
            int[] reversedArr = generateReversedArray(size);

            // write to file
            saveToFile(randomArr, "random" + size + ".txt");
            saveToFile(sortedArr, "sorted" + size + ".txt");
            saveToFile(reversedArr, "reversed" + size + ".txt");
        }
    }

    static void saveToFile(int[] arr, String filename) {
        try {
            java.io.PrintWriter output = new java.io.PrintWriter(filename);
            output.println(Arrays.toString(arr));
            output.close();
        } catch (java.io.IOException ex) {
            System.out.println("File not found.");
        }
    }

    static int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++)
            arr[i] = (int) (Math.random() * size);
        return arr;
    }

    static int[] generateSortedArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++)
            arr[i] = i;
        return arr;
    }

    static int[] generateReversedArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++)
            arr[i] = size - i;
        return arr;
    }

    static int[] generateNearlySortedArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++)
            arr[i] = i;
        for (int i = 0; i < size / 10; i++) {
            int idx1 = (int) (Math.random() * size);
            int idx2 = (int) (Math.random() * size);
            int temp = arr[idx1];
            arr[idx1] = arr[idx2];
            arr[idx2] = temp;
        }
        return arr;
    }
}
