/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod2.dvdlibrary.view;

import com.sg.mod2.dvdlibrary.model.Dvd;
import com.sg.mod2.dvdlibrary.model.Mpaar;
import java.util.List;

/**
 *
 * @author Ben Norman
 */
public class DvdLibraryView {

    private final UserIO io;

    public DvdLibraryView(UserIO io) {
        this.io = io;
    }

    /*
     * =========================================================================
     * Display menu methods
     * =========================================================================
     */
    public int getAndDisplayCommandOptions() {
        io.print("=== Options ===");
        io.print("1 - add a dvd");
        io.print("2 - remove dvd");
        io.print("3 - edit dvd");
        io.print("4 - list dvds");
        io.print("5 - get dvd");
        io.print("6 - exit");

        // read int returns -1 for invalid values
        return io.readInt("Please choose from the above choices", 1, 6);
    }

    /*
     * =========================================================================
     * Adding Dvd methods
     * =========================================================================
     */
    public void displayDvdAddBanner() {
        displayBanner("Add Dvd");
    }

    public String askForDvdTitle() {
        return io.readString("Enter title: ");
    }

    private void displayRatingChoices() {
        io.print("1 - G");
        io.print("2 - PG");
        io.print("3 - PG-13");
        io.print("4 - R");
        io.print("5 - NC-17");
        io.print("6 - Unrated / Not Rated");
    }

    // TODO: seperate method for editing where the previous data is shown
    public Dvd askForDvdInfo(String title) {
        String releaseDate = io.readString("Enter release date: ");

        displayRatingChoices();

        Mpaar rating = askForRatingHelper();

        String directorsName = io.readString("Enter directors name");
        String studioName = io.readString("Enter studios name");
        String note = io.readString("Add a note ");
        // create the dvd
        Dvd dvd = new Dvd(title);
        dvd.setReleaseDate(releaseDate);
        dvd.setRating(rating);
        dvd.setDirectorsName(directorsName);
        dvd.setStudio(studioName);
        dvd.setNote(note);
        return dvd;
    }

    private Mpaar askForRatingHelper() {
        Mpaar rating = null;
        do {
            int input = io.readInt("Enter rating", 1, 6);
            if (input != -1) { // if valid input
                // enum is layed out the same as user choices however
                // arrays start at 0 so we need to - 1
                rating = Mpaar.values()[input - 1];
            } else {
                io.print("Unknown rating");
            }
        } while (rating == null);
        return rating;
    }

    public void displayAddDvdSuccsess() {
        io.print("Successfully added Dvd.");
    }

    public String askToAddAnotherDvd() {
        return askToDuplicateAction("Add");
    }

    /*
     * =========================================================================
     * Removing Dvd methods
     * =========================================================================
     */
    public void displayRemoveDvdBanner() {
        displayBanner("Remove Dvd");
    }

    public String askForTitleToRemove() {
        return io.readString("Enter dvd title to remove ");
    }

    public void displayRemoveDvdSuccess() {
        io.print("Successfully Removed Dvd");
    }

    public String askToRemoveAnotherDvd() {
        return askToDuplicateAction("Remove");
    }

    /*
     * =========================================================================
     * Editing Dvd methods
     * =========================================================================
     */
    public String askToEdit() {
        return io.readString("A Dvd with that tile already exists. Edit? (y / n)");
    }

    public void displayEditDvdBanner() {
        displayBanner("Edit Dvd");
    }

    public String askForDvdTitleToEdit() {
        return io.readString("Enter dvd name to edit");
    }

    public void dislpayEditDvdMenu() {
        io.print("1 - Edit relase date");
        io.print("2 - Edit rating");
        io.print("3 - Edit directors name");
        io.print("4 - Edit studio name");
        io.print("5 - edit note");
    }

    private String askForHelper(String current, String msg) {
        return io.readString("Current " + msg + " is: " + current + ". Enter a new " + msg);
    }

    public String askForNewReleaseDate(String current) {
        return askForHelper(current, "release date");
    }

    public Mpaar askForNewRating(Mpaar currentRating) {
        io.print("Current rating is: " + currentRating.toString() + " Enter new rating");
        displayRatingChoices();
        return askForRatingHelper();
    }

    public String askForNewDirectorName(String current) {
        return askForHelper(current, "director name");
    }

    public String askForNewStudioName(String current) {
        return askForHelper(current, "studio name");
    }

    public String askForNewNote(String current) {
        return askForHelper(current, "note");
    }

    public String askToKeepEditing() {
        return io.readString("Keep Editing? (y / n)");
    }

    public void displayUnknownEditCommand() {
        io.print("Unknown edit command");
    }

    public int askForEditChoice() {
        return io.readInt("Please choose what you want to edit: ", 1, 6);
    }

    public void displayEditDvdSuccess() {
        io.print("Successfully edited dvd.");
    }

    public String askToEditAnotherDvd() {
        return askToDuplicateAction("Edit");
    }

    /*
     * =========================================================================
     * Display Single Dvd methods
     * =========================================================================
     */
    public void displayDvd(Dvd dvd) {
        // TODO: skip blank lines
        io.print("");
        io.print("Title:\t\t" + dvd.getTitle());
        io.print("Release Date:\t" + dvd.getReleaseDate());
        io.print("MPAA Rating:\t" + dvd.getRating().toString());
        io.print("Director:\t" + dvd.getDirectorsName());
        io.print("Studio:\t\t" + dvd.getStudio());
        io.print("Note\t\t" + dvd.getNote());
        io.print("");
    }

    public void displayGetDvdBanner() {
        displayBanner("Get Dvd Menu");
    }

    public String askForTitleToDisplay() {
        return io.readString("Enter dvd title: ");
    }

    public String askToGetAnotherDvd() {
        return askToDuplicateAction("Get");
    }

    /*
     * =========================================================================
     * Display all Dvd's methods
     * =========================================================================
     */
    public void displayListDvdsBanner(List<Dvd> listDvds) {
        displayBanner("Dvds");
        for (Dvd d : listDvds) {
            displayDvd(d);
        }
    }

    /*
     * =========================================================================
     * Display unkown command
     * =========================================================================
     */
    public void displayUnknownCommandBanner() {
        io.print("Unknown command");
    }

    /*
     * =========================================================================
     * Display exit banner
     * =========================================================================
     */
    public void displayExitBanner() {
        io.print("Good Bye!");
    }

    // public helper methods
    public void displayDvdNotFound(String title) {
        io.print("Dvd " + title + " not found");
    }

    public void displayEmptyLibrary() {
        io.print("---> Library is empty <----");
    }

    // private helper methods
    private void displayBanner(String txt) {
        io.print("==== " + txt + " ====");
    }

    /**
     * appends " another Dvd? (y / n)" to the given msg
     */
    private String askToDuplicateAction(String msg) {
        return io.readString(msg + " another Dvd? (y / n)");
    }

    public void displayErrorMessage(String message) {
        io.print(message);
    }
}
