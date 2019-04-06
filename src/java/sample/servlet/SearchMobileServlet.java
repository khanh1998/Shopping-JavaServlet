/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.tbl_Mobile.Tbl_MobileDAO;
import sample.tbl_Mobile.Tbl_MobileDTO;

/**
 *
 * @author KHANHBQSE63463
 */
@WebServlet(name = "SearchMobileServlet", urlPatterns = {"/SearchMobileServlet"})
public class SearchMobileServlet extends HttpServlet {

    private final String SEARCH_CUSTOMER_JSP = "SearchCustomer.jsp";
    private final String SEARCH_STAFF_JSP = "SearchStaff.jsp";

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
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        String idOrName = request.getParameter("idOrNameOfDevice");
        String url = "";
        try {
            if (idOrName != null) { //Staff search
                if (idOrName.trim().length() > 0) {
                    Tbl_MobileDAO mobileDAO = new Tbl_MobileDAO();
                    ArrayList<Tbl_MobileDTO> result = mobileDAO.searchByIdOrName(idOrName.trim());
                    request.setAttribute("RESULT", result);
                }
                url = SEARCH_STAFF_JSP;
            } else if (idOrName == null) { //Customer Search

                String minPriceStr = request.getParameter("minPrice");
                String maxPriceStr = request.getParameter("maxPrice");

                if (!minPriceStr.isEmpty() || !maxPriceStr.isEmpty()) {
                    Tbl_MobileDAO mobileDAO = new Tbl_MobileDAO();
                    ArrayList<Tbl_MobileDTO> result = null;

                    if (!minPriceStr.isEmpty() && !maxPriceStr.isEmpty()) {

                        float minPrice = Float.parseFloat(minPriceStr);
                        float maxPrice = Float.parseFloat(maxPriceStr);
                        result = mobileDAO.searchByRangeOfPrice(minPrice, maxPrice);
                    } else if (minPriceStr.isEmpty() && !maxPriceStr.isEmpty()) {

                        float maxPrice = Float.parseFloat(maxPriceStr);
                        result = mobileDAO.searchByMaxPrice(maxPrice);
                    } else if (maxPriceStr.isEmpty() && !minPriceStr.isEmpty()) {

                        float minPrice = Float.parseFloat(minPriceStr);
                        result = mobileDAO.searchByMinPrice(minPrice);
                    }
                    //check sold out list
                    HttpSession session = request.getSession(true);
                    Set<String> soldOutIdList = (Set<String>) session.getAttribute("SOLD_OUT_ID_LIST");
                    if (soldOutIdList == null) {
                        soldOutIdList = new HashSet<>();
                    }
                    for (Tbl_MobileDTO mobileDTO : result) {
                        if (mobileDTO.getQuantity() == 0) {
                            soldOutIdList.add(mobileDTO.getMobileId());
                        }
                    }
                    session.setAttribute("SOLD_OUT_ID_LIST", soldOutIdList);
                    request.setAttribute("RESULT", result);
                }
                url = SEARCH_CUSTOMER_JSP;
            }
        } catch (NamingException ex) {
            log("Search Device Servlet " + ex.getMessage());
        } catch (SQLException ex) {
            log("Search Device Servlet " + ex.getMessage());
        } finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
            out.close();
        }
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
