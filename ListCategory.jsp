<%-- 
    Document   : ListCategory
    Created on : Mar 1, 2025, 2:30:50 PM
    Author     : USER
--%>
<%@page import="model.Category"%>
<%@page import="java.util.List"%>
<%@page import="context.Navigation"%>
<%@page import="context.Action"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>List Categories Page</title>
        <link rel="shortcut icon" href="images/web_logo.png">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" 
              integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" 
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="<%= Navigation.DASHBOARD%>"></jsp:include>

            <div class="container">
                <h2 class="text-center mt-4">List All Categories</h2>

                <!-- Search and Add functionality row -->
                <div class="row mb-3">
                    <div class="col">
                        <a href="<%= Navigation.ADD_CATEGORY%>" class="btn btn-primary">
                        <i class="fas fa-plus"></i> Add New Category
                    </a>
                </div>
                <div class="col-auto">
                    <form action="CategoryController" method="get" class="d-flex">
                        <input type="hidden" name="action" value="<%= Action.LIST_CATEGORY%>">
                        <input type="text" name="searchQuery" class="form-control me-2" placeholder="Search categories...">
                        <button type="submit" class="btn btn-outline-primary">
                            <i class="fas fa-search"></i>
                        </button>
                    </form>
                </div>
            </div>

            <table class="table table-hover">
                <thead class="table-info">
                    <tr>
                        <th scope="col">Type ID</th>
                        <th scope="col">Category Name</th>
                        <th scope="col">Memo</th>                   
                        <th scope="col">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%-- Lấy dữ liệu từ session --%>
                    <c:set var="listCategory" value="${requestScope.listCategory != null ? requestScope.listCategory : sessionScope.listCategory}" />


                    <c:forEach items="${listCategory}" var="a">
                        <tr id="${a.typeId}">
                            <td>${a.typeId}</td>
                            <td>${a.categoryName}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${a.memo != null}">${a.memo}</c:when>
                                    <c:otherwise><span class="text-muted">No memo</span></c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:url var="updateUrl" value="<%= Navigation.UPDATE_CATEGORY%>">
                                    <c:param name="category" value="${a.typeId}"></c:param>
                                </c:url>
                                <c:url var="deleteUrl" value="CategoryController">
                                    <c:param name="action" value="<%= Action.DELETE_CATEGORY%>"></c:param>
                                    <c:param name="category" value="${a.typeId}"></c:param>
                                </c:url>
                                <div class="btn-group">
                                    <a href="CategoryController?action=<%= Action.UPDATE_CATEGORY%>&category=${a.typeId}" class="btn btn-warning btn-sm">
                                        <i class="fas fa-edit"></i> Update
                                    </a>
                                    <a href="CategoryController?action=<%= Action.DELETE_CATEGORY%>&category=${a.typeId}" 
                                       onclick="return confirm('Do you really want to delete category ${a.categoryName}? This action cannot be undone.')" 
                                       class="btn btn-danger btn-sm">
                                        <i class="fas fa-trash"></i> Delete
                                    </a>
                                </div>
                            </td>
                        </tr>  
                    </c:forEach>

                    <%-- Hiển thị thông báo nếu không có dữ liệu --%>
                    <c:if test="${empty listCategory}">
                        <tr>
                            <td colspan="4" class="text-center">No categories found in the database.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>

            <!-- Pagination if needed -->
            <c:if test="${totalPages > 1}">
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <li class="page-item ${currentPage == i ? 'active' : ''}">
                                <a class="page-link" href="CategoryController?action=<%= Action.LIST_CATEGORY%>&page=${i}">
                                    ${i}
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
            </c:if>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <script>
                                           document.addEventListener('DOMContentLoaded', function () {
                                               // Highlight row of last modified category if parameter exists
                                               const urlParams = new URLSearchParams(window.location.search);
                                               const lastModified = urlParams.get('lastModified');
                                               if (lastModified) {
                                                   const row = document.getElementById(lastModified);
                                                   if (row) {
                                                       row.classList.add('table-warning');
                                                       setTimeout(() => {
                                                           row.classList.remove('table-warning');
                                                       }, 3000);
                                                   }
                                               }

                                               console.log("List Category Data:", ${not empty listCategory});
                                           });
        </script>
    </body>
</html>