package se.kth.fpih.parsepolisen;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void tinker() {
        System.out.println("START_OF_TEST");
        runTest();
        System.out.println("END_OF_TEST");
    }

    private void runTest() {

    }
}