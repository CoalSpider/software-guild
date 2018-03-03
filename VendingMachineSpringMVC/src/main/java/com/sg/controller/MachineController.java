/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.controller;

import com.sg.dao.PersistanceException;
import com.sg.model.VendableItem;
import com.sg.service.ServiceFileImpl;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Ben Norman
 */
@Controller
public class MachineController {

    @Inject
    private ServiceFileImpl service;

    @GetMapping("/")
    public String getMainPage(Model model) {
        // Get all the Contacts from the DAO
        List<VendableItem> items = new ArrayList<>();
        try {
            items.addAll(service.getAllItems());
        } catch (PersistanceException ex) {
            // swallow nothing we can do right now
        }

        // Put the List of Contacts on the Model
        model.addAttribute("items", items);

        return "machine";
    }
}
