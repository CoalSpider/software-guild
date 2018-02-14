/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.exceptions;

/**
 *
 * @author Ben Norman
 */
public class DuplicateOrderException extends Exception{

    public DuplicateOrderException() {
    }

    public DuplicateOrderException(String message) {
        super(message);
    }

    public DuplicateOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateOrderException(Throwable cause) {
        super(cause);
    }

    public DuplicateOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
