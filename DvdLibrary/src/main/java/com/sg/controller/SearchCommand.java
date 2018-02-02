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
public enum SearchCommand {
    UNKNOWN(-1),RELEASED_IN(1),RATING(2),DIRECTOR(3),STUDIO(4),NEWEST(5),OLDEST(6),AVG_AGE(7),AVG_NOTE_LEN(8);
    final int val;
    SearchCommand(int val){
        this.val = val;
    }
    
    static SearchCommand fromInt(int input){
        switch(input){
            case 1: return RELEASED_IN;
            case 2: return RATING;
            case 3: return DIRECTOR;
            case 4: return STUDIO;
            case 5: return NEWEST;
            case 6: return OLDEST;
            case 7: return AVG_AGE;
            case 8: return AVG_NOTE_LEN;
            default: return UNKNOWN;
        }
    }
}
