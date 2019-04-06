/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashSet;
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

/**
 *
 * @author KHANHBQSE63463
 */
@WebServlet(name = "AddToCart", urlPatterns = {"/AddToCart"})
public class AddToCart extends HttpServlet {

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
        String mobileId = request.getParameter("mobileId");
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");
        String url = "CoordinatorServlet"
                + "?action=SEARCH"
                + "&minPrice=" + minPrice
                + "&maxPrice=" + maxPrice;
        try {
            //get quantity of mobile from database
            Tbl_MobileDAO mobileDAO = new Tbl_MobileDAO();
            int totalMobiles = mobileDAO.getQuantityOfMobile(mobileId);
            //get selected mobile in cart
            HttpSession session = request.getSession(false);
            if (session != null) {
                int role = (int) session.getAttribute("ROLE");
                if (role == CUSTOMER_ROLE) {
                    Cart cart = (Cart) session.getAttribute("CART");
                    if (cart == null) {
                        cart = new Cart();
                        cart.setUserId((String) session.getAttribute("USERNAME"));
                    }
                    int selectedMobiles = cart.getQuantityOfItem(mobileId);
                    //check if a customer can one more mobile or not
                    if (totalMobiles > selectedMobiles) {
                        cart.addItem(mobileId);
                    }
                    if (totalMobiles == (selectedMobiles + 1)) {
                        Set<String> soldOutIdList = (Set<String>) session.getAttribute("SOLD_OUT_ID_LIST");
                        if (soldOutIdList == null) {
                            soldOutIdList = new HashSet<>();
                        }
                        soldOutIdList.add(mobileId);
                        session.setAttribute("SOLD_OUT_ID_LIST", soldOutIdList);
                    }
                    //update cart object to session
                    session.setAttribute("CART", cart);
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (NamingException ex) {
            log("Add To Cart: NamingException" + ex.getMessage());
        } catch (SQLException ex) {
            log("Add To Cart: SQLException" + ex.getMessage());
        } finally {
            response.sendRedirect(url);
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
