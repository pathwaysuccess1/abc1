<%@page import="org.eclipse.jdt.core.compiler.CategorizedProblem"%>
<%@page import="context.Navigation"%>
<%@page import="context.Action"%>
<%@page import="model.Category"%>
<%@page import="model.CategoryDAO"%>
<%@page import="model.Product"%>
<%@page import="model.ProductDAO"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Product</title>
        <link rel="shortcut icon" href="images/web_logo.png">
    </head>
    <body>
        <c:if test="${login == null}">
            <jsp:forward page="Login.jsp" />
        </c:if>

        <jsp:include page="MainDashboard.jsp" />

        <%
            String updateProduct = request.getParameter("product");
            ProductDAO productDAO = new ProductDAO();
            Product product = productDAO.getObjectById(updateProduct);
            int type = (product != null && product.getType() != null) ? product.getType().getTypeId() : -1;
        %>
        
        <div class="container" style="width: 60%">
            <h2 class="my-4 text-center">Update Product</h2>
            <form action="ProductController" method="POST" enctype="multipart/form-data">
                <div class="row mb-3">
                    <label class="col-form-label col-sm-2 fw-bold" for="id">Product ID</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="id" value="<%= (product != null) ? product.getProductId() : "" %>" readonly>
                    </div>
                </div>
                
                <div class="row mb-3">
                    <label class="col-form-label col-sm-2 fw-bold" for="name">Product Name</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="name" value="<%= (product != null) ? product.getProductName() : "" %>" required>
                    </div>
                </div>
                
                <div class="row mb-3">
                    <label class="col-form-label col-sm-2 fw-bold" for="brief">Brief</label>
                    <div class="col-sm-10">
                        <textarea class="form-control" name="brief" required><%= (product != null) ? product.getBrief() : "" %></textarea>
                    </div>
                </div>
                
                <div class="row mb-3">
                    <label class="col-form-label col-sm-2 fw-bold" for="unit">Unit</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="unit" value="<%= (product != null) ? product.getUnit() : "" %>" required>
                    </div>
                </div>
                
                <div class="row mb-3">
                    <label class="col-form-label col-sm-2 fw-bold" for="price">Price</label>
                    <div class="col-sm-10">
                        <input type="number" class="form-control" name="price" value="<%= (product != null) ? product.getPrice() : "" %>">
                    </div>
                </div>
                
                <div class="row mb-3">
                    <label class="col-form-label col-sm-2 fw-bold" for="discount">Discount</label>
                    <div class="col-sm-10">
                        <input type="number" class="form-control" name="discount" value="<%= (product != null) ? product.getDiscount() : "" %>">
                    </div>
                </div>
                
                <div class="row mb-3">
                    <label class="col-form-label col-sm-2 fw-bold" for="type">Type Name</label>
                    <div class="col-sm-10">
                        <select class="form-select" name="type">
                            <%
                                CategoryDAO dao = new CategoryDAO();
                                List<Category> list = dao.listAll();
                                for (Category c : list) {
                            %>
                            <option value="<%= c.getTypeId() %>" <%= (c.getTypeId() == type) ? "selected" : "" %>>
                                <%= c.getCategoryName() %>
                            </option>
                            <% } %>
                        </select>
                    </div>
                </div>
                
                <div class="row mb-3">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-outline-secondary" name="action" value="<%= Action.UPDATE_PRODUCT %>">Submit</button>
                        <a href="<%= Navigation.LIST_PRODUCT %>" class="btn btn-warning" style="margin-left: 60px">Cancel</a>
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>
