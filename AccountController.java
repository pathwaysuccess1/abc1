/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.xml.internal.bind.v2.model.nav.Navigator;
import context.Action;
import context.Navigation;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.AccessException;
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
import javax.swing.text.NavigationFilter;
import model.Account;
import model.AccountDAO;

/**
 *
 * @author USER
 */
public class AccountController extends HttpServlet {

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
                case Action.ADD_ACCOUNT:
                    addAcoount(request, response);
                    break;
                case Action.LIST_ACCOUNT:
                    listAccount(request, response);
                    break;
                case Action.UPDATE_ACCOUNT:
                    updateAccount(request, response);
                    break;
                case Action.ISUSE_UPDATE_ACCOUNT:
                    isUseUpdate(request, response);
                    break;
                case Action.DELETE_ACCOUNT:
                    deleteAccount(request, response);
                    break;
                default:
                    throw new AssertionError();
            }
        } catch (Exception e) {
            Logger.getLogger(GuestController.class.getName()).log(Level.SEVERE, null, e);
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

    private void addAcoount(HttpServletRequest request, HttpServletResponse respone)
            throws ServletException, IOException, ClassNotFoundException, SQLException {

        respone.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String account = request.getParameter("account");
        String pass = request.getParameter("pass");
        String lastName = request.getParameter("lastName");
        String firstName = request.getParameter("firstName");

        String ns = request.getParameter("birthday");
        Date birthday = Date.valueOf(ns);

        boolean gender = (request.getParameter("gender").equals("1")) ? true : false;
        String phone = request.getParameter("phone");

        boolean isUse = (request.getParameter("isUse") != null) ? true : false;
        String roleinSystem = request.getParameter("roleinSystem");

        // Neu tai khoan da co, thi hay thong bao cho user
        if (new AccountDAO().getObjectById(account) != null) {
            String msg = "Account is already exist !";
            request.setAttribute("accountMsg", msg);
            request.getRequestDispatcher(Navigation.ADD_ACCOUNT).forward(request, respone);
        } else {
            Account acc = new Account(account, pass, lastName, firstName, birthday, gender, phone, isUse, roleinSystem);
            int result = new AccountDAO().insertRec(acc);

            // List acc accounts
            respone.sendRedirect("AccountController?action=" + Action.LIST_ACCOUNT);
        }
    }

    private void deleteAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");

            String account = request.getParameter("account");
            new AccountDAO().deleteRec(account);
            response.sendRedirect("AccountController?action=" + Action.DELETE_ACCOUNT);
        }
    }

    private void isUseUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String account = request.getParameter("account");
        Account acc = new AccountDAO().getObjectById(account);

        // // Update by call method of AccountDAO
        new AccountDAO().updateIsUsed(acc.getAccount(), !acc.isIsUse());

        // List all accounts
        response.sendRedirect("AccountController?action=" + Action.LIST_ACCOUNT);
    }

    private void listAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        HttpSession session = request.getSession();

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        List<Account> list = new AccountDAO().listAll();
// Debug kiểm tra danh sách account lấy về
        System.out.println("List Account Size: " + list.size());
        for (Account acc : list) {
            System.out.println("Account: " + acc.getAccount());
        }
        request.setAttribute("listAccount", list);  // Use request instead of session
        request.getRequestDispatcher(Navigation.LIST_ACCOUNT).forward(request, response);

    }

    private void updateAccount(HttpServletRequest request, HttpServletResponse response)
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

        boolean isUse = (request.getParameter("isUse") != null) ? true : false;
        String roleInSystem = request.getParameter("roleInSystem");

        Account upAcc = new AccountDAO().getObjectById(account);

        upAcc.setPass(pass);
        upAcc.setLastName(lastName);
        upAcc.setFirstName(firstName);
        upAcc.setBirthday(birthday);
        upAcc.setGender(gender);
        upAcc.setPhone(phone);
        upAcc.setIsUse(isUse);
        upAcc.setRoleInSystem(roleInSystem);

        // Update account by call method of AccountDAO
        int result = new AccountDAO().updateRec(upAcc);

        // List all accounts                                 
        response.sendRedirect("AccountController?action=" + Action.LIST_ACCOUNT);

    }
}
