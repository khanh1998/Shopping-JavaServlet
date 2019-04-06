<%-- 
    Document   : SearchStaff
    Created on : Oct 10, 2018, 2:37:04 PM
    Author     : KHANHBQSE63463
--%>

<%@page import="sample.tbl_Mobile.Tbl_MobileDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Staff</title>
    </head>
    <body>
        <h1>Staff</h1>
        <%
            int role = (int) session.getAttribute("ROLE");
            if (role == 2) {
            String username = (String)session.getAttribute("USERNAME");
            if (username != null) {
        %>
        <font color="blue">
        Welcome  
        
        <%= username %>
        </font>
        <a href="CoordinatorServlet?action=LOGOUT">Logout</a>
        <form action="CoordinatorServlet">
            <input type="text" name="idOrNameOfDevice" value="">
            <input type="submit" name="action" value="SEARCH">
            <input type="reset" value="Reset">
        </form>
        <a href="AddMobile.html">Add a new Mobile</a>
        <%
            ArrayList<Tbl_MobileDTO> mobileList = (ArrayList<Tbl_MobileDTO>) request.getAttribute("RESULT");
            if (mobileList != null) {
                if (mobileList.isEmpty()) {
        %>
        <font color="red">Empty</font>
        <%
        } else {
        %>
        <table border="1">
            <thead>
                <tr>
                    <td>No.</td>
                    <td>Mobile ID</td>
                    <td>Description</td>
                    <td>Price</td>
                    <td>Name</td>
                    <td>Year</td>
                    <td>Quantity</td>
                    <td>Not sale</td>
                    <td>Delete</td>
                    <td>Update</td>
                </tr>
            </thead>
            <tbody>
                <%
                    int i = 0;
                    for (Tbl_MobileDTO mobile : mobileList) {
                        String urlRewriting = "CoordinatorServlet"
                                + "?action=DELETE"
                                + "&lastSearchValue=" + request.getParameter("idOrNameOfDevice")
                                + "&mobileId=" + mobile.getMobileId();
                %>
                <tr>
                    <form action="CoordinatorServlet">
                        <td><%= ++i%></td>
                        <td>
                            <input type="hidden" name="mobileId" value="<%= mobile.getMobileId()%>">
                            <%= mobile.getMobileId()%>
                        </td>
                        <td>
                            <input type="text" name="description" pattern=".{1,250}" 
                                   title="Length of Description must greater than 0 and less than 251" value="<%= mobile.getDescription()%>" required>
                        </td>
                        <td>
                            <input type="text" name="price" pattern="^\d+(.\d+)?$"
                             title="Price of mobile must be a float number" value="<%= mobile.getPrice()%>" required>
                        </td>
                        <td><%= mobile.getMobileName()%></td>
                        <td><%= mobile.getYearOfProduction()%></td>
                        <td>
                            <input type="text" name="quantity" pattern="^\d+$"
                                   title="Quantity must be a integer" value="<%= mobile.getQuantity()%>" required>

                        </td>
                        <td>
                            <input type="checkbox" name="notSale" value="<%= mobile.isNotSale()%>"
                            <%
                                if (mobile.isNotSale()) {
                            %>
                                checked
                            <%
                                } //end if isNotSale
                            %>       
                            >
                        </td>
                        <td><a href="<%= urlRewriting%>">Delete</a></td>
                        <td>
                            <input type="hidden" name="lastSearchValue" value="<%= request.getParameter("idOrNameOfDevice") %>">
                            <input type="submit" name="action" value="UPDATE">
                        </td>
                    </form>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
        <%
                }//end if empty
            }//end if != null
        }
}
%>
    </body>


</html>
