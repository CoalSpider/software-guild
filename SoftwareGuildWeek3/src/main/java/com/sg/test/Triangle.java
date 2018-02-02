/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.test;

/**
 *
 * @author Ben Norman
 */
public class Triangle {

    // Three sides form a triangle if two sides are > the third side
    public boolean checkIsTriangle(float s1, float s2, float s3) {
        return (s1 > 0 && s2 > 0 && s3 > 0) && (s1 + s2 > s3) && (s2 + s3 > s1) && (s1 + s3 > s2);
    }
}
