/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod2.dvdlibrary.controller;

import com.sg.mod2.dvdlibrary.dao.DvdLibrary;
import com.sg.mod2.dvdlibrary.model.Dvd;
import com.sg.mod2.dvdlibrary.view.DvdLibraryView;

import static com.sg.mod2.dvdlibrary.controller.Command.*;
import com.sg.mod2.dvdlibrary.dao.DvdLibraryException;

/**
 *
 * @author Ben Norman
 */
public class DvdLibraryController {

    private final DvdLibrary dao;
    private final DvdLibraryView view;

    /**
     * -1 for no duplicate command, otherwise == the command*
     */
    private Command dupeCommand = UNKNOWN;

    public DvdLibraryController(DvdLibrary dao, DvdLibraryView view) {
        this.dao = dao;
        this.view = view;
    }

    public void run() {
        try {
            Command command;
            loop:
            while (true) {
                // if the user didnt choose to run the same command agian
                command = (dupeCommand == UNKNOWN) ? getAndDisplayCommandOptions() : dupeCommand;
                // if the library is empty we want to let the user know before they
                // try entering a title to remove/edit/get/list something
                if(command == REMOVE || command == EDIT || command == GET || command == LIST){
                    if(dao.listDvds().isEmpty()){
                        view.displayEmptyLibrary();
                        continue;
                    }
                }
                switch (command) {
                    case ADD:
                        addDvd();
                        break;
                    case REMOVE:
                        removeDvd();
                        break;
                    case EDIT:
                        editDvd();
                        break;
                    case LIST:
                        listDvds();
                        break;
                    case GET:
                        getDvd();
                        break;
                    case EXIT:
                        break loop;
                    default:
                        displayUnknownCommandBanner();
                }
            }
            displayExitBanner();
        } catch (DvdLibraryException dle) {
            view.displayErrorMessage(dle.getMessage());
        }
    }

    /**
     * if they user entered a integer return it else return -1*
     */
    private Command getAndDisplayCommandOptions() {
        return intToCommand(view.getAndDisplayCommandOptions());
    }

    private boolean dvdTitleExists(String title) throws DvdLibraryException {
        return dao.listTitles().contains(title);
    }

    private void addDvd() throws DvdLibraryException {
        view.displayDvdAddBanner();
        String title = view.askForDvdTitle();

        // if the dvd already exists with that title check to see
        // if the user wants to edit the dvd
        if (dvdTitleExists(title)) {
            // if user wants to edit goto edit command else return to menu
            dupeCommand = "y".equals(view.askToEdit()) ? EDIT : UNKNOWN;
            return;
        }

        Dvd dvd = view.askForDvdInfo(title);
        dao.addDvd(dvd.getTitle(), dvd);
        view.displayAddDvdSuccsess();

        dupeCommand = "y".equals(view.askToAddAnotherDvd()) ? ADD : UNKNOWN;

    }

    private void removeDvd() throws DvdLibraryException {
        view.displayRemoveDvdBanner();
        
        String title = view.askForTitleToRemove();

        // removeDvd returns null if the dvd didnt exist
        if (dao.removeDvd(title) == null) {
            view.displayDvdNotFound(title);
        } else {
            view.displayRemoveDvdSuccess();
        }

        dupeCommand = "y".equals(view.askToRemoveAnotherDvd()) ? REMOVE : UNKNOWN;
    }

    private void editDvd() throws DvdLibraryException {
        view.displayEditDvdBanner();
        
        String title = view.askForDvdTitleToEdit();

        if (dvdTitleExists(title) == false) {
            view.displayDvdNotFound(title);
        } else {
            Dvd dvd = dao.getDvd(title);
            view.dislpayEditDvdMenu();

            int editChoice;
            do {
                editChoice = view.askForEditChoice();
                editCommandHelper(dvd, editChoice);
            } while ("y".equals(view.askToKeepEditing()));

            dao.addDvd(title, dvd);
            view.displayEditDvdSuccess();
        }

        dupeCommand = "y".equals(view.askToEditAnotherDvd()) ? EDIT : UNKNOWN;
    }

    private void editCommandHelper(Dvd dvd, int userChoice) {
        switch (EditCommand.fromInt(userChoice)) {
            case RELEASE_DATE:
                dvd.setReleaseDate(view.askForNewReleaseDate(dvd.getReleaseDate()));
                break;
            case RATING:
                dvd.setRating(view.askForNewRating(dvd.getRating()));
                break;
            case DIRECTOR:
                dvd.setDirectorsName(view.askForNewDirectorName(dvd.getDirectorsName()));
                break;
            case STUDIO:
                dvd.setStudio(view.askForNewStudioName(dvd.getStudio()));
                break;
            case NOTE:
                dvd.setNote(view.askForNewNote(dvd.getNote()));
                break;
            default:
                view.displayUnknownEditCommand();
        }
    }

    private void listDvds() throws DvdLibraryException {
        view.displayListDvdsBanner(dao.listDvds());
    }

    private void getDvd() throws DvdLibraryException {
        view.displayGetDvdBanner();
        
        String title = view.askForTitleToDisplay();

        if (dvdTitleExists(title) == false) {
            view.displayDvdNotFound(title);
        } else {
            view.displayDvd(dao.getDvd(title));
        }

        dupeCommand = "y".equals(view.askToGetAnotherDvd()) ? GET : UNKNOWN;
    }

    private void displayUnknownCommandBanner() {
        view.displayUnknownCommandBanner();
    }

    private void displayExitBanner() {
        view.displayExitBanner();
    }

    private void searchForDvd(String search) {
        // TODO:
    }
}
