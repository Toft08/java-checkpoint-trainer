import java.util.*;

public class TopFrequents {

    public List<Integer> findTopKFrequent(int[] nums, int k) {
        Map<Integer, Integer> freqMap = new HashMap<>();
        Map<Integer, Integer> firstIndexMap = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
            firstIndexMap.putIfAbsent(num, i);
        }

        List<Integer> numbers = new ArrayList<>(freqMap.keySet());

        // Sort by frequency (descending), then by first occurrence
        numbers.sort((a, b) -> {
            int freqCompare = freqMap.get(b) - freqMap.get(a);
            if (freqCompare != 0) return freqCompare;
            return firstIndexMap.get(a) - firstIndexMap.get(b);
        });

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < Math.min(k, numbers.size()); i++) {
            result.add(numbers.get(i));
        }

        return result;
    }
}

// Alt solution

// public class TopFrequents {
//     public List<Integer> findTopKFrequent(int[] nums, int k) {
//         Map<Integer, Integer> freqMap = new LinkedHashMap<>();
//         for (int num : nums) {
//             freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
//         }

//         PriorityQueue<Map.Entry<Integer, Integer>> heap = new PriorityQueue<>(
//             (a, b) -> {
//                 // Higher frequency first
//                 int freqCompare = Integer.compare(b.getValue(), a.getValue());
//                 if (freqCompare == 0) {
//                     // If frequencies are equal, order by first appearance in the original array
//                     List<Integer> order = new ArrayList<>(freqMap.keySet());
//                     return Integer.compare(order.indexOf(a.getKey()), order.indexOf(b.getKey()));
//                 }
//                 return freqCompare;
//             }
//         );

//         heap.addAll(freqMap.entrySet());

//         List<Integer> result = new ArrayList<>();
//         int count = Math.min(k, heap.size());
//         for (int i = 0; i < count; i++) {
//             result.add(heap.poll().getKey());
//         }

//         return result;
//     }
// }
