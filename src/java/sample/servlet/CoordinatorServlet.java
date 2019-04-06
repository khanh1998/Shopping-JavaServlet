/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author KHANHBQSE63463
 */
public class CoordinatorServlet extends HttpServlet {

    private final String LOGIN_HTML = "login.html";
    private final String AUTO_LOGIN = "AutoLoginServlet";
    private final String LOGIN_SERVLET = "LoginServlet";
    private final String SEARCH_MOBILE_SERVLET = "SearchMobileServlet";
    private final String DELETE_MOBILE_SERVLET = "DeleteMobileServlet";
    private final String UPDATE_MOBILE_SERVLET = "UpdateMobileServlet";
    private final String ADD_MOBILE_SERVLET = "AddMobileServlet";
    private final String LOGOUT_SERVLET = "LogoutServlet";
    private final String ADD_TO_CART = "AddToCart";
    private final String VIEW_CART_SERVLET = "ViewCartServlet";
    private final String REMOVE_MOBILE_FROM_CART_SERVLET = "RemoveMobileFromCart";
    private final String PAY_BILL_SERVLET = "PayBillServlet";
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

        String action = request.getParameter("action");
        String url = AUTO_LOGIN;
        try {
            if (null == action) {
                url = AUTO_LOGIN;
            } else {
                switch (action) {
                    case "LOGIN":
                        url = LOGIN_SERVLET;
                        break;
                    case "LOGOUT":
                        url = LOGOUT_SERVLET;
                        break;
                    case "SEARCH":
                        url = SEARCH_MOBILE_SERVLET;
                        break;
                    case "DELETE":
                        url = DELETE_MOBILE_SERVLET;
                        break;
                    case "UPDATE":
                        url = UPDATE_MOBILE_SERVLET;
                        break;
                    case "ADD_MOBILE":
                        url = ADD_MOBILE_SERVLET;
                        break;
                    case "ADD_TO_CART":
                        url = ADD_TO_CART;
                        break;
                    case "VIEW_CART":
                        url = VIEW_CART_SERVLET;
                        break;
                    case "REMOVE_MOBILE_FROM_CART":
                        url = REMOVE_MOBILE_FROM_CART_SERVLET;
                        break;
                    case "PAY":
                        url = "PAY_BILL";
                        url = PAY_BILL_SERVLET;
                        break;
                    default:
                        break;
                }
            }
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
