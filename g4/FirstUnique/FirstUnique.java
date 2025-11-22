import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FirstUnique {
    public char findFirstUnique(String s) {
        Map<Character, Integer> countMap = new LinkedHashMap<>();

        for (char c : s.toCharArray()) {
            countMap.put(c, countMap.getOrDefault(c, 0) + 1);
        }

        for (Map.Entry<Character, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }
        return '_';
    }
}

// Alt solution:

// public class FirstUnique {
//     public char findFirstUnique(String s) {
//         Map<Character, Integer> countMap = new HashMap<>();

//         for (int i = 0; i < s.length(); i++) {
//             char c = s.charAt(i);
//             countMap.put(c, countMap.getOrDefault(c, 0) + 1);
//         }

//         for (int i = 0; i < s.length(); i++) {
//             char c = s.charAt(i);
//             if( countMap.get(c) == 1) {
//                 return c;
//             }
//         }
//         return '_';
//     }
// }