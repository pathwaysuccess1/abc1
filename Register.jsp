
<%-- 
    Document   : Register
    Created on : Jun 8, 2024, 7:40:50 AM
    Author     : ACER
    Usage      : Register an account 
--%>

<%@page import="context.Action"%>
<%@page import="context.Navigation"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Register Account</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="shortcut icon" href="images/web_logo.png">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body >
       
        <div class="container">
                <h2 class="my-4 text-center">Register Account</h2>
                <form  action="GuestController" method="POST"  style="width: 60%; margin: 0 auto">
                    <div class="row mb-3">
                        <label class="col-form-label col-sm-2 fw-bold" for="account">Account</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control"  placeholder="Enter account" name="account" minlength="3" maxlength="15" required="">
                        <c:if test="${accountMsg != null}">
                            <small style="color: red">* ${accountMsg}</small>
                        </c:if>
                    </div>

                </div>

                <div class="row mb-3">
                    <label class="col-form-label col-sm-2 fw-bold" for="pass">Password </label>
                    <div class="col-sm-10">          
                        <input type="password" class="form-control" placeholder="Enter password" name="pass" minlength="3" maxlength="15" required="">
                    </div>
                </div>

                <div class="row mb-3">
                    <label class="col-form-label col-sm-2 fw-bold" for="lastName">Last name </label>
                    <div class="col-sm-10">          
                        <input type="text" class="form-control"  placeholder="Last name" minlength="2" maxlength="15" name="lastName" required="">
                    </div>
                </div>

                <div class="row mb-3">
                    <label class="col-form-label col-sm-2 fw-bold" for="firstName">First name </label>
                    <div class="col-sm-10">          
                        <input type="text" class="form-control"  placeholder="First name" minlength="2" maxlength="15" name="firstName" required="">
                    </div>
                </div>

                <div class="row mb-3">
                    <label class="col-form-label col-sm-2 fw-bold" for="phone">Phone </label>
                    <div class="col-sm-10">          
                        <input type="phone" class="form-control"  placeholder="Phone number" name="phone" pattern="[0][0-9]{9}" required="">
                    </div>
                </div>

                <div class="row mb-3">
                    <label class="col-form-label col-sm-2 fw-bold" for="birthday">Birthday </label>
                    <div class="col-sm-10">          
                        <input type="date" class="form-control"  placeholder="First name" name="birthday" 
                               max="2010-01-01"
                               pattern="(?:19|20)[0-9]{2}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))" required="">
                    </div>
                </div>


                <label class="col-form-label col-sm-2 fw-bold" for="birthday">Gender </label>
                <div class="form-check form-check-inline">          
                    <input class="form-check-input" type="radio" name="gender"  value="1" checked="">
                    <label class="form-check-label" for="gender">Male</label>
                </div>
                <div class="form-check form-check-inline">          
                    <input class="form-check-input" type="radio" name="gender"  value="0">
                    <label class="form-check-label" for="gender">Female</label>
                </div>

         
                <div class="row mb-3">        
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-outline-secondary" value="<%= Action.REGISTER %>" name="action">Submit</button>
                    </div>
                    <div class="col-sm-offset-2 col-sm-2" >
                        <a href="<%= Navigation.WELCOME %>" class="btn btn-warning" style="margin-left: 100px">Cancel</a>
                    </div>
                </div>
            </form>

        </div>
    </body>
</html>
