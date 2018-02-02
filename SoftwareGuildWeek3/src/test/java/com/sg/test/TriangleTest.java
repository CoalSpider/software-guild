/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Ben Norman
 */
public class TriangleTest {

    Triangle triangle = new Triangle();

    /**
     * Test of checkIsTriangle method, of class Traingle.
     */
    @Test
    public void testCheckIsTriangle() {

    }

    @Test
    public void testEqTriangle() {
        assertTrue(triangle.checkIsTriangle(10f, 10f, 10f));
        assertTrue(triangle.checkIsTriangle(1f, 1f, 1f));
    }

    @Test
    public void negativeSideValid() {
        assertFalse(triangle.checkIsTriangle(-3f, 4f, 5f));
        assertFalse(triangle.checkIsTriangle(3f, -4f, 5f));
        assertFalse(triangle.checkIsTriangle(3f, 4f, -5f));
    }

    @Test
    public void zeroSideLength() {
        assertFalse(triangle.checkIsTriangle(0f, 1f, 3f));
        assertFalse(triangle.checkIsTriangle(3f, 0f, 1f));
        assertFalse(triangle.checkIsTriangle(1f, 3f, 0f));
    }

    @Test
    public void testRightTriangle() {
        // abc
        // bac
        // bca
        // acb
        // cab
        // cba
        float a = 3;
        float b = 4;
        float c = 5;
        assertTrue(triangle.checkIsTriangle(a, b, c));
        assertTrue(triangle.checkIsTriangle(b, a, c));
        assertTrue(triangle.checkIsTriangle(b, c, a));
        assertTrue(triangle.checkIsTriangle(a, c, b));
        assertTrue(triangle.checkIsTriangle(c, a, b));
        assertTrue(triangle.checkIsTriangle(c, b, a));
    }
    
    @Test
    public void isololeseTriangle(){
        float a = 2;
        float b = 1;
        assertTrue(triangle.checkIsTriangle(a, a, b));
        assertTrue(triangle.checkIsTriangle(a, b, a));
        assertTrue(triangle.checkIsTriangle(b, a, a));
    }

}
