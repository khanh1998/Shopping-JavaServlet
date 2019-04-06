<%-- 
    Document   : ViewCart
    Created on : Oct 11, 2018, 9:19:39 PM
    Author     : KHANHBQSE63463
--%>

<%@page import="sample.tbl_Mobile.Tbl_MobileDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="sample.cart.Cart"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart</title>
    </head>
    <body>
        <h1>View Cart</h1>

        <%
            String minPrice = request.getParameter("lastMinPrice");
            String maxPrice = request.getParameter("lastMaxPrice");
            String username = (String)session.getAttribute("USERNAME");
            if (username != null) {
            Cart cart = (Cart) session.getAttribute("CART");
            if (cart != null) {
                Map<String, Integer> items = cart.getList();
                if (items != null) {
                    ArrayList<Tbl_MobileDTO> selectedMobiles = (ArrayList<Tbl_MobileDTO>) request.getAttribute("SELECTEDMOBILES");
                    if (selectedMobiles != null) {

        %>
        <form action="CoordinatorServlet">
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Remove</th>
                    </tr>
                </thead>
                <tbody>
                    <%  int i = 0;
                        double total = 0;
                        for (Tbl_MobileDTO mobile : selectedMobiles) {
                            total += mobile.getPrice();
                    %>
                    <tr>
                        <td><%= ++i%></td>
                        <td><%= mobile.getMobileName()%></td>
                        <td><%= mobile.getDescription()%></td>
                        <td><%= items.get(mobile.getMobileId())%></td>
                        <td><%= mobile.getPrice() %></td>
                        <td>
                            <input type="checkbox" name="mobileId" value="<%= mobile.getMobileId()%>">
                        </td>
                    </tr>
                    <%
                        }
                    %>
                    <tr><td><%= "Total: " + total %></td></tr>
                <input type="hidden" name="lastMinPrice" value="<%= minPrice%>">
                <input type="hidden" name="lastMaxPrice" value="<%= maxPrice%>">
                <input type="submit" name="action" value="REMOVE_MOBILE_FROM_CART">
                </tbody>
            </table>
        </form>
        <a href="CoordinatorServlet?action=PAY">Pay</a>
        <%
            }//end if
        } else {
        %>
        <font color="green">Your cart is empty</font>
        <%
            }
        } else {
        %>
        <font color="green">You haven't bought anything yet</font>
        <%
            }

            String urlBackToShopping = "CoordinatorServlet"
                    + "?action=SEARCH"
                    + "&minPrice=" + minPrice
                    + "&maxPrice=" + maxPrice;
        %>
        <a href="<%= urlBackToShopping%>">Back to shopping</a>
        <%}%>
    </body>
</html>
