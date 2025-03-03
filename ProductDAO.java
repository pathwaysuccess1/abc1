/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import utils.ConnectDB;

/**
 *
 * @author USER
 */
public class ProductDAO implements Accessible<Product> {

    private ServletContext sc;
    private Connection con;

    public ProductDAO() throws ClassNotFoundException, SQLException {
        ConnectDB connectDB = new ConnectDB();
        con = connectDB.getConnection();
    }

    public ProductDAO(ServletContext sc) throws ClassNotFoundException, SQLException {
        this.sc = sc;
        con = getConnect(sc);
    }

    private Connection getConnect(ServletContext sc) throws ClassNotFoundException, SQLException {
        ConnectDB connectDB = new ConnectDB(sc);
        return connectDB.getConnection();
    }

    @Override
    public int insertRec(Product o) {
        int result = 0;
        String query = "INSERT INTO products (productId, productName, productImage, brief, postedDate, categoryId, accountId, unit, price, discount) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, o.getProductId());
            pstmt.setString(2, o.getProductName());
            pstmt.setString(3, o.getProductImage());
            pstmt.setString(4, o.getBrief());
            pstmt.setDate(5, new java.sql.Date(o.getPostedDate().getTime()));
            pstmt.setInt(6, o.getType().getTypeId()); // Lấy categoryId từ đối tượng Category
            pstmt.setString(7, o.getAccount().getAccount());
            pstmt.setString(8, o.getUnit());
            pstmt.setInt(9, o.getPrice());
            pstmt.setInt(10, o.getDiscount());

            result = pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * Update information of a product
     */
    @Override
    public int updateRec(Product o) {
        int result = 0;
        String query = "UPDATE products SET productName=?, productImage=?, brief=?, postedDate=?, categoryId=?, "
                + "accountId=?, unit=?, price=?, discount=? WHERE productId=?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, o.getProductName());
            pstmt.setString(2, o.getProductImage());
            pstmt.setString(3, o.getBrief());
            pstmt.setDate(4, new java.sql.Date(o.getPostedDate().getTime()));
            pstmt.setInt(5, o.getType().getTypeId());
            pstmt.setString(6, o.getAccount().getAccount());
            pstmt.setString(7, o.getUnit());
            pstmt.setInt(8, o.getPrice());
            pstmt.setInt(9, o.getDiscount());
            pstmt.setString(10, o.getProductId());

            result = pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * Remove product from database
     */
    @Override
    public int deleteRec(Product o) {
        int result = 0;
        String query = "DELETE FROM products WHERE productId=?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, o.getProductId());
            result = pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * Get all products by categoryId
     */
    public List<Product> listByCategory(int categoryId) {
        List<Product> products = new ArrayList<>();
       String query = "SELECT p.*, c.categoryName, c.memo, a.userName, a.email " +
             "FROM products p " +
             "LEFT JOIN categories c ON p.categoryId = c.categoryId " +
             "LEFT JOIN accounts a ON p.accountId = a.accountId " +
             "WHERE p.categoryId = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, categoryId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("productId"));
                product.setProductName(rs.getString("productName"));
                product.setProductImage(rs.getString("productImage"));
                product.setBrief(rs.getString("brief"));
                product.setPostedDate(rs.getDate("postedDate"));
                product.setType(new Category(rs.getInt("categoryId"), "categoryName", "memo"));
                product.setAccount(new Account(
                        rs.getString("account"), // account
                        rs.getString("pass"), // pass
                        rs.getString("lastName"), // lastName
                        rs.getString("firstName"), // firstName
                        rs.getDate("birthday"), // birthday
                        rs.getBoolean("gender"), // gender
                        rs.getString("phone"), // phone
                        rs.getBoolean("isUse"), // isUse
                        rs.getString("roleInSystem") // roleInSystem
                ));
                product.setUnit(rs.getString("unit"));
                product.setPrice(rs.getInt("price"));
                product.setDiscount(rs.getInt("discount"));

                products.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products;
    }

    /**
     * Get all products from database
     */
    @Override
    public List<Product> listAll() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT p.*, c.categoryName, c.memo, a.userName, a.email " +
             "FROM products p " +
             "LEFT JOIN categories c ON p.categoryId = c.categoryId " +
             "LEFT JOIN accounts a ON p.accountId = a.accountId";

        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("productId"));
                product.setProductName(rs.getString("productName"));
                product.setProductImage(rs.getString("productImage"));
                product.setBrief(rs.getString("brief"));
                product.setPostedDate(rs.getDate("postedDate"));
                product.setType(new Category(rs.getInt("categoryId"), "categoryName", "memo"));
                product.setAccount(new Account(
                        rs.getString("account"), // account
                        rs.getString("pass"), // pass
                        rs.getString("lastName"), // lastName
                        rs.getString("firstName"), // firstName
                        rs.getDate("birthday"), // birthday
                        rs.getBoolean("gender"), // gender
                        rs.getString("phone"), // phone
                        rs.getBoolean("isUse"), // isUse
                        rs.getString("roleInSystem") // roleInSystem
                ));
                product.setUnit(rs.getString("unit"));
                product.setPrice(rs.getInt("price"));
                product.setDiscount(rs.getInt("discount"));

                products.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products;
    }

    /**
     * Get a product object by Id
     */
    @Override
    public Product getObjectById(String id) {
        Product product = null;
        String query = "SELECT * FROM products WHERE productId=?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                product = new Product();
                product.setProductId(rs.getString("productId"));
                product.setProductName(rs.getString("productName"));
                product.setProductImage(rs.getString("productImage"));
                product.setBrief(rs.getString("brief"));
                product.setPostedDate(rs.getDate("postedDate"));
                product.setType(new Category(rs.getInt("categoryId"), "categoryName", "memo"));
                product.setAccount(new Account(
                        rs.getString("account"), // account
                        rs.getString("pass"), // pass
                        rs.getString("lastName"), // lastName
                        rs.getString("firstName"), // firstName
                        rs.getDate("birthday"), // birthday
                        rs.getBoolean("gender"), // gender
                        rs.getString("phone"), // phone
                        rs.getBoolean("isUse"), // isUse
                        rs.getString("roleInSystem") // roleInSystem
                ));
                product.setUnit(rs.getString("unit"));
                product.setPrice(rs.getInt("price"));
                product.setDiscount(rs.getInt("discount"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return product;
    }

    public List<Product> listByName(String productName) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT p.*, c.categoryName, c.memo, a.userName, a.email " +
             "FROM products p " +
             "LEFT JOIN categories c ON p.categoryId = c.categoryId " +
             "LEFT JOIN accounts a ON p.accountId = a.accountId " +
             "WHERE p.productName LIKE ?";
 // Sử dụng LIKE để tìm kiếm tương đối
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, "%" + productName + "%"); // Thêm ký tự % để tìm kiếm tương đối
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("productId"));
                product.setProductName(rs.getString("productName"));
                product.setProductImage(rs.getString("productImage"));
                product.setBrief(rs.getString("brief"));
                product.setPostedDate(rs.getDate("postedDate"));
                product.setType(new Category(rs.getInt("categoryId"), "categoryName", "memo")); // Giả sử Category có constructor này
                product.setAccount(new Account(
                        rs.getString("account"), // account
                        rs.getString("pass"), // pass
                        rs.getString("lastName"), // lastName
                        rs.getString("firstName"), // firstName
                        rs.getDate("birthday"), // birthday
                        rs.getBoolean("gender"), // gender
                        rs.getString("phone"), // phone
                        rs.getBoolean("isUse"), // isUse
                        rs.getString("roleInSystem") // roleInSystem
                ));
                product.setUnit(rs.getString("unit"));
                product.setPrice(rs.getInt("price"));
                product.setDiscount(rs.getInt("discount"));

                products.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products;
    }

    public int deleteRec(String productId) {
        int result = 0;
        String query = "DELETE FROM products WHERE productId=?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, productId);
            result = pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    public List<Product> listByCategory(String categoryId) throws ClassNotFoundException, SQLException {
    List<Product> list = new ArrayList<>();
    String sql = "SELECT * FROM Products WHERE TypeID = ?";
    
    try (
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, categoryId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product product = new Product();
                                product.setProductId(rs.getString("productId"));
                product.setProductName(rs.getString("productName"));
                product.setProductImage(rs.getString("productImage"));
                product.setBrief(rs.getString("brief"));
                product.setPostedDate(rs.getDate("postedDate"));
                product.setType(new Category(rs.getInt("categoryId"), "categoryName", "memo")); // Giả sử Category có constructor này
                product.setAccount(new Account(
                        rs.getString("account"), // account
                        rs.getString("pass"), // pass
                        rs.getString("lastName"), // lastName
                        rs.getString("firstName"), // firstName
                        rs.getDate("birthday"), // birthday
                        rs.getBoolean("gender"), // gender
                        rs.getString("phone"), // phone
                        rs.getBoolean("isUse"), // isUse
                        rs.getString("roleInSystem") // roleInSystem
                ));
                product.setUnit(rs.getString("unit"));
                product.setPrice(rs.getInt("price"));
                product.setDiscount(rs.getInt("discount"));

                list.add(product);
            }
        }
    }
    
    return list;
}
}
