/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg;

import com.sg.controller.DvdLibraryController;
import com.sg.dao.DvdLibrary;
import com.sg.dao.DvdLibraryFileImpl;
import com.sg.view.DvdLibraryView;
import com.sg.view.UserIO;
import com.sg.view.UserIOConsoleImpl;

/**
 *
 * @author Ben Norman
 */
public class App {
    public static void main(String[] args) {
        // use default System.in for io
        UserIO io = new UserIOConsoleImpl();
        DvdLibraryView view = new DvdLibraryView(io);
        DvdLibrary dao = new DvdLibraryFileImpl();
        DvdLibraryController controller = new DvdLibraryController(dao,view);
        controller.run();
    }
}
