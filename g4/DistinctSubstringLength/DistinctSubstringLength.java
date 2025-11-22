import java.util.*;

public class DistinctSubstringLength {
    public int maxLength(String s) {
        Set<Character> seen = new HashSet<>();
        int start = 0, maxLen = 0;

        for (int end = 0; end < s.length(); end++) {
            char ch = s.charAt(end);

            while (seen.contains(ch)) {
                seen.remove(s.charAt(start));
                start++;
            }

            seen.add(ch);
            maxLen = Math.max(maxLen, end - start + 1);
        }
        return maxLen;
    }
}