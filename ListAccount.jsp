<%-- 
    Document   : ListAccount
    Created on : Feb 25, 2025, 7:08:03 PM
    Author     : USER
--%>
<%@page import="context.Action"%>
<%@page import="context.Navigation"%>
<%@page import="java.util.List"%>
<%@page import="model.Account"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>List Account Page</title>
        <link rel="shortcut icon" href="images/web_logo.png">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" 
              integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" 
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>

    <body>
        <jsp:include page="<%= Navigation.DASHBOARD%>"></jsp:include>

            <div class="container">
                <h2 class="text-center mt-4">List All Accounts</h2>

                <!-- Add search or filter controls if needed -->
                <div class="row mb-3">
                    <div class="col">
                        <a href="<%= Navigation.ADD_ACCOUNT%>" class="btn btn-primary">
                        <i class="fas fa-plus"></i> Add New Account
                    </a>
                </div>
                <div class="col-auto">
                    <form action="AccountController" method="get" class="d-flex">
                        <input type="hidden" name="action" value="<%= Action.LIST_ACCOUNT%>">
                        <input type="text" name="searchQuery" class="form-control me-2" placeholder="Search accounts...">
                        <button type="submit" class="btn btn-outline-primary">
                            <i class="fas fa-search"></i>
                        </button>
                    </form>
                </div>
            </div>

            <table class="table table-hover">
                <thead class="table-info">
                    <tr>
                        <th scope="col">Account</th>
                        <th scope="col">Full name</th>
                        <th scope="col">Birthday</th>
                        <th scope="col">Gender</th>
                        <th scope="col">Phone</th>
                        <th scope="col">Role in system</th>
                        <th scope="col">Status</th>
                        <th scope="col">Action</th>
                    </tr>
                </thead>
                <tbody>                          
                    <c:forEach items="${listAccount}" var="a">
                        <tr id="${a.account}" class="${a.isUse ? '' : 'table-secondary'}">
                            <td>${a.account}</td>
                            <td>${a.lastName}, ${a.firstName}</td>
                            <td><fmt:formatDate value="${a.birthday}" pattern="dd-MM-yyyy"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${a.gender}">Male</c:when>
                                    <c:otherwise>Female</c:otherwise>
                                </c:choose>
                            </td>
                            <td>${a.phone}</td>
                            <td>${a.roleInSystem}</td>
                            <td>
                                <span class="badge ${a.isUse ? 'bg-success' : 'bg-danger'}">
                                    ${a.isUse ? 'Active' : 'Inactive'}
                                </span>
                            </td>
                            <td>
                                <c:url var="updateUrl" value="<%= Navigation.UPDATE_ACCOUNT%>">
                                    <c:param name="account" value="${a.account}"></c:param>
                                </c:url>
                                <c:url var="isUseUpdateUrl" value="AccountController">
                                    <c:param name="action" value="<%= Action.ISUSE_UPDATE_ACCOUNT%>"></c:param>
                                    <c:param name="account" value="${a.account}"></c:param>
                                </c:url>
                                <c:url var="deleteUrl" value="AccountController">
                                    <c:param name="action" value="<%= Action.DELETE_ACCOUNT%>"></c:param>
                                    <c:param name="account" value="${a.account}"></c:param>
                                </c:url>

                                <div class="btn-group">
                                    <c:choose>
                                        <c:when test="${login.account != a.account}">
                                            <a href="AccountController?action=<%= Action.UPDATE_ACCOUNT%>&account=${a.account}" class="btn btn-warning btn-sm">
                                                <i class="fas fa-edit"></i> Update
                                            </a>
                                            <a href="AccountController?action=<%= Action.ISUSE_UPDATE_ACCOUNT%>&account=${a.account}" 
                                               onclick="return confirm('Do you want to change status of account ${a.account}?')" 
                                               class="btn btn-${a.isUse ? 'danger' : 'success'} btn-sm">
                                                <i class="fas fa-${a.isUse ? 'ban' : 'check'}"></i>
                                                ${a.isUse ? 'Deactivate' : 'Activate'}
                                            </a>
                                            <a href="AccountController?action=<%= Action.DELETE_ACCOUNT%>&account=${a.account}" 
                                               onclick="return confirm('Do you really want to delete account ${a.account}? This action cannot be undone.')" 
                                               class="btn btn-danger btn-sm">
                                                <i class="fas fa-trash"></i> Delete
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="#" class="btn btn-warning btn-sm disabled">
                                                <i class="fas fa-edit"></i> Update
                                            </a>
                                            <a href="#" class="btn btn-${a.isUse ? 'danger' : 'success'} btn-sm disabled">
                                                <i class="fas fa-${a.isUse ? 'ban' : 'check'}"></i>
                                                ${a.isUse ? 'Deactivate' : 'Activate'}
                                            </a>
                                            <a href="#" class="btn btn-danger btn-sm disabled">
                                                <i class="fas fa-trash"></i> Delete
                                            </a>
                                            <span class="text-muted ms-2">(Cannot modify own account)</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </td>
                        </tr>  
                    </c:forEach>
                    <c:if test="${empty listAccount}">
                        <tr>
                            <td colspan="8" class="text-center">No accounts found in the database.</td>
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
                                <a class="page-link" href="AccountController?action=<%= Action.LIST_ACCOUNT%>&page=${i}">
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
                                                   // Add any JavaScript needed for the page
                                                   document.addEventListener('DOMContentLoaded', function () {
                                                       // Example: Highlight the row of the last modified account
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
                                                   });
        </script>
    </body>
</html>