/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod2.dvdlibrary.model;

/**
 *
 * @author Ben Norman
 * 
 *  Motion Picture Association of America Rating System
 **/
public enum Mpaar {
    /** G - General Audiences – all ages admitted **/
    G,
    /**PG - Parental Guidance Suggested – some material may not be suitable for children **/
    PG,
    /** PG13 - Parents Strongly Cautioned – some material may be inappropriate for children under 13 **/
    PG13,
    /** R - Restricted – under 17 requires accompanying parent or adult guardian **/
    R,
    /** NC17 - AdultsOnly - No One 17 and Under Admitted. **/
    NC17,
    /** NRUR- Not Rated / Unrated**/
    NRUR;
}