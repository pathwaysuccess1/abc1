<%-- 
    Document   : UpdateCategory
    Created on : Feb 25, 2025, 7:09:57 PM
    Author     : USER
--%>

<%@page import="context.Action"%>
<%@page import="context.Navigation"%>
<%@page import="model.Category"%>
<%@page import="model.CategoryDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Category Page</title>
        <link rel="shortcut icon" href="images/web_logo.png">
    </head>
    <body>
        <c:if test="${login == null}">
            <jsp:forward page = "Login.jsp"></jsp:forward>
        </c:if>

        <jsp:include page="Dashboard.jsp"></jsp:include>

        <%
            String cate = request.getParameter("category");
            Category updateCategory = new CategoryDAO().getObjectById(cate);
        %>
        <div class="container" style="width: 50%">

            <h2 class="my-4 text-center">Update category <span style="color: red"><%= updateCategory.getTypeId()%></span></h2>
                <% if (request.getAttribute("categoryUpdateMsg") != null) {%>
            <div class="alert alert-danger">
                <%= request.getAttribute("categoryUpdateMsg")%>
            </div>
            <% }%>
            <form  action="CategoryController" method="POST">
                <div class="row mb-3 d-none">
                    <label class="col-form-label col-sm-2 fw-bold" for="typeId">Category Id </label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control"  placeholder="Enter category name" name="typeId" required="" value="<%= updateCategory.getTypeId()%>">
                    </div>
                </div>

                <div class="row mb-3">
                    <label class="col-form-label col-sm-2 fw-bold" for="cateName">Category name: </label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control"  placeholder="Enter category name" minlength="2" maxlength="30" name="cateName" required="" value="<%= updateCategory.getCategoryName()%>">
                        <c:if test="${categoryUpdateMsg != null}">
                            <small style="color: red">* ${categoryUpdateMsg}</small>
                        </c:if>
                    </div>
                </div>

                <div class="row mb-3">
                    <label class="col-form-label col-sm-2 fw-bold" for="memo">Memo: </label>
                    <div class="col-sm-10">          
                        <textarea class="form-control" aria-label="With textarea" name="memo" placeholder="Enter memo (optional)"><%=updateCategory.getMemo()%></textarea>
                    </div>
                </div>

                <div class="row mb-3">        
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-outline-secondary" name="action" value="<%= Action.UPDATE_CATEGORY%>">Save</button>
                    </div>

                    <div class="col-sm-offset-2 col-sm-2" >
                        <a href="<%= Navigation.LIST_CATEGORY%>" class="btn btn-warning" style="margin-left: 30px">Cancel</a>
                    </div>
                </div>
            </form>

        </div>
    </body>
</html>