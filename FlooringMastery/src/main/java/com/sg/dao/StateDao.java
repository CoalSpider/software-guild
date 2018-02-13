/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dao;

import com.sg.dto.State;
import java.util.List;

/**
 *
 * @author Ben Norman
 */
public interface StateDao {

    /**
     * @return the list of states the company sells in
     * @throws PersistenceException if there was a issue getting a list of
     * states
     */
    List<State> getStates() throws PersistenceException;
}
