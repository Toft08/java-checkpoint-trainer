public class MultiplicationTable {
    public static void generate(int num) {
        // Loop from 1 to 10
        for (int i = 1; i <= 10; i++) {
            // Print in the format "num x i = result"
            System.out.println(num + " x " + i + " = " + (num * i));
        }
    }
}