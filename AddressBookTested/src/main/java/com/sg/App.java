/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg;

import com.sg.controller.AddressController;
import com.sg.dao.AddressDao;
import com.sg.dao.AddressDaoFileImpl;
import com.sg.ui.AddressView;
import com.sg.ui.UserIO;
import com.sg.ui.UserIOConsoleImpl;

/**
 *
 * @author Ben Norman
 */
public class App {
    public static void main(String[] args) {
        UserIO io = new UserIOConsoleImpl();
        AddressView view = new AddressView(io);
        AddressDao dao = new AddressDaoFileImpl();
        AddressController ac = new AddressController(dao,view);
        ac.run();
    }
}
