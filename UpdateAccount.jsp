<%-- 
    Document   : UpdateAccount
    Created on : Feb 25, 2025, 7:09:48 PM
    Author     : USER
--%>

<%@page import="context.Action"%>
<%@page import="context.Navigation"%>
<%@page import="model.AccountDAO"%>
<%@page import="model.Account"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Update Account</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="shortcut icon" href="images/web_logo.png">
    </head>
    <body > 

        <jsp:include page="Dashboard.jsp"></jsp:include>

        <%
            String account = (String) request.getParameter("account");
            Account updateAccount = new AccountDAO().getObjectById(account);
        %>

        <div class="container" >

            <h2 class="my-4 text-center">Update account <span style="color: red"><%= updateAccount.getAccount()%> </span></h2>
            <form  action="AccountController" method="POST">
                <div class="row mb-3">
                    <label class="col-form-label col-sm-2" for="account">Account</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control"  name="account" value="<%= updateAccount.getAccount()%>" readonly="">
                    </div>
                </div>

                <div class="row mb-3">
                    <label class="col-form-label col-sm-2" for="pass">Password </label>
                    <div class="col-sm-10">          
                        <input type="password" class="form-control" placeholder="Enter password" name="pass" value="<%= updateAccount.getPass()%>" required="">
                    </div>
                </div>

                <div class="row mb-3">
                    <label class="col-form-label col-sm-2" for="lastName">Last name </label>
                    <div class="col-sm-10">          
                        <input type="text" class="form-control"  placeholder="Last name" minlength="2" maxlength="15" name="lastName" value="<%= updateAccount.getLastName()%>" required="">
                    </div>
                </div>

                <div class="row mb-3">
                    <label class="col-form-label col-sm-2" for="firstName">First name </label>
                    <div class="col-sm-10">          
                        <input type="text" class="form-control"  placeholder="First name" minlength="2" maxlength="15" name="firstName" value="<%= updateAccount.getFirstName()%>" required="">
                    </div>
                </div>

                <div class="row mb-3">
                    <label class="col-form-label col-sm-2" for="phone">Phone </label>
                    <div class="col-sm-10">          
                        <input type="phone" class="form-control"  placeholder="Phone number" pattern="[0][0-9]{9}" name="phone" value="<%= updateAccount.getPhone()%>" required="">
                    </div>
                </div>

                <div class="row mb-3">
                    <label class="col-form-label col-sm-2" for="birthday">Birthday </label>
                    <div class="col-sm-10">          
                        <input type="date" class="form-control" name="birthday" 
                               value="<%=  updateAccount.getBirthday() %>"  required="">
                    </div>
                </div>


                <label class="col-form-label col-sm-2" for="birthday">Gender </label>
                <div class="form-check form-check-inline">          
                    <input class="form-check-input" type="radio" name="gender"  value="1" checked="">
                    <label class="form-check-label" for="gender">Male</label>
                </div>
                <div class="form-check form-check-inline">          
                    <input class="form-check-input" type="radio" name="gender"  value="0">
                    <label class="form-check-label" for="gender">Female</label>
                </div>

                <div class="row mb-3">
                    <label class="col-form-label col-sm-2" for="roleInSystem">Role in system </label>
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
                            <label><input type="checkbox" name="isUse" value="1" checked=""> Is active</label>
                        </div>
                    </div>
                </div>

                <div class="row mb-3">        
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-outline-secondary" name="action" value="<%= Action.UPDATE_ACCOUNT%>">Update</button>
                    </div>

                    <div class="col-sm-offset-2 col-sm-2" >
                        <a href="<%= Navigation.LIST_ACCOUNT%>" class="btn btn-warning" style="margin-left: 100px">Cancel</a>
                    </div>
                </div>

                <div class="row mb-3">        

                </div>
            </form>






    </body>
</html>