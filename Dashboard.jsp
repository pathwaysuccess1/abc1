<%-- 
    Document   : Dashboard
    Created on : Feb 25, 2025, 7:07:34 PM
    Author     : USER
--%>

<%@page import="model.Account"%>
<%@page import="context.Navigation"%>
<%@page import="context.Action"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Dashboard Page</title>
        <link rel="shortcut icon" href="images/web_logo.png">
        <!-- Latest compiled and minified CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Latest compiled JavaScript -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body>

        <%
            Account a = (Account) session.getAttribute("login");
            String fullname = "";
            if (a != null) {
                fullname = a.getLastName() + " " + a.getFirstName();
            } else {
                // Handle the case where the user isn't logged in
                // Maybe redirect to the login page
                response.sendRedirect("Login.jsp");
                return;
            }
        %>
        <nav class="navbar navbar-expand-sm navbar-dark bg-dark">
            <div class="container">

                <a class="navbar-brand" href="<%= Navigation.MAIN_DASHBOARD%>">Welcome to <span style="color: red"><%= a.getFirstName()%> </span><%= fullname%> </a>

                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav">
                        <li class="nav-item mx-4">
                            <a class="nav-link" href="<%= Navigation.WELCOME%>">Home</a>                        
                        </li>
                        <li class="nav-item mx-4">                             
                            <a class="nav-link" href="<%= Navigation.MAIN_DASHBOARD%>">Dashboard</a>
                        </li>

                        <li class="nav-item dropdown mx-4">
                            <a class="nav-link dropdown-toggle" href="#"  data-bs-toggle="dropdown" aria-expanded="false">
                                Accounts
                            </a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="<%= Navigation.ADD_ACCOUNT%>">Add new</a></li>
                                <li><a class="dropdown-item" href="AccountController?action=<%= Action.LIST_ACCOUNT%>">Get list</a></li>

                            </ul>
                        </li>

                        <li class="nav-item dropdown mx-4">
                            <a class="nav-link dropdown-toggle" href="#"  data-bs-toggle="dropdown" aria-expanded="false">
                                Categories
                            </a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="<%= Navigation.ADD_CATEGORY%>">Add new</a></li>
                                <li><a class="dropdown-item" href="CategoryController?action=<%= Action.LIST_CATEGORY%>">Get list</a></li>

                            </ul>
                        </li>

                        <li class="nav-item dropdown mx-4">
                            <a class="nav-link dropdown-toggle" href="#"  data-bs-toggle="dropdown" aria-expanded="false">
                                Products
                            </a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="<%= Navigation.ADD_PRODUCT%>">Add new</a></li>
                                <li><a class="dropdown-item" href="ProductController?action=<%= Action.LIST_PRODUCT%>&cate=0">Get list</a></li>

                            </ul>
                        </li>
                    </ul>

                </div>

                <a href="GuestController?action=<%= Action.LOGOUT%>" class="btn btn-danger">Logout</a>
            </div>              
        </nav>



    </body>
</html>