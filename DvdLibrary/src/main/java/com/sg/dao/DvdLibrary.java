/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dao;

import com.sg.model.Dvd;
import com.sg.model.Mpaar;
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
     * @return a list of Dvds whos data contains the given string
     */
    List<Dvd> searchForDvd(String search) throws DvdLibraryException;

    /**
     * @return all dvd's released in the last X years
     */
    List<Dvd> getDvdReleasedInLast(int years) throws DvdLibraryException;

    /**
     * @return all dvd's with the given MPAA rating
     */
    List<Dvd> getDvdWithRating(Mpaar rating) throws DvdLibraryException;

    /**
     * implementation should sub sort by MPAA rating
     *
     * @return all dvd's with given director
     */
    List<Dvd> getDvdsWithDirector(String directorName) throws DvdLibraryException;

    /** @return all dvd's produced by given studio **/
    List<Dvd> getDvdWithStudio(String studio) throws DvdLibraryException;
    
    /** @return the average age of the movies in the library **/
    int getAverageAge() throws DvdLibraryException;
    
    /** @return the youngest dvd in the collection**/
    Dvd getNewestDvds() throws DvdLibraryException;
    
    /** @return the oldest dvd in the collection*/
    Dvd getOldestDvds() throws DvdLibraryException;

    /** Find the average number of notes associated with movies in your collection <---- At odds with initial requirements **/
    /** Find the average length of notes in the library**/
    int getAverageNoteLength() throws DvdLibraryException;
}
