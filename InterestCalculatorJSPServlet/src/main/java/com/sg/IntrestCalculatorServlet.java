/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ben Norman
 */
@WebServlet(name = "IntrestCalculatorServlet", urlPatterns = {"/IntrestCalculatorServlet"})
public class IntrestCalculatorServlet extends HttpServlet {

    //CurrentBalance * (1 + (QuarterlyInterestRate / 100))
    private BigDecimal calculateQuarterlyAmount(BigDecimal currBalance, BigDecimal quarteryRate) {
        return currBalance.multiply(quarteryRate.divide(new BigDecimal("100.00"))).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String intrest = request.getParameter("intrest");
        String principle = request.getParameter("principle");
        String years = request.getParameter("years");
        BigDecimal yrIntrest = new BigDecimal(intrest);
        BigDecimal qrtIntrest = yrIntrest.multiply(new BigDecimal(0.25));
        BigDecimal sPrinciple = new BigDecimal(principle);
        
        int yrs = Integer.parseInt(years);
        List<AccountYear> accountYears = new ArrayList<>();
        for (int i = 0; i < yrs; i++) {
            AccountYear ay = new AccountYear();
            ay.setYear(i);
            ay.setStartPrinciple(sPrinciple);
            for (int j = 0; j < 4; j++) {
                BigDecimal qIntrest = calculateQuarterlyAmount(sPrinciple, qrtIntrest);
                ay.setIntrestEarned(ay.getIntrestEarned().add(qIntrest));
                sPrinciple = sPrinciple.add(qIntrest);
            }
            ay.setEndPrinciple(sPrinciple);
            accountYears.add(ay);
        }

        request.setAttribute("accountYears", accountYears);

        RequestDispatcher rd = request.getRequestDispatcher("result.jsp");
        rd.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
