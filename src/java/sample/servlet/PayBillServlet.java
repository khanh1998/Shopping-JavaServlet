/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.cart.Cart;
import sample.tbl_Mobile.Tbl_MobileDAO;
import sample.tbl_bill.Tbl_BillDAO;

/**
 *
 * @author KHANHBQSE63463
 */
@WebServlet(name = "PayBillServlet", urlPatterns = {"/PayBillServlet"})
public class PayBillServlet extends HttpServlet {

    private final int CUSTOMER_ROLE = 0;

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

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                int role = (int) session.getAttribute("ROLE");
                if (role == CUSTOMER_ROLE) {
                    Cart cart = (Cart) session.getAttribute("CART");
                    String userName = (String) session.getAttribute("USERNAME");

                    //bill
                    Tbl_BillDAO billDAO = new Tbl_BillDAO();
                    billDAO.writeBill(cart.getList(), userName, new Timestamp(System.currentTimeMillis()));

                    //update quantity
                    Tbl_MobileDAO mobileDAO = new Tbl_MobileDAO();
                    for (Map.Entry<String, Integer> mobile : cart.getList().entrySet()) {
                        mobileDAO.minusQuantityOfMobile(mobile.getKey(), mobile.getValue());
                    }

                    cart.removeAllItems();
                    session.setAttribute("CART", cart);
                    session.setAttribute("CART", null);
                    session.removeAttribute("CART");
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } catch (NamingException ex) {
            log("PAY BILL SERVLET: NamingException", ex);
        } catch (SQLException ex) {
            log("PAY BILL SERVLET: SQLException", ex);
        } finally {
            response.sendRedirect("SearchCustomer.jsp");
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
