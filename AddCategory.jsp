<%-- 
    Document   : AddCategory
    Created on : Feb 25, 2025, 7:06:54 PM
    Author     : USER
--%>

<%@page import="context.Navigation"%>
<%@page import="context.Action"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Add New Category</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
       <link rel="shortcut icon" href="images/web_logo.png">
    </head>
    <body >
        <c:if test="${login == null}">
            <jsp:forward page = "Login.jsp"></jsp:forward>
        </c:if>
        
        <jsp:include page="Dashboard.jsp"></jsp:include>
        
        <div class="container" style="width: 50%">

            <h2 class="my-4 text-center">Add new category</h2>
            <form  action="CategoryController" method="POST">
                <div class="row mb-3">
                    <label class="col-form-label col-sm-2 fw-bold" for="cateName">Category name: </label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" minlength="2" maxlength="30" placeholder="Enter category name" name="cateName" required="">
                         <c:if test="${categoryMsg != null}">
                            <small style="color: red">* ${categoryMsg}</small>
                        </c:if>
                    </div>
                </div>

                <div class="row mb-3">
                    <label class="col-form-label col-sm-2 fw-bold" for="memo">Memo: </label>
                    <div class="col-sm-10">          
                        <textarea class="form-control" aria-label="With textarea" rows="7" name="memo"></textarea>
                    </div>
                </div>

                <div class="row mb-3">        
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-outline-secondary" name="action" value="<%= Action.ADD_CATEGORY %>">Save</button>
                    </div>
                     <div class="col-sm-offset-2 col-sm-2" >
                        <a href="<%= Navigation.LIST_CATEGORY %>" class="btn btn-warning" style="margin-left: 30px">Cancel</a>
                    </div>
                </div>
            </form>

        </div>
    </body>
</html>