/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod2.addressbook.dao;

import com.sg.mod2.addressbook.dto.Address;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ben Norman
 */
public class AddressDaoFileImpl implements AddressDao {
    // looks like the exercise wants to use last name as a key
    Map<String,Address> addressBook = new HashMap<>();
    
    @Override
    public Address addAddress(Address address) {
        Address add = addressBook.put(address.getLastName(), address);
        return add;
    }

    @Override
    public Address removeAddress(Address address) {
        // TODO: error w/ duplicate last names
        Address deleted = addressBook.remove(address.getLastName());
        return deleted;
    }

    @Override
    public int addressCount() {
        return addressBook.values().size();
    }

    @Override
    public List<Address> getAllAddresses() {
        return new ArrayList<>(addressBook.values());
    }

    @Override
    public Address getAddressByLastName(String lastName) {
        return addressBook.get(lastName);
    }
    
}
