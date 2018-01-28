/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod2.dvdlibrary.model;

/**
 *
 * @author Ben Norman
 */
public class Dvd {
    // required field for identification
    private String title;
    // optional fields
    private String releaseDate;
    private Mpaar rating;
    private String directorsName;
    private String studio;
    private String note;
    
    public Dvd(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Mpaar getRating() {
        return rating;
    }

    public void setRating(Mpaar rating) {
        this.rating = rating;
    }

    public String getDirectorsName() {
        return directorsName;
    }

    public void setDirectorsName(String directorsName) {
        this.directorsName = directorsName;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
