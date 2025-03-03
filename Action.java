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
public interface Action {

    // Action with Authentication 
    static final String LOGIN = "Login";
    static final String LOGOUT = "Logout";
    static final String REGISTER = "RegisterAccount";
    static final String ADD_CART_SHOP = "AddCartShop";
    static final String ORDER = "Order";
    
    // Action with Account 
    static final String ADD_ACCOUNT = "AddAccount";
    static final String UPDATE_ACCOUNT = "UpdateAccount";
    static final String LIST_ACCOUNT = "ListAccount";
    static final String DELETE_ACCOUNT = "DeleteAccount";
    static final String ISUSE_UPDATE_ACCOUNT = "IsUseUpdateAccount";

    // Action with Category
    static final String ADD_CATEGORY = "AddCategory";
    static final String UPDATE_CATEGORY = "UpdateCategory";
    static final String LIST_CATEGORY = "ListCategory";
    static final String DELETE_CATEGORY = "DeleteCategory";

    // Action with Product
    static final String ADD_PRODUCT = "AddProduct";
    static final String UPDATE_PRODUCT = "UpdateProduct";
    static final String LIST_PRODUCT = "ListProduct";
    static final String DELETE_PRODUCT = "DeleteProduct";
    static final String CHANGE_IMG_PRODUCT = "ChangeImgProduct";
    static final String LIST_PRODUCT_BY_NAME = "ListProductByName";
    static final String LIST_PRODUCT_BY_CATEGORY = "ListProductByCategory";
    static final String LIST_PRODUCT_BY_CATE_PUBLIC = "ListProductByCatePublic";

}