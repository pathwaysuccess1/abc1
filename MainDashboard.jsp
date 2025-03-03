<%@page import="model.Account"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <link rel="shortcut icon" href="images/web_logo.png">
        <style>
            .action-links a {
                margin-right: 10px;
                text-decoration: none;
            }
            .action-links a.edit {
                color: #007bff;
            }
            .action-links a.delete {
                color: #dc3545;
            }
        </style>
    </head>
    <body>
        <jsp:include page="Dashboard.jsp"></jsp:include>
        <%
            boolean check = false;
            Account a = (Account) session.getAttribute("login");
            if (a != null && "administrator".equals(a.getRoleInSystem())) {
                check = true;
            }
        %>
        <h2 class="text-center mt-5">Available To Access</h2>
        <% if (check) { %>
        <table class="table table-striped mt-4 text-center" style="width: 70%; margin: auto">
            <thead>
                <tr>
                    <th>Accounts</th>
                    <th>Categories</th>
                    <th>Products</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><a href="AddAccount.jsp">Add new account</a></td>
                    <td><a href="AddCategory.jsp">Add new category</a></td>
                    <td><a href="AddProduct.jsp">Add new product</a></td>
                    <td class="action-links">
                        <a href="#" class="edit">Update</a>
                        <a href="#" class="delete">Delete</a>
                    </td>
                </tr>
                <tr>
                    <td><a href="ListAccount.jsp">Get list accounts</a></td>
                    <td><a href="ListCategory.jsp">Get list categories</a></td>
                    <td><a href="ListProduct.jsp">Get list products</a></td>
                    <td class="action-links">
                        <a href="#" class="edit">Update</a>
                        <a href="#" class="delete">Delete</a>
                    </td>
                </tr>
            </tbody>
        </table>
        <% } else { %>
        <table class="table table-striped mt-4 text-center" style="width: 60%; margin: auto">
            <thead>
                <tr>
                    <th>Categories</th>
                    <th>Products</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><a href="AddCategory.jsp">Add new category</a></td>
                    <td><a href="AddProduct.jsp">Add new product</a></td>
                    <td class="action-links">
                        <a href="#" class="edit">Update</a>
                        <a href="#" class="delete">Delete</a>
                    </td>
                </tr>
                <tr>
                    <td><a href="ListCategory.jsp">Get list categories</a></td>
                    <td><a href="ListProduct.jsp">Get list products</a></td>
                    <td class="action-links">
                        <a href="#" class="edit">Update</a>
                        <a href="#" class="delete">Delete</a>
                    </td>
                </tr>
            </tbody>
        </table>
        <% } %>    
    </body>
</html>
