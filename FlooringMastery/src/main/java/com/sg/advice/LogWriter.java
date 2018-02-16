/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.advice;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Ben Norman
 */
class LogWriter {
    void write(String data, String fileName) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(fileName, true));
            pw.println(data);
            pw.flush();
        } catch (IOException ex) {
            System.out.println("Could not persist logging info " + ex.getMessage());
        }
    }
}
