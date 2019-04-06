<%-- 
    Document   : AddMobile
    Created on : Oct 10, 2018, 7:56:45 PM
    Author     : KHANHBQSE63463
--%>

<%@page import="sample.tbl_Mobile.Tbl_MobileError"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Mobile</title>
    </head>
    <body>
        <h1>Add Mobile</h1>
        <font color="blue">
        Welcome  
        <%
            String username = (String)session.getAttribute("USERNAME");
            Tbl_MobileError mobileError = (Tbl_MobileError) request.getAttribute("ERROR");
        %>
        <%= username %>
        </font>
        <a href="CoordinatorServlet?action=LOGOUT">Logout</a>
        <a href="SearchStaff.jsp">Back to Search</a>
        <form action="CoordinatorServlet">
            <%
                if (mobileError.getMobileIdLengthError() != null) {
            %>
                <font color="red"><%= mobileError.getMobileIdLengthError() %></font><br/>
            <%
                } else
                if (mobileError.getMobileIdExisted() != null) {
            %>
                <font color="red"><%= mobileError.getMobileIdExisted() %></font><br/>
            <%
                }
            %>
            Mobile ID <input type="text" value="<%=request.getParameter("mobileId")%>" name="mobileId"><br/>
            <%
                if (mobileError.getDescriptionLengthError() != null) {
            %>
                <font color="red"><%= mobileError.getDescriptionLengthError() %></font><br/>
            <%
                }
            %>
            Description <input type="text" value="<%=request.getParameter("description")%>" name="description"><br/>
            <%
                if (mobileError.getPriceFormatError()!= null) {
            %>
                <font color="red"><%= mobileError.getPriceFormatError() %></font><br/>
            <%
                }
            %>
            Price <input type="text" value="<%=request.getParameter("price")%>" name="price"><br/>
            <%
                if (mobileError.getMobileNameLengthError()!= null) {
            %>
                <font color="red"><%= mobileError.getMobileNameLengthError() %></font><br/>
            <%
                }
            %>
            Name <input type="text" value="<%=request.getParameter("mobileName")%>" name="mobileName"><br/>
            <%
                if (mobileError.getYearOfProductionError()!= null) {
            %>
                <font color="red"><%= mobileError.getYearOfProductionError() %></font><br/>
            <%
                }
            %>
            Year <input type="number" value="<%=request.getParameter("yearOfProduction")%>" name="yearOfProduction"><br/>
            <%
                if (mobileError.getQuantityError()!= null) {
            %>
                <font color="red"><%= mobileError.getQuantityError() %></font><br/>
            <%
                }
            %>
            Quantity <input type="number" value="<%=request.getParameter("quantity")%>" name="quantity"><br/>
            Not sale <input type="checkbox" value="" name="notSale" <%if (request.getParameter("notSale") != null){%>checked<%}%>><br/>
            <input type="submit" name="action" value="ADD_MOBILE">
            <input type="reset" value="Reset">
        </form>
    </body>
</html>
