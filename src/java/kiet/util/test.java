/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiet.util;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author TuanKiet
 */
public class test {
    public static void main(String[] args) throws NamingException, SQLException, ClassNotFoundException {
        Connection con = DBHelper.makeConnection();
        if(con == null){
            System.out.println("duck duck");
        }
        else{
            System.out.println("yes sir");
        }
    }
}
