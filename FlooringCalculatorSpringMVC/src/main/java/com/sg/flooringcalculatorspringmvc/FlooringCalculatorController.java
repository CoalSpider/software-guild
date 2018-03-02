/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringcalculatorspringmvc;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
public class FlooringCalculatorController {
    private static final BigDecimal INSTALL_RATE_PER_HOUR = new BigDecimal("20");
    private static final BigDecimal LABOR_COST_PER_HOUR = new BigDecimal("86.00");
    private static final BigDecimal BILLING_INCREMENT_IN_MIN = new BigDecimal("15");
    private static final BigDecimal LABOR_COST_PER_CYCLE = LABOR_COST_PER_HOUR.multiply(new BigDecimal("0.25"));
    
    @RequestMapping(value="/calculate", method=RequestMethod.POST)
    public String calculate(HttpServletRequest request, Model model){
        String width = request.getParameter("width");
        String length = request.getParameter("length");
        String costFt2 = request.getParameter("cost per ft^2");
        BigDecimal w = new BigDecimal(width);
        BigDecimal l = new BigDecimal(length);
        BigDecimal cost = new BigDecimal(costFt2);

        BigDecimal area = w.multiply(l);
        BigDecimal materialTotal = cost.multiply(area).setScale(2, RoundingMode.HALF_UP);

        BigDecimal hours = area.divide(INSTALL_RATE_PER_HOUR);
        BigDecimal minutes = hours.multiply(new BigDecimal("60"));
        BigDecimal billingCycles = minutes.divide(BILLING_INCREMENT_IN_MIN).setScale(0,RoundingMode.CEILING);
        BigDecimal laborTotal = billingCycles.multiply(LABOR_COST_PER_CYCLE).setScale(2, RoundingMode.HALF_UP);

        model.addAttribute("area", area);
        model.addAttribute("materialCost", materialTotal);
        model.addAttribute("laborCost", laborTotal);
        model.addAttribute("total", materialTotal.add(laborTotal));
        return "result";
    }
}
