package com.ankita.weather;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void city_empty_check() {
        assertFalse("",false);
    }

    @RunWith(JUnit4.class)
    public class StringUtilitiesTest {

        @Test
        public void addition_isCorrect() throws Exception {
            assertEquals("Hello JUnit", "Hello" + " " + "JUnit");
        }
    }
}