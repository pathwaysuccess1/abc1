/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.ServletContext;
import utils.ConnectDB;

/**
 *
 * @author USER
 */
public class AccountDAO implements Accessible<Account> {

    private ServletContext sc;
    private Connection con;

    public AccountDAO() throws ClassNotFoundException, SQLException {
        ConnectDB connectDB = new ConnectDB();
        con = connectDB.getConnection();
    }

    public AccountDAO(ServletContext sc) throws ClassNotFoundException, SQLException {
        this.sc = sc;
        con = getConnect(sc);
    }

    private Connection getConnect(ServletContext sc) throws ClassNotFoundException, SQLException {
        ConnectDB connectDB = new ConnectDB(sc);
        return connectDB.getConnection();
    }

    @Override
    public int insertRec(Account o) {
        int result = 0;
        String sqlString = "INSERT INTO accounts (account, pass, lastName, firstName, birthday, gender, phone, isUse, roleInSystem) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            PreparedStatement ps = con.prepareStatement(sqlString);
            ps.setString(1, o.getAccount());
            ps.setString(2, o.getPass());
            ps.setString(3, o.getLastName());
            ps.setString(4, o.getFirstName());
            ps.setDate(5, new java.sql.Date(o.getBirthday().getTime()));
            ps.setBoolean(6, o.isGender());
            ps.setString(7, o.getPhone());
            ps.setBoolean(8, o.isIsUse());
            ps.setString(9, o.getRoleInSystem());

            result = ps.executeUpdate();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    @Override
    public int updateRec(Account o) {
        int result = 0;
        String sqlString = "UPDATE accounts SET account=?, pass=?, lastName=?, firstName=?, birthday=?, "
                + "gender=?, phone=?, isUse=?, roleinSystem=? WHERE account=?";

        try {
            PreparedStatement ps = con.prepareStatement(sqlString);
            ps.setString(1, o.getAccount());
            ps.setString(2, o.getPass());
            ps.setString(3, o.getLastName());
            ps.setString(4, o.getFirstName());
            ps.setDate(5, new java.sql.Date(o.getBirthday().getTime()));
            ps.setBoolean(6, o.isGender());
            ps.setString(7, o.getPhone());
            ps.setBoolean(8, o.isIsUse());
            ps.setString(9, o.getRoleInSystem());
            ps.setString(10, o.getAccount());

            result = ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, sqlString, e);
        }
        return result;
    }

    @Override
    public int deleteRec(Account obj) {
        int result = 0;
        String sqlString = "DELETE FROM accounts WHERE account=?";

        try {
            PreparedStatement ps = con.prepareStatement(sqlString);
            ps.setString(1, obj.getAccount());
            result = ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, sqlString, e);
        }
        return result;
    }

    @Override
    public Account getObjectById(String id) {
        Account account = null;
        String sqlString = "SELECT * FROM accounts WHERE account=?";

        try (PreparedStatement ps = con.prepareStatement(sqlString)) {
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                account = new Account();
                account.setAccount(rs.getString("account"));
                account.setPass(rs.getString("pass"));
                account.setLastName(rs.getString("lastName"));
                account.setFirstName(rs.getString("firstName"));
                account.setBirthday(rs.getDate("birthday"));
                account.setGender(rs.getBoolean("gender"));
                account.setPhone(rs.getString("phone"));
                account.setIsUse(rs.getBoolean("isUse"));
                account.setRoleInSystem(rs.getString("roleInSystem"));

            }
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, sqlString, e);
        }
        return account;
    }

    @Override
    public List<Account> listAll() {
        List<Account> accounts = new ArrayList<>();
        String sqlString = "SELECT * FROM accounts";

        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sqlString)) {
            while (rs.next()) {
                Account account = new Account();
                account.setAccount(rs.getString("account"));
                account.setPass(rs.getString("pass"));
                account.setLastName(rs.getString("lastName"));
                account.setFirstName(rs.getString("firstName"));
                account.setBirthday(rs.getDate("birthday"));
                account.setGender(rs.getBoolean("gender"));
                account.setPhone(rs.getString("phone"));
                account.setIsUse(rs.getBoolean("isUse"));
                account.setRoleInSystem(rs.getString("roleInSystem"));

                accounts.add(account);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return accounts;
    }

    public List<Account> listByRole(int role) {
        List<Account> accounts = new ArrayList<>();
        String sqlString = "SELECT * FROM accounts WHERE roleInSystem=?";

        try (PreparedStatement ps = con.prepareStatement(sqlString)) {
            ps.setInt(1, role);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Account account = new Account();
                account.setAccount(rs.getString("account"));
                account.setPass(rs.getString("pass"));
                account.setLastName(rs.getString("lastName"));
                account.setFirstName(rs.getString("firstName"));
                account.setBirthday(rs.getDate("birthday"));
                account.setGender(rs.getBoolean("gender"));
                account.setPhone(rs.getString("phone"));
                account.setIsUse(rs.getBoolean("isUse"));
                account.setRoleInSystem(rs.getString("roleInSystem"));

                accounts.add(account);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return accounts;
    }

    public int updateIsUsed(String acc, boolean isUsed) {
        int result = 0;
        String sqlString = "UPDATE accounts SET isUse=? WHERE account=?";

        try (PreparedStatement ps = con.prepareStatement(sqlString)) {
            ps.setBoolean(1, isUsed);
            ps.setString(2, acc);
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public Account loginSuccess(String acc, String pass) {
        Account account = null;
        String sqlString = "SELECT * FROM accounts WHERE account=? AND pass=?";

        try (PreparedStatement ps = con.prepareStatement(sqlString)) {
            ps.setString(1, acc);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                account = new Account();
                account.setAccount(rs.getString("account"));
                account.setPass(rs.getString("pass"));
                account.setLastName(rs.getString("lastName"));
                account.setFirstName(rs.getString("firstName"));
                account.setBirthday(rs.getDate("birthday"));
                account.setGender(rs.getBoolean("gender"));
                account.setPhone(rs.getString("phone"));
                account.setIsUse(rs.getBoolean("isUse"));
                account.setRoleInSystem(rs.getString("roleInSystem"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return account;
    }

    public int deleteRec(String account) {
        
        int result = 0;
        String sqlString = "DELETE FROM accounts WHERE account=?";

        try {
            PreparedStatement ps = con.prepareStatement(sqlString);
            ps.setString(1, account);
            result = ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, sqlString, e);
        }
        return result;
    }
}
