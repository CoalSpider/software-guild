/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.ui;

import com.sg.dto.Address;

/**
 *
 * @author Ben Norman
 */
public class AddressView {

    UserIO io;
    
    public AddressView(UserIO io) {
        this.io = io;
    }
    
    public int printMenuAndGetSelection() {
        /**
         * 1. Add Address 2. Delete Address 3. Find Address 4. List Address
         * Count 5. List All Addresses*
         */
        io.print("Main Menu");
        io.print("1. Add Address");
        io.print("2. Delete Address");
        io.print("3. Find Address");
        io.print("4. List Address Count");
        io.print("5. List All Addresses");
        io.print("6. Exit");
        
        return io.readInt("Please select from the above choices.", 1, 6);
    }
    
    
    public void displayCreateAddressBanner(){
        io.print("Add Address Menu:");
    }
    
    public Address getNewAddress(){
        String firstName = io.readString("Please Enter A First Name:");
        String lastName = io.readString("Please Enter A Last Name:");
        String streetAddress = io.readString("Please Enter A Street Address:");
        Address address = new Address(firstName,lastName,streetAddress);
        return address;
    }
    
    public void displayCreateSuccessBanner(){
        io.readString("Address successfully added. Hit enter to continue");
    }
    
    public void displayDeleteAddressBanner(){
        io.print("Delete Address Menu:");
    }
    
    public String getLastNameToDelete(){
        return io.readString("Please enter last name of address to delete:");
    }
    
    public void displayAddress(Address address){
        io.print("");
        io.print(address.getFirstName());
        io.print(address.getLastName());
        io.print(address.getStreetAddress());
        io.print("");
    }
    
    public String displayDeleteConfirmBanner(){
        return io.readString("Really delete? (y / n)");
    }
    
    public void displayDeleteSuccessBanner(){
        io.readString("Address deleted. Hit enter to continue");
    }
    
    public void displayDeleteCancelBanner(){
        io.readString("Delete canceled. Hit enter to continue");
    }
    
    public void displayListAddressesBanner(){
        io.print("List Adress Menu: ");
    }
    
    public void displayContinueBanner(){
        io.readString("Hit enter to continue");
    }
    
    public void displayAddressCount(int count){
        io.readString("There are " + count + " addresses. Hit enter to continue");
    }
    
    public void displayFindAddressMenuBanner(){
        io.print("Please enter last name of address to find:");
    }
    
    public String getLastNameToFind(){
        return io.readString("Please enter last name of address to find:");
    }
    
    public void printUnknownCommandBanner(){
        io.readString("Unknown Command. Hit enter to continue");
    }
    
    public void displayExitBanner(){
        io.print("Good Bye!");
    }
}
