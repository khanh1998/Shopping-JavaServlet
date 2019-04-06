<%-- 
    Document   : SearchCustomer
    Created on : Oct 10, 2018, 2:55:49 PM
    Author     : KHANHBQSE63463
--%>

<%@page import="java.util.Set"%>
<%@page import="sample.tbl_Mobile.Tbl_MobileDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search For Customer</title>
    </head>
    <body>
        <h1>Customer</h1>
        <%
            int role = (int) session.getAttribute("ROLE");
            if (role == 0) {
            String username = (String) session.getAttribute("USERNAME");
            if (username != null) {
        %>
        <font color="blue">
        Welcome  
        
        <%= username%>
        </font>
        <a href="CoordinatorServlet?action=LOGOUT">Logout</a>
        <form action="CoordinatorServlet">
            Min price <input type="text" name="minPrice" pattern="^\d+(.\d+)?$"
                             title="Price of mobile must be a float number" 
                             value="<%if (request.getParameter("minPrice") != null) {%><%= request.getParameter("minPrice").trim()%><%}%>">
            Max price <input type="text" name="maxPrice" pattern="\d+(.\d+)?" 
                             title="Price of mobile must be a float number" 
                             value="<%if (request.getParameter("maxPrice") != null) {%><%= request.getParameter("maxPrice").trim()%><%}%>">
            <input type="submit" name="action" value="SEARCH">
            <input type="reset" value="Reset">
        </form>
        <%
            String lastMinValue = request.getParameter("minPrice");
            if (lastMinValue == null)
                lastMinValue = "";
            String lastMaxValue = request.getParameter("maxPrice");
            if (lastMaxValue == null)
                lastMaxValue = "";
            String urlViewCart = "CoordinatorServlet"
                    + "?action=VIEW_CART"
                    + "&lastMinPrice=" + lastMinValue
                    + "&lastMaxPrice=" + lastMaxValue;
        %>
        <a href="<%= urlViewCart%>">View Cart</a>
        <%
            ArrayList<Tbl_MobileDTO> mobileList = (ArrayList<Tbl_MobileDTO>) request.getAttribute("RESULT");
            Set<String> soldOutIdList = (Set<String>) session.getAttribute("SOLD_OUT_ID_LIST");
            System.out.println(soldOutIdList);
            if (mobileList != null) {
                if (mobileList.isEmpty()) {
        %>
        <font color="red">Empty</font>
        <%
        } else {
        %>
        <table border="1" cellspacing="2">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Year</th>
                    <th>Add to cart</th>
                </tr>
            </thead>
            <tbody>
                <%
                    int i = 0;
                    for (Tbl_MobileDTO mobileDTO : mobileList) {
                %>
                <tr>
            <form action="CoordinatorServlet">
                <input type="hidden" name="mobileId" value="<%= mobileDTO.getMobileId()%>">
                <input type="hidden" name="minPrice" value="<%= request.getParameter("minPrice")%>">
                <input type="hidden" name="maxPrice" value="<%= request.getParameter("maxPrice")%>">
                <th><%= ++i%></th>
                <th>
                    <%= mobileDTO.getMobileName()%>
                </th>
                <th>
                    <%= mobileDTO.getDescription()%>
                </th>
                <th>
                    <%= mobileDTO.getPrice()%>
                </th>
                <th>
                    <%= mobileDTO.getYearOfProduction()%>
                </th>
                <th><%
                    if (mobileDTO.isNotSale()) {
                    %>
                    <font color="red">Not sale</font>
                    <%
                    } else if (soldOutIdList != null && soldOutIdList.contains(mobileDTO.getMobileId())) {
                    %>
                    <font color="blue">Sold out</font>
                    <%
                    } else {

                    %>
                    <input type="submit" name="action" value="ADD_TO_CART">

                    <%                        }
                    %>
                </th>
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
