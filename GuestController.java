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
import java.sql.Date;
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
import model.AccountDAO;
import model.Product;
import model.ProductDAO;

/**
 *
 * @author USER
 */
public class GuestController extends HttpServlet {

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
                case Action.LOGIN:
                    login(request, response);
                    break;

                // List all accounts
                case Action.LOGOUT:
                    logout(request, response);
                    break;

                // Update an account
                case Action.REGISTER:
                    register(request, response);
                    break;

                // Update isUse account 
                case Action.LIST_PRODUCT_BY_CATE_PUBLIC:
                    listProduct(request, response);
                    break;

                default:
                    throw new AssertionError();
            }
        } catch (Exception ex) {
            Logger.getLogger(GuestController.class.getName()).log(Level.SEVERE, null, ex);
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
        response.sendRedirect("MainDashboard.jsp");
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

        private void login(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException, ClassNotFoundException, SQLException {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                // Get parameter from input form
                String username = request.getParameter("account");
                String password = request.getParameter("pass");

                Account account = null;

                AccountDAO blo = new AccountDAO();
                account = blo.loginSuccess(username, password);

                HttpSession session = request.getSession();

                if (account != null) {
                    session.setAttribute("login", account);
                    response.sendRedirect("MainDashboard.jsp");
                } else {
                    String msg = "Invalid username or password";
                    request.setAttribute("ERROR", msg);
                    request.getRequestDispatcher(Navigation.LOGIN).forward(request, response);
                }

            }
        }

    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Delete session of this user and forward this user to login page
        HttpSession session = request.getSession();
        session.invalidate();
        request.getRequestDispatcher(Navigation.LOGIN).forward(request, response);
    }

    private void register(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String account = request.getParameter("account");
        String pass = request.getParameter("pass");
        String lastName = request.getParameter("lastName");
        String firstName = request.getParameter("firstName");

        String ns = request.getParameter("birthday");
        Date birthday = Date.valueOf(ns);

        boolean gender = (request.getParameter("gender").equals("1")) ? true : false;
        String phone = request.getParameter("phone");

        // If account is already existed, then render message for user
        if (new AccountDAO().getObjectById(account) != null) {
            String msg = "Account is already exist!";
            request.setAttribute("accountMsg", msg);
            request.getRequestDispatcher(Navigation.ADD_ACCOUNT).forward(request, response);
        } else {
            Account acc = new Account(account, pass, lastName, firstName, birthday, gender, phone, gender, phone);
            int result = new AccountDAO().insertRec(acc);

            
        response.sendRedirect(Navigation.MAIN_DASHBOARD);
        }
    }

    private void listProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        String cateId = request.getParameter("cate");
        int cate = Integer.parseInt(cateId);
        List<Product> list = new ProductDAO().listAll();

        if (cate == 0) {
            list = new ProductDAO().listAll();
        } else {
            list = new ProductDAO().listByCategory(cate);
        }

        session.setAttribute("listProductPublic", list);

        response.sendRedirect(Navigation.WELCOME);

    }
}
