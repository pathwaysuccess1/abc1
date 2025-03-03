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
public class CategoryDAO implements Accessible<Category> {

    private ServletContext sc;
    private Connection con;

    public CategoryDAO() throws ClassNotFoundException, SQLException {
        ConnectDB connectDB = new ConnectDB();
        con = connectDB.getConnection();
    }

    public CategoryDAO(ServletContext sc) throws ClassNotFoundException, SQLException {
        this.sc = sc;
        con = getConnect(sc);
    }

    private Connection getConnect(ServletContext sc) throws ClassNotFoundException, SQLException {
        ConnectDB connectDB = new ConnectDB(sc);
        return connectDB.getConnection();
    }

    @Override
    public int insertRec(Category o) {
        int result = 0;
        String query = "INSERT INTO categories (categoryName, memo) VALUES (?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, o.getCategoryName());
            pstmt.setString(2, o.getMemo());
            result = pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("SQL Error: " + ex.getMessage());
        }
        return result;
    }

    /**
     * Update information of a category
     */
    @Override
    public int updateRec(Category o) {
        int result = 0;
        String query = "UPDATE categories SET categoryName=?, memo=? WHERE categoryId=?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, o.getCategoryName());
            pstmt.setString(2, o.getMemo());
            pstmt.setInt(3, o.getTypeId());
            result = pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * Remove a category from database
     */
    @Override
    public int deleteRec(Category o) {
        int result = 0;
        String query = "DELETE FROM categories WHERE categoryId=?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, o.getTypeId());
            result = pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * Get all categories from database
     */
    @Override
    public List<Category> listAll() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories";
        try {
            if (con == null) {
                System.out.println("Lỗi: Kết nối database chưa được thiết lập.");
                return categories;
            }

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Category category = new Category();
                category.setTypeId(rs.getInt("typeId"));
                category.setCategoryName(rs.getString("categoryName"));
                category.setMemo(rs.getString("memo"));
                categories.add(category);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Lỗi SQL: " + ex.getMessage());
        }
        return categories;
    }

    /**
     * Get a category object by id
     */
    @Override
    public Category getObjectById(String id) {
        Category category = null;
        String query = "SELECT * FROM categories WHERE categoryId=?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                category = new Category();
                category.setTypeId(rs.getInt("typeId"));
                category.setCategoryName(rs.getString("categoryName"));
                category.setMemo(rs.getString("memo"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return category;
    }

    public List<Category> listByCateName(String categoryName) {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories WHERE categoryName LIKE ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, "%" + categoryName + "%"); // Sử dụng LIKE để tìm kiếm tương đối
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setTypeId(rs.getInt("typeId"));
                category.setCategoryName(rs.getString("categoryName"));
                category.setMemo(rs.getString("memo"));
                categories.add(category);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;
    }

    public Category getCateByName(String categoryName) {
        Category category = null;
        String query = "SELECT * FROM categories WHERE categoryName = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, categoryName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                category = new Category();
                category.setTypeId(rs.getInt("typeId"));
                category.setCategoryName(rs.getString("categoryName"));
                category.setMemo(rs.getString("memo"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return category;
    }

    public int deleteRec(String cate) {
        int result = 0;
        String query = "DELETE FROM categories WHERE categoryId=?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, cate);
            result = pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
