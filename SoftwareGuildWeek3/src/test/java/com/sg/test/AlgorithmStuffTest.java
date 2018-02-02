/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.test;

import static org.eclipse.jetty.util.log.Log.ignore;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Ben Norman
 */
public class AlgorithmStuffTest {

    AlgorithmStuff ast = new AlgorithmStuff();

    @Test
    public void works() {
        try {
            double[] resultA = ast.someMethod(1, -4, 3);
            double[] resultB = ast.someMethod(2, 10, 3);
            double[] resultC = ast.someMethod(1,-5,-6);
            assertTrue(resultA[0]==4.0d);
            assertTrue(resultA[1]==-0.0d);
            assertTrue(resultB[0]==16.5d);
            assertTrue(resultB[1]==-21.5d);
            
            assertTrue(resultC[0]==27.0d);
            assertTrue(resultC[1]==-22.0d);
        } catch (IllegalArgumentException | ArithmeticException e) {
            fail();
        }
    }

    @Ignore
    public void testDeterminent() {
        try {
            ast.someMethod(1, 2, 3);
            fail();
        } catch (IllegalArgumentException e) {
            // pass
        }
        
        try{
            ast.someMethod(3, 4, 5);
            fail();
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    @Ignore
    public void aIsZeroTest() {
        try {
            ast.someMethod(0, 100, 100);
            fail();
        } catch (ArithmeticException e) {

        }
    }

}
