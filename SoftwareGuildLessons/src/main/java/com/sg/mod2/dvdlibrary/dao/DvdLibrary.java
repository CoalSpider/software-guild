/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod2.dvdlibrary.dao;

import com.sg.mod2.dvdlibrary.model.Dvd;
import java.util.List;

/**
 *
 * @author Ben Norman
 */
public interface DvdLibrary {

    /**
     * Adds the given Dvd to the library and associates it with the given title
     *
     * @param title title the given Dvd is associated with
     * @param dvd Dvd to be added to the library
     * @return the Dvd object previously associated with the given student id if
     * it exists, null otherwise
     */
    Dvd addDvd(String title, Dvd dvd) throws DvdLibraryException;

    /**
     * Removes from the library the Dvd associated with the given title.
     *
     * @param title title of dvd to be removed
     * @return Dvd object that was removed or null if no Dvd was associated with
     * the given Dvd title
     */
    Dvd removeDvd(String title) throws DvdLibraryException;

    /**
     * Edits the details of the Dvd associated with the given title.
     *
     * @param title title of the Dvd to edit
     * @param newData container for Dvds replacement data
     * @return the Dvd that was edited 
     *
     */
    Dvd editDvd(String title, Dvd newData) throws DvdLibraryException;

    /**
     * @return list of Dvds in the library
     */
    List<Dvd> listDvds() throws DvdLibraryException;

    /**
     * @return list of Dvd titles in the library*
     */
    List<String> listTitles() throws DvdLibraryException;

    /**
     * @param title Title of the Dvs to retrieve
     * @return the Dvd object associated with the given Dvd title, null if no
     * such Dvd exists
     */
    Dvd getDvd(String title) throws DvdLibraryException;

    // Idea from Blake Edwards to include a search
    /**
     * @param search the string to search for
     * @return a list of Dvds whos data contains the given string *
     */
    List<Dvd> searchForDvd(String search) throws DvdLibraryException;
}
