/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.factorizorspringmvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Ben Norman
 */
@RestController
public class FactorizerRestController {

    @RequestMapping(value = "/factorNumberRest/{num}", method = RequestMethod.GET)
    //@ResponseBody // prevents from passing through and turning into jsp
    // view resolver ignores methods with anotation with @ResponseBody
    public FactorizerData factor(@PathVariable("num") Integer numberToFactor) {
// A List to hold our factors
        List<Integer> factorList = new ArrayList<>();
        // A sum to help us calculate whether or not the number
        // is perfect
        int factorCount = 0;
        // Get the input from the user and convert it to an int
//        String input = request.getParameter("numberToFactor");
//        int numberToFactor = Integer.parseInt(input);

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
//        Map<String,Object> returnMap = new HashMap<>();
//        returnMap.put("numberToFactor", numberToFactor);
//        returnMap.put("factors", factorList);
//        returnMap.put("isPrime", factorCount==1);
//        returnMap.put("isPerfect", factorCount==numberToFactor);  

        FactorizerData data = new FactorizerData(numberToFactor,factorList,factorCount==1,factorCount==numberToFactor);

//        return returnMap;
        return data;
    }
}
