/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.controller;

import com.sg.dao.PersistanceException;
import com.sg.model.Change;
import com.sg.model.VendableItem;
import com.sg.service.InsufficentFundsException;
import com.sg.service.ServiceFileImpl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Ben Norman
 */
@Controller
public class MachineController {

    @Inject
    private ServiceFileImpl service;

    private Integer itemSelection;
    private BigDecimal machineAmount = new BigDecimal("0.00");
    private List<VendableItem> items;
    private String changeMsg = "";
    private String purchaseMsg = "";

    @GetMapping("/")
    public String getMainPage(Model model) {
        //  Put the List of Contacts on the Model
        if (items == null) {
            try {
                // sort by number
                items = service.getAllItems()
                        .stream()
                        .sorted((itemA, itemB) -> itemA.getNum() - itemB.getNum())
                        .collect(Collectors.toList());
            } catch (PersistanceException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
        model.addAttribute("purchaseMsg", purchaseMsg);
        model.addAttribute("changeMsg", changeMsg);
        model.addAttribute("machineAmount", machineAmount.toPlainString());
        model.addAttribute("items", items);
        model.addAttribute("itemChoice", itemSelection == null ? "" : itemSelection);
        return "machine";
    }

    @PostMapping("/selectItem")
    public String selectItem(Integer num) {
        itemSelection = num;
        return "redirect:/";
    }

    private void addMoney(BigDecimal amount) {
        machineAmount = machineAmount.add(amount).setScale(2, RoundingMode.HALF_UP);
    }

    @PostMapping("/addDollar")
    public String addDollar() {
        addMoney(new BigDecimal("1.00"));
        return "redirect:/";
    }

    @PostMapping("/addQuater")
    public String addQuater() {
        addMoney(new BigDecimal("0.25"));
        return "redirect:/";
    }

    @PostMapping("/addDime")
    public String addDime() {
        addMoney(new BigDecimal("0.10"));
        return "redirect:/";
    }

    @PostMapping("/addNickel")
    public String addNickel() {
        addMoney(new BigDecimal("0.05"));
        return "redirect:/";
    }

    @PostMapping("/makePurchase")
    public String makePurchase() {
        if (itemSelection == null) {
            purchaseMsg = "Please Select An Item";
            return "redirect:/";
        }
        
        // the item is known to exist
        VendableItem item = items.stream()
                .filter((i) -> i.getNum() == itemSelection)
                .findFirst().get();
        
        if (item.getQuantity() <= 0) {
            purchaseMsg = "Sold Out!";
            return "redirect:/";
        }
        try {
            BigDecimal moneyLeft = service.vendItem(machineAmount, item);
            // convert to coinage
            Change change = new Change(moneyLeft);
            changeMsg = change.toString();
            machineAmount = new BigDecimal("0.00");
            purchaseMsg = "Thank You!";
            itemSelection = null;
        } catch (InsufficentFundsException ex) {
            purchaseMsg = "Please Deposit: " + item.getPrice().subtract(machineAmount).toPlainString();
        } catch (PersistanceException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("/returnChange")
    public String returnChange() {
        Change change = new Change(machineAmount);
        changeMsg = change.toString();
        machineAmount = new BigDecimal("0.00");
        itemSelection = null;
        purchaseMsg = "";
        return "redirect:/";
    }
}
