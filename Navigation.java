/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package context;

/**
 *
 * @author USER
 */
public interface Navigation {

    // Navigation to Login 
    static final String LOGIN = "Login.jsp";
    static final String REGISTER = "Register.jsp";
    
    // Navigation to Invalid 
    static final String INVALID = "invalid.html";

    // Navigation to Private Page 
    static final String WELCOME = "Welcome.jsp";
    static final String DASHBOARD = "Dashboard.jsp";
    static final String MAIN_DASHBOARD = "MainDashboard.jsp";

    // Navigation to Account Page 
    static final String ADD_ACCOUNT = "AddAccount.jsp";
    static final String LIST_ACCOUNT = "ListAccount.jsp";
    static final String UPDATE_ACCOUNT = "UpdateAccount.jsp";

    // Navigation to Category Page 
    static final String ADD_CATEGORY = "AddCategory.jsp";
    static final String LIST_CATEGORY = "ListCategory.jsp";
    static final String UPDATE_CATEGORY = "UpdateCategory.jsp";

    // Navigation to Product Page 
    static final String ADD_PRODUCT = "AddProduct.jsp";
    static final String LIST_PRODUCT = "ListProduct.jsp";
    static final String UPDATE_PRODUCT = "UpdateProduct.jsp";

    // Navigation to Product View Page 
    static final String PUBLIC_PRODUCT = "ProductPublic.jsp";
    static final String PRODUCT_DETAIL = "ProductDetail.jsp";
    
    // Navigation to Footer 
}