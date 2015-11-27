package ua.testprojectsolveast.threading;

import ua.testprojectsolveast.threading.base.FibCalc;

/**
 * Created by Anton on 04.11.2015.
 */
public class FibCalcImpl implements FibCalc {

    /**
     * Calc Fibanacci number, use recursion. (Can throw error - "OutOfMemoryError")
     */
    public long fibRecursion(int n) {
        long sum = 0;
        if (n <= 0) {
            /*NOP*/
        } else if (n <= 2) {
            sum = 1;
        } else {
            sum = fib(n - 1) + fib(n - 2);
        }
        return sum;
    }


    /**
     * Calc Fibanacci number, use for
     */
    @Override
    public long fib(int n) {
        long sum = 0;
        if (n <= 0) {
            return 0;
        } else if (n <= 2) {
            return 1;
        } else if (n > 2) {
            long p = 1, c = 1;
            for (long i = 2; i < n; i++) {
                sum = p + c;
                p = c;
                c = sum;
            }
        }
        return sum;
    }

}
