/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.UnitTestPractice;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ben Norman
 */
public class StringTimesTest {

    StringTimes st = new StringTimes();

    /**
     * Test of stringTimes method, of class StringTimes.
     */
    @Test
    public void testStringTimes() {
        assertEquals("", st.stringTimes("Hi", 0));
        assertEquals("Hi", st.stringTimes("Hi", 1));
        assertEquals("HiHiHi", st.stringTimes("Hi", 3));
        assertEquals("", st.stringTimes("", 2));
        assertEquals("nullnull", st.stringTimes(null, 2));
        try {
            // should throw exception
            st.stringTimes("Hi", -1);
            fail();
        } catch (IllegalArgumentException e) {
            // automatic pass
        }
    }

}
