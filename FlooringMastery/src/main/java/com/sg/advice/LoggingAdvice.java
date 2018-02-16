/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.advice;

import java.time.LocalDateTime;
import org.aspectj.lang.JoinPoint;

/**
 *
 * @author Ben Norman
 */
public class LoggingAdvice {
    private static final String DELIMITER = "::";
    private final StringBuilder outputBuilder = new StringBuilder();
    
    private final String errorLogFileName;
    private final String actionLogFileName;
    private final LogWriter writer = new LogWriter();

    public LoggingAdvice(String errorLogFileName, String actionLogFileName) {
        this.errorLogFileName = errorLogFileName;
        this.actionLogFileName = actionLogFileName;
    }

    public void createErrorLogEntry(JoinPoint jp, Throwable error) {
        outputBuilder.append(LocalDateTime.now());
        outputBuilder.append(DELIMITER);
        outputBuilder.append(jp.getSignature().getName());
        outputBuilder.append(DELIMITER);
        for(Object obj : jp.getArgs()){
            outputBuilder.append(obj);
            outputBuilder.append(DELIMITER);
        }
        outputBuilder.append(DELIMITER);
        outputBuilder.append(error.getClass().getName());
        outputBuilder.append(DELIMITER);
        outputBuilder.append(error.getMessage());

        writer.write(outputBuilder.toString(), this.errorLogFileName);
        
        outputBuilder.delete(0, outputBuilder.length());
    }
    
    public void createActionLogEntry(JoinPoint jp) {
        System.out.println("created action log");
        outputBuilder.append(LocalDateTime.now());
        outputBuilder.append(DELIMITER);
        outputBuilder.append(jp.getSignature().getName());
        outputBuilder.append(DELIMITER);
        for(Object obj : jp.getArgs()){
            outputBuilder.append(obj);
            outputBuilder.append(DELIMITER);
        }

        writer.write(outputBuilder.toString(), this.actionLogFileName);
        
        outputBuilder.delete(0, outputBuilder.length());
    }
}
