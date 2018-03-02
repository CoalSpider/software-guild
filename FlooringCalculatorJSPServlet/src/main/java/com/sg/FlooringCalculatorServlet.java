/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
@WebServlet(name = "FlooringCalculatorServlet", urlPatterns = {"/FlooringCalculatorServlet"})
public class FlooringCalculatorServlet extends HttpServlet {
    private static final BigDecimal INSTALL_RATE_PER_HOUR = new BigDecimal("20");
    private static final BigDecimal LABOR_COST_PER_HOUR = new BigDecimal("86.00");
    private static final BigDecimal BILLING_INCREMENT_IN_MIN = new BigDecimal("15");
    private static final BigDecimal LABOR_COST_PER_CYCLE = LABOR_COST_PER_HOUR.multiply(new BigDecimal("0.25"));
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

        request.setAttribute("area", area);
        request.setAttribute("materialCost", materialTotal);
        request.setAttribute("laborCost", laborTotal);
        request.setAttribute("total", materialTotal.add(laborTotal));
        
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
