public class NextPrime {
    public static Integer nextPrime(Integer n) {
        int next = n + 1; // start from the next integer
        while (!isPrime(next)) {
            next++;
        }
        return next;
    }

    private static boolean isPrime(int num) {
        if (num < 2) return false; // 0 and 1 are not prime
        for (int i = 2; i * i <= num; i++) { // check divisibility up to sqrt(num)
            if (num % i == 0) return false;
        }
        return true;
    }
}
