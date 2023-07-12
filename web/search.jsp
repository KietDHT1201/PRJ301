<%-- 
    Document   : search
    Created on : Jun 1, 2023, 1:31:00 PM
    Author     : ASUS
--%>

<%--<%@page import="kiet.registration.RegistrationDTO"%>
<%@page import="java.util.List"%>--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
    </head>
    <body>
        <font color="red">
        Welcome, ${sessionScope.USER.lastname}
        </font>
        
        <h1 style="color:red">Search Page</h1>
        
        <form action="searchLastName">
            Search <input type="text" name="txtSearchValue" value="${param.txtSearchValue}"><br/>
            <input type="submit" value="Search" name="btAction"/>
        </form>
        
        <br/>
        
        <c:set var="searchValue" value="${param.txtSearchValue}"/> 
        
        <c:if test="${not empty searchValue}">
            <c:set var="result" value="${requestScope.SEARCH_RESULT}"/>
            <c:if test="${not empty result}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Full name</th>
                            <th>Role</th>
                            <th>Delete</th>
                            <th>Update</th>
                        </tr>
                    </thead>
                    <tbody>
                         <c:forEach var="dto" items="${result}" varStatus="counter">
                             <form action="update" method="POST">
                             <tr>
                                <td>
                                    ${counter.count}
                                 .</td>
                                <td>
                                    ${dto.username}
                                    <input type="hidden" name="txtUsername" value="${dto.username}" />
                                </td>
                                <td>
                                    <input type="text" name="txtPassword" value="${dto.password}" />
                                </td>
                                <td>
                                    <input type="text" name="txtFullname" value="${dto.lastname.trim()}" />
                                </td>
                                <td>
                                    <input type="checkbox" name="chkAdmin" value="ON" 
                                           <c:if test="${dto.role}">
                                               checked="checked"
                                           </c:if>
                                           />
                                </td>
                                <td>
                                    <c:url var="deleteLink" value="delete">
                                        <c:param name="btAction" value="Delete"/>
                                        <c:param name="pk" value="${dto.username}"/>
                                        <c:param name="LastSearchValue" value="${searchValue}"/>
                                    </c:url>
                                    <a href="${deleteLink}">Delete</a>
                                </td>
                                <td>
                                    <input type="hidden" name="LastSearchValue" value="${searchValue}" />
                                    <input type="submit" value="Update" name="btAction" />
                                </td>
                            </tr>
                            </form>   
                         </c:forEach>                                                  
                
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty result}">
                <h2>
                    No record is matched !!!
                </h2>
            </c:if>
        </c:if>
        <br/>
        <form action="logout">
            Click this button to logout --> <input type="submit" value="Logout" name="btAction"/><br/>
        </form>
        <%--%
            //login thành công khi cookie tồn tại
            Cookie[] cookies = request.getCookies();
            if(cookies != null){
                Cookie newestCookie = cookies[cookies.length -1];
                String username = newestCookie.getName();
                %>
                <font color="red">
                    Welcome, <%= username %>
                </font>
        <%
            }
        %--%>
<!--        <h1 style="color:red">Search Page</h1>
        
        <form action="DispatcherServlet">
            Search <input type="text" name="txtSearchValue" value=""><br/>
            <input type="submit" value="Search" name="btAction"/><br/>
            <input type="submit" value="Logout" name="btAction"/><br/>

        </form>-->
        
        <%--<% 
            String searchValue = request.getParameter("txtSearchValue");
            
            if (searchValue != null) {
                // tại sao obj phải ép kiểu
                // do obj seriable 
                List<RegistrationDTO> result = (List<RegistrationDTO>)
                        request.getAttribute("SEARCH_RESULT");
                //lấy xong fai check
                if (result != null) {
                    %>
                    
                    <table border="1">
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>Username</th>
                                <th>Password</th>
                                <th>FullName</th>
                                <th>Role</th>
                                <th>Delete</th>
                                <th>Update</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                int count = 0;
                                for(RegistrationDTO dto: result){
                                    String urlRewriting = "DispatcherServlet"
                                            + "?btAction=Delete"
                                            + "&pk=" + dto.getUsername()
                                            + "&LastSearchValue=" + searchValue;
                                    //req para còn exist do config para
                                    %>
                                    
                        <form action="DispatcherServlet" method="POST">
                            <tr>
                                        <td>
                                            <%= ++count%>
                                        .</td>
                                        <td>
                                            <%= dto.getUsername() %>
                                            <input type="hidden" name="txtUsername" 
                                                   value="<%= dto.getUsername() %>" />
                                        </td>
                                        <td>
                                            <input type="text" name="txtPassword" 
                                                   value="<%= dto.getPassword() %>"/>
                                        </td>
                                        <td>
                                            <%= dto.getLastname() %>
                                        </td>
                                        <td>
                                            <input type="checkbox" name="chkAdmin" value="ON" 
                                                   <% 
                                                    if (dto.isRole()) {
                                                      %>
                                                      checked="checked"
                                                   <%
                                                    } //end user is an admin
                                                   %>
                                                   />
                                        </td>                                        
                                        <td>
                                            <a href="<%= urlRewriting %>">Delete</a>
                                        </td>
                                        <td>
                                            <input type="hidden" name="lastSearchValue" 
                                                   value="<%= searchValue %>" />
                                            <input type="submit" value="Update" name="btAction" />
                                        </td>
                                    </tr>
                        </form>
                                    <%
                                }// end get data from result
                            %>
                        </tbody>
                    </table>

            <%
                } else { //result == null
                    %>
            
                    <h2>
                        No record is matched!!!
                    </h2>
                    
            <%
                }
            }// end page is called invalid
            
            
        %> --%>
    </body>
</html>
