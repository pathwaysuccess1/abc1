<%-- 
    Document   : AddAcount
    Created on : Feb 25, 2025, 7:06:18 PM
    Author     : USER
--%>

<%@page import="context.Navigation"%>
<%@page import="context.Action"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Add New Account</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="shortcut icon" href="images/web_logo.png">
    </head>
    <body >
        <c:if test="${login == null }">
            <jsp:forward page = "Login.jsp"></jsp:forward>
        </c:if>

        <jsp:include page="Dashboard.jsp"></jsp:include>

            <div class="container" >

                <h2 class="my-4 text-center">Add new account</h2>
                <form  action="AccountController" method="POST">
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
                    <label class="col-form-label col-sm-2 fw-bold" for="roleInSystem">Role in system </label>
                    <div class="col-sm-10">          
                        <select class="form-select"  name="roleInSystem">
                            <option value="administrator">Administrator</option>
                            <option value="staff">Staff</option>   
                            <option value="customer">Customer</option>  
                        </select>
                    </div>
                </div>

                <div class="row mb-3">        
                    <div class="col-sm-offset-2 col-sm-10">
                        <div class="checkbox">
                            <label ><input type="checkbox" name="isUse" value="1"> Is active</label>
                        </div>
                    </div>
                </div>

                <div class="row mb-3">        
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-outline-secondary" value="<%= Action.ADD_ACCOUNT%>" name="action">Submit</button>
                    </div>
                    <div class="col-sm-offset-2 col-sm-2" >
                        <a href="<%= Navigation.LIST_ACCOUNT%>" class="btn btn-warning" style="margin-left: 100px">Cancel</a>
                    </div>
                </div>
            </form>

        </div>
    </body>
</html>
