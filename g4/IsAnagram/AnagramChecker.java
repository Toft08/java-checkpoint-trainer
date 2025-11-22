import java.util.Arrays;

public class AnagramChecker {
    public boolean isAnagram(String str1, String str2) {
        // Step 1: Normalise both strings

        if (str1 == null || str2 == null) return false;

        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();

        if (str1.length() != str2.length()) return false;

        char[] arr1 = str1.toCharArray();
        char[] arr2 = str2.toCharArray();

        Arrays.sort(arr1);
        Arrays.sort(arr2);

        return Arrays.equals(arr1, arr2);
    }
}