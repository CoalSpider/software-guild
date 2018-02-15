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
public class AlreadyDeletedException extends Exception{

    public AlreadyDeletedException() {
    }

    public AlreadyDeletedException(String message) {
        super(message);
    }

    public AlreadyDeletedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyDeletedException(Throwable cause) {
        super(cause);
    }

    public AlreadyDeletedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
