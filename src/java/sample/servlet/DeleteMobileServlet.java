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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.tbl_Mobile.Tbl_MobileDAO;

/**
 *
 * @author KHANHBQSE63463
 */
@WebServlet(name = "DeleteMobileServlet", urlPatterns = {"/DeleteMobileServlet"})
public class DeleteMobileServlet extends HttpServlet {

    private final String ERROR_HTML = "error.html";
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

        String mobileId = request.getParameter("mobileId");
        String url = ERROR_HTML;
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                int role = (int) session.getAttribute("ROLE");
                if (role == STAFF_ROLE) {
                    if (mobileId != null) {
                        Tbl_MobileDAO mobileDAO = new Tbl_MobileDAO();
                        boolean isDeleteSuccess = mobileDAO.deleteById(mobileId);
                        if (isDeleteSuccess) {
                            url = "CoordinatorServlet"
                                    + "?action=SEARCH"
                                    + "&idOrNameOfDevice=" + request.getParameter("lastSearchValue");
                        }
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (NamingException ex) {
            log("Delete Mobile Servlet : NamingException " + ex.getMessage());
        } catch (SQLException ex) {
            log("Delete Mobile Servlet : SQLException " + ex.getMessage());
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
