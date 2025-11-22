public class BubbleSort extends Sorter {
    @Override
    public void sort() {
        int[] arr = getArray();
        int n = arr.length;
        
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            // If no swapping occurred, the array is already sorted
            if (!swapped) {
                break;
            }
        }
    }
}