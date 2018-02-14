/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.controller;

/**
 *
 * @author Ben Norman
 */
public enum Command {
    UNKNOWN(-1), DISPLAY(1), ADD(2), EDIT(3), REMOVE(4), SAVE(5), QUIT(6);
    private int val;

    Command(int val) {
        this.val = val;
    }
    
    public int getVal(){
        return val;
    }

    public static Command commandFromInt(int val){
        switch(val){
            case 1: return DISPLAY;
            case 2: return ADD;
            case 3: return EDIT;
            case 4: return REMOVE;
            case 5: return SAVE;
            case 6: return QUIT;
        }
        return UNKNOWN;
    }
}
