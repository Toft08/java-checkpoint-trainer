public abstract class Factorial {
    public abstract long calculate(int n);
}

class IterativeFactorial extends Factorial {
    @Override
    public long calculate(int n) {
        if (n == 0) return 1;
        long result = 1;
        for ( int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}

class RecursiveFactorial extends Factorial {
    @Override
    public long calculate(int n) {
        if (n == 0) return 1;
        return n * calculate(n - 1);
    }
}