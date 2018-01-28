/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod2.dvdlibrary.dao;

/**
 *
 * @author Ben Norman
 */
public class DvdLibraryException extends Exception{

    public DvdLibraryException() {
    }

    public DvdLibraryException(String message) {
        super(message);
    }

    public DvdLibraryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DvdLibraryException(Throwable cause) {
        super(cause);
    }

    public DvdLibraryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
