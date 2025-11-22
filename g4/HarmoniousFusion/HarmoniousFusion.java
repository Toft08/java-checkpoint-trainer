public class HarmoniousFusion {
    public int[] merge(int[] arr1, int[] arr2) {
        int n1 = arr1.length;
        int n2 = arr2.length;
        int[] merged = new int[n1 + n2];

        int i = 0, j = 0, k = 0;

        while (i < n1 && j < n2) {
            if (arr1[i] <= arr2[j]) {
                merged[k++] = arr1[i++];
            } else {
                merged[k++] = arr2[j++];
            }
        }
        while (i < n1) {
            merged[k++] = arr1[i++];
        }
        while (j < n2) {
            merged[k++] = arr2[j++];
        }
        return merged;
    }
}

// Alt solution:

// import java.util.Arrays;

// public class HarmoniousFusion {
//     public int[] merge(int[] arr1, int[] arr2) {
//         if (arr1 == null) return arr2 == null ? new int[0] : arr2.clone();
//         if (arr2 == null) return arr1.clone();

//         // Step 1: create a new array big enough for both
//         int[] merged = new int[arr1.length + arr2.length];

//         // Step 2: copy arr1 into merged
//         for (int i = 0; i < arr1.length; i++) {
//             merged[i] = arr1[i];
//         }

//         // Step 3: copy arr2 into merged (start after arr1 ends)
//         for (int i = 0; i < arr2.length; i++) {
//             merged[arr1.length + i] = arr2[i];
//         }

//         // Step 4: sort the result
//         Arrays.sort(merged);

//         return merged;
//     }
// }