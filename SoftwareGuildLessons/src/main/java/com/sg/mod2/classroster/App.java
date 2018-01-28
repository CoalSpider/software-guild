/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod2.classroster;

import com.sg.mod2.classroster.controller.ClassRosterController;
import com.sg.mod2.classroster.dao.ClassRosterDao;
import com.sg.mod2.classroster.dao.ClassRosterDaoFileImpl;
import com.sg.mod2.classroster.ui.ClassRosterView;
import com.sg.mod2.classroster.ui.UserIO;
import com.sg.mod2.classroster.ui.UserIOConsoleImpl;

/**
 *
 * @author Ben Norman
 */
public class App {

    public static void main(String[] args) {
        UserIO myIo = new UserIOConsoleImpl();
        ClassRosterView myView = new ClassRosterView(myIo);
        ClassRosterDao myDao = new ClassRosterDaoFileImpl();
        ClassRosterController controller
                = new ClassRosterController(myDao, myView);
        controller.run();
    }
}
