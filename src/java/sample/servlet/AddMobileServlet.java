/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
import sample.tbl_Mobile.Tbl_MobileError;
import sample.utils.Validation;

/**
 *
 * @author KHANHBQSE63463
 */
@WebServlet(name = "AddMobileServlet", urlPatterns = {"/AddMobileServlet"})
public class AddMobileServlet extends HttpServlet {
    private final int STAFF_ROLE = 2;
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

        String mobileIdStr = request.getParameter("mobileId").trim();
        String descriptionStr = request.getParameter("description").trim();
        String priceStr = request.getParameter("price").trim();
        String mobileNameStr = request.getParameter("mobileName").trim();
        String yearOfProductionStr = request.getParameter("yearOfProduction").trim();
        String quantityStr = request.getParameter("quantity").trim();
        boolean notSale = true;
        if (request.getParameter("notSale") == null) {
            notSale = false;
        }

        //String url = "CoordinatorServlet?action=SEARCH&idOrNameOfDevice=" + mobileIdStr;
        String url = "AddMobile.jsp";

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                int role = (int)session.getAttribute("ROLE");
                if (role == STAFF_ROLE) {
                    Tbl_MobileDAO mobileDAO = new Tbl_MobileDAO();
                    Tbl_MobileError mobileError = new Tbl_MobileError();
                    float price = 0;
                    int yearOfProduction = 0;
                    int quantity = 0;
                    boolean isError = false;
                    if (!Validation.isValidLengthMobileId(mobileIdStr)) {
                        mobileError.setMobileIdLengthError("Length of MobileId must greater than 1 and less than 11");
                        isError = true;
                    } else if (mobileDAO.isExistedMobileID(mobileIdStr)) {
                        mobileError.setMobileIdExisted("This mobileId is existed");
                        isError = true;
                    }
                    if (!Validation.isValidLengthDescription(descriptionStr)) {
                        mobileError.setDescriptionLengthError("Length of description must greater than 1 and less than 251");
                        isError = true;
                    }
                    if (!Validation.isValidPriceValue(priceStr)) {
                        mobileError.setPriceFormatError("Price valud must be a float number");
                        isError = true;
                    }
                    if (!Validation.isValidLengthMobileName(mobileNameStr)) {
                        mobileError.setMobileNameLengthError("Length of mobile name must greater than 1 and less than 20");
                        isError = true;
                    }
                    if (!Validation.isValidYearOfProductionValue(yearOfProductionStr)) {
                        mobileError.setYearOfProductionError("Year of production must greater than 0");
                        isError = true;
                    }
                    if (!Validation.isValidQuantityValue(quantityStr)) {
                        mobileError.setQuantityError("Quantity must greater than 0");
                        isError = true;
                    }
                    if (!isError) {
                        price = Float.parseFloat(priceStr);
                        yearOfProduction = Integer.parseInt(yearOfProductionStr);
                        quantity = Integer.parseInt(quantityStr);
                        Tbl_MobileDTO mobileDTO = new Tbl_MobileDTO(mobileIdStr, descriptionStr, price, mobileNameStr, yearOfProduction, quantity, notSale);
                        mobileDAO.insert(mobileDTO);
                    }
                    request.setAttribute("ERROR", mobileError);
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } catch (NamingException ex) {
            log("Add Mobile Servlet : NamingException " + ex.getMessage());
        } catch (SQLException ex) {
            log("Add Mobile Servlet : SQLException " + ex.getMessage());
        } finally {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
            requestDispatcher.forward(request, response);
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
