public class InsertionSort extends Sorter {
    @Override
    public void sort() {
        int[] arr = getArray();
        int n = arr.length;

        // Classic insertion sort (no early break)
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;

            // Shift elements that are larger than key
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }
}
