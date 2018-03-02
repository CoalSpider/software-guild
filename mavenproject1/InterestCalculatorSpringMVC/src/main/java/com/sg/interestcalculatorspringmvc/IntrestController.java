/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.interestcalculatorspringmvc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Ben Norman
 */
@RestController
public class IntrestController {

    //CurrentBalance * (1 + (QuarterlyInterestRate / 100))
    private BigDecimal calculateQuarterlyAmount(BigDecimal currBalance, BigDecimal quarteryRate) {
        return currBalance.multiply(quarteryRate.divide(new BigDecimal("100.00"))).setScale(2, RoundingMode.HALF_UP);
    }

    @RequestMapping(value = "/calculate", method = RequestMethod.POST)
    public List<AccountYear> calculate(BigDecimal intrest, BigDecimal principle, Integer years) {
        BigDecimal quarterly = intrest.multiply(new BigDecimal(0.25));

        List<AccountYear> accountYears = new ArrayList<>();
        for (int i = 0; i < years; i++) {
            AccountYear ay = new AccountYear();
            ay.setYear(i);
            ay.setStartPrinciple(principle);
            for (int j = 0; j < 4; j++) {
                BigDecimal q = calculateQuarterlyAmount(principle, quarterly);
                ay.setIntrestEarned(ay.getIntrestEarned().add(q));
                principle = principle.add(q);
            }
            ay.setEndPrinciple(principle);
            accountYears.add(ay);
        }

        return accountYears;
    }

    @RequestMapping(value = "/calculateRest/{years}/{principle}/{intrest}", method = RequestMethod.GET)
    public List<AccountYear> calculateRest(@PathVariable Integer years,@PathVariable BigDecimal principle,@PathVariable BigDecimal intrest) {
        BigDecimal quarterly = intrest.multiply(new BigDecimal(0.25));

        List<AccountYear> accountYears = new ArrayList<>();
        for (int i = 0; i < years; i++) {
            AccountYear ay = new AccountYear();
            ay.setYear(i);
            ay.setStartPrinciple(principle);
            for (int j = 0; j < 4; j++) {
                BigDecimal q = calculateQuarterlyAmount(principle, quarterly);
                ay.setIntrestEarned(ay.getIntrestEarned().add(q));
                principle = principle.add(q);
            }
            ay.setEndPrinciple(principle);
            accountYears.add(ay);
        }

        return accountYears;
    }
}
