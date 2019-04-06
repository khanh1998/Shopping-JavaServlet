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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.tbl_User.Tbl_UserDAO;

/**
 *
 * @author KHANHBQSE63463
 */
public class LoginServlet extends HttpServlet {
    
    private final int CUSTOMER_ROLE = 0;
    private final int STAFF_ROLE = 2;
    private final int MANAGER_ROLE = 1;
    
    private final String UNREGISTRATED_HTML = "Unregistered.html";
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

        String url = UNREGISTRATED_HTML;
        try {
            String username = request.getParameter("userId");
            int password = Integer.parseInt(request.getParameter("password"));
            //int role = Integer.parseInt(request.getParameter("role"));
            
            Tbl_UserDAO workshop1DAO = new Tbl_UserDAO();
            
            int loginSuccess = workshop1DAO.isRegistratedAccount(username, password);
            if (loginSuccess > -1) {
                if (loginSuccess == CUSTOMER_ROLE) {
                    url = SEARCH_CUSTOMER_JSP;
                } else if (loginSuccess == STAFF_ROLE) {
                    url = SEARCH_STAFF_JSP;
                }
                
                HttpSession session = request.getSession(true);
                
                session.setAttribute("USERNAME", username);
                session.setAttribute("PASSWORD", password);
                session.setAttribute("ROLE", loginSuccess);
            }
            response.sendRedirect(url);
        } catch (NamingException ex) {
            log("Login Servlet NamingException: " + ex.getMessage());
        } catch (SQLException ex) {
            log("Login Servlet SQLException: " + ex.getMessage());
        } catch (NumberFormatException ex) { 
            log("Login Servlet SQLException: " + ex.getMessage());
        }finally {

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
