/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiet.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author ASUS
 */
public class DBHelper  {
    public static Connection makeConnection()
        throws /*ClassNotFoundException*/NamingException, SQLException{
        
        //1. Get current context
        Context currentContext= new InitialContext();
        
        //2. Get Web Server Context
        Context tomcatContext = (Context) currentContext.lookup("java:comp/env");
        //3. Get DS to open connection
        DataSource ds = (DataSource) tomcatContext.lookup("se1715");
        //4. Open connection
        Connection con = ds.getConnection();
        
        return con;
        
//        //1. Load Driver
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        //2. Create connection string
//        String url = "jdbc:sqlserver://"
//                + "localhost:1433;"
//                + "databaseName=se1715";
//        //3. Open connection
//        Connection con = DriverManager.getConnection(url, "sa", "123");
//        //4. Return caller
//        return con;
    }
}
