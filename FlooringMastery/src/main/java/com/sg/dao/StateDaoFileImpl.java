/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dao;

import com.sg.dto.State;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Ben Norman
 */
public class StateDaoFileImpl implements StateDao {

    private static final String FILE_NAME = "taxes";
    private static final String DELIMITER = ",";

    private List<State> states;

    private void readStates() throws PersistenceException {
        if (states != null) {
            return;
        }
        states = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(FILE_NAME))) {
            // skip header line
            sc.nextLine();
            while (sc.hasNext()) {
                String[] data = sc.nextLine().split(DELIMITER);
                states.add(new State(data[0], new BigDecimal(data[1])));
            }
        } catch (FileNotFoundException ex) {
            throw new PersistenceException("could not read states file " + ex.getMessage());
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            throw new PersistenceException("Error reading state data " + e.getMessage());
        }
    }

    @Override
    public List<State> getStates() throws PersistenceException {
        readStates();
        // defensive copy
        return new ArrayList<>(states);
    }
}
