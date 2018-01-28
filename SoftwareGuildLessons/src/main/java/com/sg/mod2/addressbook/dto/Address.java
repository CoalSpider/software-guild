/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod2.addressbook.dto;

/**
 *
 * @author Ben Norman
 */
public class Address {
    private String firstName;
    private String lastName;
    private String streetAddress;
    
    public Address(){
        
    }
    
    public Address(String firstName,String lastName,String streetAddress){
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAddress = streetAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }
}
