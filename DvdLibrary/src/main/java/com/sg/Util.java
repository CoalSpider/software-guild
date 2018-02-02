/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg;

import java.time.format.DateTimeFormatter;

/**
 *
 * @author Ben Norman
 */
public class Util {
    private static final String DATE_PATTERN = "MM/dd/yyyy";
    public static final DateTimeFormatter DATE_FORMAT =  DateTimeFormatter.ofPattern(DATE_PATTERN);
}
