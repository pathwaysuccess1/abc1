/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import context.Action;
import context.Navigation;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import model.Product;
import model.ProductDAO;

/**
 *
 * @author USER
 */
public class ProductController extends HttpServlet {

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
                 * Product Request
                 */
                // Add new product
                case Action.ADD_PRODUCT:
                    addProduct(request, response);
                    break;

                // List all products
                case Action.LIST_PRODUCT:
                    listProduct(request, response);
                    break;

                // Update a product
                case Action.UPDATE_PRODUCT:
                    updateProduct(request, response);
                    break;

                // Delete a product
                case Action.DELETE_PRODUCT:
                    deleteProduct(request, response);
                    break;

                default:
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
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

    private void addProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        PrintWriter out = response.getWriter();

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        Account account = (Account) request.getSession().getAttribute("login");

        String productId = request.getParameter("id");
        String productName = request.getParameter("name");
        String brief = request.getParameter("brief");
        String unit = request.getParameter("unit");
        String price = request.getParameter("price");
        String discount = request.getParameter("discount");
        String typeId = request.getParameter("type");

        // Process Date by TimeStamp
        Timestamp postedDate = new Timestamp(System.currentTimeMillis());

        CategoryDAO cateDao = new CategoryDAO();
        Category type = cateDao.getObjectById(typeId);

        ProductDAO proDao = new ProductDAO();
        Product product = new Product();

        // If product is already existed, then render message for user
        if (proDao.getObjectById(productId) != null) {
            String msg = "Product is already exist!";
            request.setAttribute("productMsg", msg);
            request.getRequestDispatcher(Navigation.ADD_PRODUCT).forward(request, response);
        } else {
            product.setProductId(productId);
            product.setProductName(productName);
            product.setBrief(brief != null ? brief : "");
            product.setPostedDate(postedDate);
            product.setType(type);
            product.setAccount(account);
            product.setUnit(unit != null ? unit : "");
            product.setPrice(price != null ? Integer.parseInt(price) : 0);
            product.setDiscount(discount != null ? Integer.parseInt(discount) : 0);

            proDao.insertRec(product);
            response.sendRedirect("ProductController?action=" + Action.LIST_PRODUCT + "&cate=0");
        }
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            String productId = request.getParameter("product");

            ProductDAO dao = new ProductDAO();
            dao.deleteRec(productId);

            response.sendRedirect("ProductController?action=" + Action.LIST_PRODUCT + "&cate=0");
        }
    }

    private void listProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String cateId = request.getParameter("cate");
        int cate = Integer.parseInt(cateId);
        ProductDAO dao = new ProductDAO();
        List<Product> list = dao.listAll();

        if (list == null) {
            System.out.println("Danh sách sản phẩm trả về null");
        } else {
            System.out.println("List Size: " + list.size());
            for (Product pro : list) {
                System.out.println("Product: " + pro.getProductName());
            }
        }
        // Đặt danh sách sản phẩm vào request thay vì session
        request.setAttribute("listProduct", list);
        System.out.println("Request attribute set: " + request.getAttribute("listProduct"));
        request.getRequestDispatcher(Navigation.LIST_PRODUCT).forward(request, response);
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("login");

        String productId = request.getParameter("id");
        String productName = request.getParameter("name");
        String brief = request.getParameter("brief");
        String unit = request.getParameter("unit");
        String price = request.getParameter("price");
        String discount = request.getParameter("discount");
        String typeId = request.getParameter("type");

        // Process Date by TimeStamp
        Timestamp postedDate = new Timestamp(System.currentTimeMillis());

        CategoryDAO cateDao = new CategoryDAO();
        Category type = cateDao.getObjectById(typeId);

        // Set all product attributes
        Product product = new Product();
        ProductDAO dao = new ProductDAO();
        product.setProductId(productId);
        product.setProductName(productName);
        product.setBrief(brief != null ? brief : "");
        product.setPostedDate(postedDate);
        product.setType(type);
        product.setAccount(account);
        product.setUnit(unit != null ? unit : "");
        product.setPrice(price != null ? Integer.parseInt(price) : 0);
        product.setDiscount(discount != null ? Integer.parseInt(discount) : 0);

        dao.updateRec(product);
        response.sendRedirect("ProductController?action=" + Action.LIST_PRODUCT + "&cate=0");
    }
}
