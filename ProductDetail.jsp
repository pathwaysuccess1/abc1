<%-- 
    Document   : ProductDetail
    Created on : Feb 25, 2025, 7:08:57 PM
    Author     : USER
--%>

<%@page import="context.Action"%>
<%@page import="model.Product"%>
<%@page import="model.ProductDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add To Cart</title>
        <link rel="shortcut icon" href="images/web_logo.png">
        <link rel="stylesheet" type="text/css" href="css/productDetail.css"> 
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body>
        <%
            String id = request.getParameter("product");
            Products product = new ProductsBLO().getObjectById(id);
        %>
        <c:set var="p" scope="request"  value="<%= product%>" />
        <div class="container">
            <div class="card">
                <div class="container-fliud">
                    <div class="wrapper row">
                        <div class="preview col-md-6">

                            <div class="preview-pic tab-content">
                                <div class="tab-pane active"  style="width: 70%; height: 70%; margin: 0 auto">
                                    <img src=".${p.productImage}" alt="product-image"/>
                                </div>                              
                            </div>


                        </div>
                        <div class="details col-md-6">
                            <h3 class="product-title">Cường's Store</h3>
                            <div class="rating">
                                <div class="stars">
                                    <span class="fa fa-star checked"></span>
                                    <span class="fa fa-star checked"></span>
                                    <span class="fa fa-star checked"></span>
                                    <span class="fa fa-star"></span>
                                    <span class="fa fa-star"></span>
                                </div>
                                <span class="review-no">41 reviews</span>
                            </div>
                            <p class="product-description">${p.brief}</p>
                            <h4 class="price">Current price: <span><format:style price="${p.price}" sale="${p.discount}" /></span></h4>
                            <p class="vote"><strong>91%</strong> of buyers enjoyed this product! <strong>(87 votes)</strong></p>

                            <div class="action">
                                Quantity: <input id="number" type="number" value="1" name="quantity" min="1" style="width:10%; border-radius: 5px; margin-bottom: 10px" />
                                <br/>

                                <button class=" add-to-cart btn btn-default" type="submit" name="action" value="<%= Action.ADD_CART_SHOP %>">Add to cart</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>