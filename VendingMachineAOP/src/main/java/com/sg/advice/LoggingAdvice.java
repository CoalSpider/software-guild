/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.advice;

import com.sg.model.VendableItem;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import org.aspectj.lang.JoinPoint;

/**
 *
 * @author Ben Norman
 */
public class LoggingAdvice {

    private static final String FILE_NAME = "errorLog.txt";

    public void createNoItemInventoryExceptionEntry(JoinPoint joinPoint) {
        writeErrorEntry(joinPoint,"NoItemInventoryException");
    }

    public void createInsufficentFundsExceptionEntry(JoinPoint joinPoint) {
        writeErrorEntry(joinPoint,"InsufficentFundsException");
    }
    
    public void writeErrorEntry(JoinPoint joinPoint, String error){
        Object[] args = joinPoint.getArgs();
        String auditEntry = "";
        for (Object currentArg : args) {
            if (currentArg instanceof String) {
                auditEntry += currentArg;
            } else if (currentArg instanceof VendableItem) {
                auditEntry += ((VendableItem) currentArg).getName();
            }
        }
        auditEntry += " : "+error;
        writeEntry(auditEntry);
    }

    public void writeEntry(String entry) {
        // auto closeable
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            LocalDateTime timestamp = LocalDateTime.now();
            out.println(timestamp.toString() + " : " + entry);
            out.flush();
        } catch (IOException e) {
            System.out.println("Could not persist error log information." + e.getMessage());
        }
    }
}
