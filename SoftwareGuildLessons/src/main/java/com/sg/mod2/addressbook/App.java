/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod2.addressbook;

import com.sg.mod2.addressbook.controller.AddressController;
import com.sg.mod2.addressbook.dao.AddressDao;
import com.sg.mod2.addressbook.dao.AddressDaoFileImpl;
import com.sg.mod2.addressbook.ui.AddressView;
import com.sg.mod2.addressbook.ui.UserIO;
import com.sg.mod2.addressbook.ui.UserIOConsoleImpl;

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
