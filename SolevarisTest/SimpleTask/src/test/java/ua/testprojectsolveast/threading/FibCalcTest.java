package ua.testprojectsolveast.threading;

import junit.framework.TestCase;
import org.junit.Test;
import ua.testprojectsolveast.threading.base.FibCalc;

/**
 * Created by Anton on 04.11.2015.
 */
public class FibCalcTest extends TestCase{


    @Test
    public void testFibonacciNumberCalc(){
        FibCalc fibCalc = new FibCalcImpl();
        assertEquals(55,fibCalc.fib(10));
        assertEquals(89,fibCalc.fib(11));
        assertEquals(0,fibCalc.fib(-5));
        assertEquals(5,fibCalc.fib(5));
        assertEquals(1,fibCalc.fib(2));
        assertEquals(1,fibCalc.fib(1));
        assertEquals(0,fibCalc.fib(0));
    }
}
