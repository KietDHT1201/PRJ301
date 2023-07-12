<%-- 
    Document   : viewCart
    Created on : Jun 19, 2023, 8:00:17 AM
    Author     : TuanKiet
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.Map"%>
<%@page import="kiet.cart.CartObj"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book Store</title>
    </head>
    
    <c:if test="${not empty sessionScope}">
        <c:set var="cart" value="${sessionScope.CART}"/>
        <c:if test="${not empty cart}">
            <c:set var="item" value="${cart.items}"/>
            <c:if test="${not empty item}">
                <h2>Your cart include</h2>
                <form action="cart" method="POST">
                    <table border="1">
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>Name</th>
                                <th>Quantity</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="dto" items="${item}" varStatus="counter">
                                <tr>
                                    <td>
                                        ${counter.count}
                                    </td>
                                    <td>
                                        ${dto.key}
                                    </td>
                                    <td>
                                        ${dto.value}
                                    </td>
                                    <td>
                                        <input type="checkbox" name="chkItem" value="${dto.key}" />
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td colspan="3">
                                    <a href="shopping.html">Click here to go shopping</a>
                                </td>
                                <td>
                                    <input type="submit" value="Remove Selected Items" name="btAction" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </form>
                    
                
            </c:if>
                <c:if test="${empty item}">
                    <h2>empty</h2>
                </c:if>
        </c:if>
    </c:if>
    <%--
    <body>
        <h1>Java Book Store</h1>
        <%
            //1. Cust goes to his/ger cart place
            if(session != null){
            //2. Cust takes his/her cart
            CartObj cart = (CartObj)session.getAttribute("CART");
                if(cart != null){
                    //3. Cust get items 
                    Map <String, Integer> items = cart.getItems();
                    if(items != null){
                        %>
                        <h2>Your cart include</h2>
                        <form name="DispatcherServlet">
                            <table border="1">
                                <thead>
                                    <tr>
                                        <th>No.</th>
                                        <th>Name</th>
                                        <th>Quantity</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        int count = 0;
                                        for(String key : items.keySet()){
                                            %>
                                    <tr>
                                        <td>
                                            <%= ++ count %>
                                        </td>
                                        <td>
                                            <%= key %>
                                        </td>
                                        <td>
                                            <%= items.get(key) %>
                                        </td>
                                        <td>
                                            <input type="checkbox" name="chkItem"
                                                   value="<%= key%>">
                                        </td>
                                    </tr>
                                    <%
                                        }//end traverse items
                                    %>
                                    <tr>
                                        <td colspan="3">
                                            <a href="Shopping.html">Click here to go shopping</a>
                                        </td>
                                        <td>
                                            <input type="submit" value="Remove Selected Items" name="btAction" />
                                        </td>
                                    </tr>
                                    <%
                                %>
                                </tbody>
                            </table>
                        </form>
                        
        <%
                        //4. Cust traverses all item to show
                        return;
                    }//end items has existed
                }//end cart has existed
            }//end session has existed
        %>
        <h2>
            No cart is existed
        </h2>
--%>
    </body>
</html>
