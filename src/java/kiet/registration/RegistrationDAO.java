/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiet.registration;

import kiet.util.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author ASUS
 */
public class RegistrationDAO implements Serializable{
    public RegistrationDTO checkLogin(String username, String password)
        throws SQLException, /*ClassNotFoundException*/NamingException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        RegistrationDTO result = null;
        
        try{
            //1. Make connection
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "Select lastname, isAdmin "
                        + "From Registration "
                        + "Where username = ? "
                        + "And password = ?";
                //3. Create statement Obj (ĐK: check connection, SQL String)
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                //4. Execute Query
                rs = stm.executeQuery();
                //5. Process Result
                if (rs.next()) {
                    //map
                    String fullName = rs.getString("lastname");
                    boolean role = rs.getBoolean("isAdmin");
                    
                    result = new RegistrationDTO(username, null, fullName, role);
                }//end rs had got only one row
            }//end connection is available
            
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }
    
    private List<RegistrationDTO> listAccounts;
    
    public List<RegistrationDTO> getListAccounts(){
        return listAccounts;
    }
    
    public void searchLastName(String searchValue)
        throws SQLException, NamingException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try {
            //1. Make connection
            con = DBHelper.makeConnection();
            //1.1 Check connection
            if (con != null) {
                //2. Create SQL String
                String sql = "Select username, password, lastname, isAdmin "
                        + "From Registration "
                        + "Where lastname like ?";
                //3. Create statement Obj (ĐK: check connection, SQL String)
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                //4. Execute Query
                rs = stm.executeQuery();
                while (rs.next()) {                    
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String lastname = rs.getString("lastname");
                    boolean role = rs.getBoolean("isAdmin");
                    
                    RegistrationDTO dto = 
                            new RegistrationDTO(username, password, lastname, role);
                    
                    if (this.listAccounts == null) {
                        this.listAccounts = new ArrayList<RegistrationDTO>();
                    }
                    
                    this.listAccounts.add(dto);
                }//end rs had got many row
            }//end connection is available
        } finally{
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public boolean deteleAccount(String username)
        throws SQLException, NamingException{
        Connection con = null;
        PreparedStatement stm = null;
        try {
            //1. Make connect
            con = DBHelper.makeConnection();
            //1.1 Check connection
            if (con!= null) {
                //2. Create SQL String
                String url = "Delete "
                        + "From Registration "
                        + "Where username like ? ";
                //3.Create statement Obj
                stm = con.prepareStatement(url);
                stm.setString(1, username);
                //4. Exexcute Query
                int effectRows = stm.executeUpdate();
                //5. Process Result
                if (effectRows > 0) {
                    return true;
                }
            }//end connection is available
            
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con!= null) {
                con.close();
            }
        }
        return false;
        
    }
    
    public boolean updateAccount(String username, String password, String fullname, boolean isRole)
        throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        boolean check = false;
        try{
            //1.Make connection
            con = DBHelper.makeConnection();
            //1.1 Check connect
            if (con != null) {
                //2. Create SQL String
                String url = "Update Registration "
                        + "Set  password = ?, lastname = ?, isAdmin = ? "
                        + "Where username = ? ";
                //3. Create statement Obj
                stm = con.prepareStatement(url);
                stm.setString(1, password);
                stm.setString(2, fullname);
                stm.setBoolean(3, isRole);
                stm.setString(4, username);
                
                //4. Execute Query
                int effectRows = stm.executeUpdate();
                if (effectRows > 0) {
                    check = true;
                }
            }//end connection is avaible
        } finally{
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return check;
    }
    
    public boolean createAccount(RegistrationDTO account) throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        boolean check = false;
        try{
            //1.Make connection
            con = DBHelper.makeConnection();
            //1.1 Check connect
            if (con != null) {
                //2. Create SQL String
                String sql = "Insert into Registration("
                        + "username, password, lastname, isAdmin"
                        + ") Values (?,?,?,?);";
                //3. Create statement Obj
                stm = con.prepareStatement(sql);
                stm.setString(1, account.getUsername());
                stm.setString(2, account.getPassword());
                stm.setString(3, account.getLastname());
                stm.setBoolean(4, account.isRole());
                //4. Execute Query
                int effectRows = stm.executeUpdate();
                if (effectRows > 0) {
                    check = true;
                }
            }//end connection is avaible
        } finally{
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return check;
    }
}
