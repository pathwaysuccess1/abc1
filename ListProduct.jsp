<%-- 
    Document   : ListProduct
    Created on : Jun 21, 2024, 12:05:17 PM
    Author     : ACER
    Usage      : List products file
--%>

<%@page import="model.ProductDAO"%>
<%@page import="model.Category"%>
<%@page import="model.CategoryDAO"%>
<%@page import="context.Action"%>
<%@page import="context.Navigation"%>
<%@page import="model.Product"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>List Product Page</title>
        <link rel="shortcut icon" href="images/web_logo.png">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" 
              integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" 
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="<%= Navigation.DASHBOARD%>"></jsp:include>

            <div class="container">
                <h2 class="text-center mt-4">List All Products</h2>

                <div class="row mb-3">
                    <div class="col">
                        <a href="<%= Navigation.ADD_PRODUCT%>" class="btn btn-primary">
                        <i class="fas fa-plus"></i> Add New Product
                    </a>
                </div>
                <div class="col-auto">
                    <form action="ProductController" method="get" class="d-flex">
                        <input type="hidden" name="action" value="<%= Action.LIST_PRODUCT%>">
                        <input type="text" name="searchQuery" class="form-control me-2" placeholder="Search products...">
                        <button type="submit" class="btn btn-outline-primary">
                            <i class="fas fa-search"></i>
                        </button>
                    </form>
                </div>
            </div>

            <!-- Category Filter Dropdown -->
            <div class="row mb-3">
                <div class="col-auto ms-auto">
                    <div class="dropdown">
                        <button type="button" class="btn btn-info dropdown-toggle" data-bs-toggle="dropdown">
                            <i class="fas fa-filter"></i> Filter by Category
                        </button>
                        <ul class="dropdown-menu">
                            <c:set var="list" value="<%= new CategoryDAO().listAll()%>"></c:set>
                            <c:forEach var="i" items="${list}">
                                <li><a class="dropdown-item" href="ProductController?action=<%= Action.LIST_PRODUCT%>&cate=${i.typeId}" id="${i.typeId}">${i.categoryName}</a></li>
                                </c:forEach>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="ProductController?action=<%= Action.LIST_PRODUCT%>&cate=0" id="0">All Products</a></li>
                        </ul>
                    </div>
                </div>
            </div>

            <table class="table table-hover">
                <thead class="table-info">
                    <tr>
                        <th scope="col">Product Name</th>
                        <th scope="col">Brief</th>
                        <th scope="col">Category</th>
                        <th scope="col">Posted Date</th>
                        <th scope="col">Unit</th>
                        <th scope="col">Price</th>
                        <th scope="col">Discount</th>
                        <th scope="col">Action</th>
                    </tr>
                </thead>
                <tbody>               
                        <c:forEach items="${listProduct}" var="i">
                            <tr id="${i.productId}">
                                <td>${i.productName}</td>
                                <td>${i.brief}</td>
                                <td>${i.typeId.categoryName}</td>
                                <td><fmt:formatDate value="${i.postedDate}" pattern="dd-MM-yyyy HH:mm"/></td>
                                <td>${i.unit}</td>
                                <td><fmt:formatNumber value="${i.price}" type="currency" currencySymbol="₫"/></td>
                                <td>${i.discount}%</td>
                                <td>
                                    <c:url var="updateUrl" value="<%= Navigation.UPDATE_PRODUCT%>">
                                        <c:param name="product" value="${i.productId}"></c:param>
                                    </c:url>
                                    <c:url var="deleteUrl" value="ProductController">
                                        <c:param name="action" value="<%= Action.DELETE_PRODUCT%>"></c:param>
                                        <c:param name="product" value="${i.productId}"></c:param>
                                    </c:url>

                                    <div class="btn-group">
                                        <a href="ProductController?action=<%= Action.UPDATE_PRODUCT%>&product=${i.productId}" class="btn btn-warning btn-sm">
                                            <i class="fas fa-edit"></i> Update
                                        </a>
                                        <a href="ProductController?action=<%= Action.DELETE_PRODUCT%>&product=${i.productId}" 
                                           onclick="return confirm('Do you really want to delete product ${i.productName}? This action cannot be undone.')" 
                                           class="btn btn-danger btn-sm">
                                            <i class="fas fa-trash"></i> Delete
                                        </a>
                                    </div>
                                    </a>
                                    </div>
                                </td>
                            </tr>  
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty listProduct}">
                        <tr>
                            <td colspan="8" class="text-center">No products found in the database.</td>
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
                                <a class="page-link" href="ProductController?action=<%= Action.LIST_PRODUCT%>&cate=${param.cate}&page=${i}">
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
                                                   // Example: Highlight the row of the last modified product
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