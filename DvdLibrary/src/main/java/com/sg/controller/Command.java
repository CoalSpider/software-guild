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
enum Command { // package private
    UNKNOWN(-1), ADD(1), REMOVE(2), EDIT(3), LIST(4), GET(5), SEARCH(6), EXIT(7);
    final int val;

    Command(int val) {
        this.val = val;
    }

    /** invalid ints return UNKNOWN     **/
    static Command intToCommand(int i) {
        switch (i) {
            case 1: return ADD;
            case 2: return REMOVE;
            case 3: return EDIT;
            case 4: return LIST;
            case 5: return GET;
            case 6: return SEARCH;
            case 7: return EXIT;
            default: return UNKNOWN;
        }
    }
}
