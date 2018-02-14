/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.exceptions;

/**
 *
 * @author Ben Norman
 * 
 * throws when we try to edit a deleted item
 */
public class DeletedOrderException extends Exception{

    public DeletedOrderException() {
    }

    public DeletedOrderException(String message) {
        super(message);
    }

    public DeletedOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeletedOrderException(Throwable cause) {
        super(cause);
    }

    public DeletedOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
