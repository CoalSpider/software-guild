/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod2.dvdlibrary.controller;

/**
 *
 * @author Ben Norman
 */
enum EditCommand { // package private
    UNKNOWN(-1),RELEASE_DATE(1),RATING(2),DIRECTOR(3),STUDIO(4),NOTE(5);
    int val;
    EditCommand(int val){
        this.val = val;
    }
    
    /** invalid ints return UNKNOWN**/
    static EditCommand fromInt(int i){
        switch(i){
            case 1: return RELEASE_DATE;
            case 2: return RATING;
            case 3: return DIRECTOR;
            case 4: return STUDIO;
            case 5: return NOTE;
            default: return UNKNOWN;
        }
    }
}
