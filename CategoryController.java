/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import context.Action;
import context.Navigation;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.Category;
import model.CategoryDAO;

/**
 *
 * @author USER
 */
public class CategoryController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            request.setCharacterEncoding("UTF-8");
            String action = request.getParameter("action");
            switch (action) {
                /**
                 * Category Request
                 */
                // Add new category
                case Action.ADD_CATEGORY:
                    addCategory(request, response);
                    break;

                // List all categories
                case Action.LIST_CATEGORY:
                    listCategory(request, response);
                    break;

                // Update a category
                case Action.UPDATE_CATEGORY:
                    updateCategory(request, response);
                    break;

                // Delete a category
                case Action.DELETE_CATEGORY:
                    deleteCategory(request, response);
                    break;

                default:
            }
        } catch (Exception ex) {
            Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void addCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String cateName = request.getParameter("cateName");
        String memo = request.getParameter("memo");

        // Kiểm tra giá trị nhập vào
        if (cateName == null || cateName.trim().isEmpty()) {
            request.setAttribute("categoryMsg", "Category name cannot be empty!");
            request.getRequestDispatcher(Navigation.ADD_CATEGORY).forward(request, response);
            return;
        }

        CategoryDAO dao = new CategoryDAO();

        // Kiểm tra xem danh mục đã tồn tại chưa
        Category existingCategory = dao.getCateByName(cateName);
        if (existingCategory != null) {
            request.setAttribute("categoryMsg", "Category name already exists!");
            request.getRequestDispatcher(Navigation.ADD_CATEGORY).forward(request, response);
            return;
        }

        // Tạo mới Category và thêm vào database
        Category c = new Category();
        c.setCategoryName(cateName);
        c.setMemo(memo);

        int rowsInserted = dao.insertRec(c);  // Giả sử insertRec trả về số dòng đã chèn
        if (rowsInserted > 0) {
            System.out.println("Inserted category: " + cateName);
            response.sendRedirect("CategoryController?action=" + Action.LIST_CATEGORY);
        } else {
            request.setAttribute("categoryMsg", "Failed to add category. Please try again.");
            request.getRequestDispatcher(Navigation.ADD_CATEGORY).forward(request, response);
        }
    }

    private void listCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        CategoryDAO dao = new CategoryDAO();
        List<Category> list = dao.listAll();
        if (list == null) {
            System.out.println("listAll() trả về null");
        } else {
            System.out.println("List Size: " + list.size());
        }

        for (Category cate : list) {
            System.out.println("Category: " + cate.getCategoryName());
        }
        session.setAttribute("listCategory", list);
        System.out.println("Session listCategory: " + session.getAttribute("listCategory"));

        // Thay đổi từ sendRedirect thành forward
        request.getRequestDispatcher(Navigation.LIST_CATEGORY).forward(request, response);
        // KHÔNG SỬ DỤNG: response.sendRedirect(Navigation.LIST_CATEGORY);
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String type = request.getParameter("typeId");
        String name = request.getParameter("cateName");
        String memo = request.getParameter("memo");

        CategoryDAO dao = new CategoryDAO();
        // If category is already existed, then render message for user
        if (dao.getCateByName(name) != null) {
            String msg = "Category name is already exist!";
            request.setAttribute("categoryUpdateMsg", msg);
            request.getRequestDispatcher(Navigation.UPDATE_CATEGORY + "?category=" + type).forward(request, response);

        } else {
            Category updateCate = dao.getObjectById(type);
            updateCate.setCategoryName(name);
            updateCate.setMemo(memo != null ? memo : "");

            int result = dao.updateRec(updateCate);
            response.sendRedirect("CategoryController?action=" + Action.LIST_CATEGORY);
        }

    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String cate = request.getParameter("category");

        CategoryDAO dao = new CategoryDAO();
        dao.deleteRec(cate);

        response.sendRedirect(request.getContextPath() + "/CategoryController?action=" + Action.LIST_CATEGORY);

    }
}
