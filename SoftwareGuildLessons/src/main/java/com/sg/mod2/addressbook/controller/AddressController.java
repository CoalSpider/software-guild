/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod2.addressbook.controller;

import com.sg.mod2.addressbook.dao.AddressDao;
import com.sg.mod2.addressbook.dto.Address;
import com.sg.mod2.addressbook.ui.AddressView;
import java.util.List;

/**
 *
 * @author Ben Norman
 */
public class AddressController {
    private final AddressDao dao;
    private final AddressView view;

    public AddressController(AddressDao dao, AddressView view) {
        this.dao = dao;
        this.view = view;
    }
    
    public void run(){
        int menuSelection = 0;
        
        loop: while(true){
            menuSelection = view.printMenuAndGetSelection();
            switch(menuSelection){
                case 1: createAddress(); break;
                case 2: deleteAddress(); break;
                case 3: listAddressWithLastName(); break;
                case 4: listAddressCount(); break;
                case 5: listAddresses(); break;
                case 6: break loop;
                default: unknownCommand();
            }
        }
      exitMessage();
    }
    
    private void createAddress(){
        view.displayCreateAddressBanner();
        Address address = view.getNewAddress();
        dao.addAddress(address);
        view.displayCreateSuccessBanner();
    }
    
    private void deleteAddress(){
        view.displayDeleteAddressBanner();
        String lastName = view.getLastNameToDelete();
        Address address = dao.getAddressByLastName(lastName);
        view.displayAddress(address);
        String confirm = view.displayDeleteConfirmBanner();
        if("y".equals(confirm)){
            dao.removeAddress(address);
            view.displayDeleteSuccessBanner();   
        } else {
            view.displayDeleteCancelBanner();
        }
    }
    
    private void listAddresses(){
        view.displayListAddressesBanner();
        List<Address> list = dao.getAllAddresses();
        for(Address a : list){
            view.displayAddress(a);
        }
        view.displayContinueBanner();
    }
    
    private void listAddressCount(){
       view.displayAddressCount(dao.addressCount());
    }
    
    private void listAddressWithLastName(){
        view.displayFindAddressMenuBanner();
        String name = view.getLastNameToFind();
        Address address = dao.getAddressByLastName(name);
        view.displayAddress(address);
        view.displayContinueBanner();
    }
    
    private void unknownCommand(){
        view.printUnknownCommandBanner();
    }
    
    private void exitMessage(){
        view.displayExitBanner();
    }
}
