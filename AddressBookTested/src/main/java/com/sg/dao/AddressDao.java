/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dao;

import com.sg.dto.Address;
import java.util.List;

/**
 *
 * @author Ben Norman
 */
public interface AddressDao {

    Address addAddress(Address address);

    Address removeAddress(Address address);

    int addressCount();

    List<Address> getAllAddresses();

    Address getAddressByLastName(String lastName);
}
