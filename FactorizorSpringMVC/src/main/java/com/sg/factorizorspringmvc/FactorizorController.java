/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.factorizorspringmvc;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Ben Norman
 */
@Controller
public class FactorizorController {

    @RequestMapping(value = "/factorNumber", method = RequestMethod.POST)
    public String factorNumber(HttpServletRequest request, Model model){//Map<String, Object> model) {
        // A List to hold our factors
        List<Integer> factorList = new ArrayList<>();
        // A sum to help us calculate whether or not the number
        // is perfect
        int factorCount = 0;
        // Get the input from the user and convert it to an int
        String input = request.getParameter("numberToFactor");
        int numberToFactor = Integer.parseInt(input);

        // Factor the number
        for (int i = 1; i <= (numberToFactor+1)/2; i++) {
            if (numberToFactor % i == 0) {
                // i goes into numberToFactor evenly so it
                // is a factor, add it to the list and add
                // it to the sum
                factorList.add(i);
                factorCount++;
            }
        }

        // Set all the results in the model Map so they
        // are available to result.jsp
//        model.put("numberToFactor", numberToFactor);
//        model.put("factors", factorList);
//        model.put("isPrime", factorCount==1);
//        model.put("isPerfect", factorCount==numberToFactor);  
        model.addAttribute("numberToFactor", numberToFactor);
        model.addAttribute("factors", factorList);
        model.addAttribute("isPrime", factorCount==1);
        model.addAttribute("isPerfect", factorCount==numberToFactor);
        return "result";
    }
}
