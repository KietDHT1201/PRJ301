/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiet.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kiet.registration.RegistrationCreateError;
import kiet.registration.RegistrationDAO;
import kiet.registration.RegistrationDTO;
import kiet.util.MyAppConstants;
import org.apache.tomcat.dbcp.pool2.PoolUtils;

/**
 *
 * @author TuanKiet
 */
public class CreateNewAccountServlet extends HttpServlet {
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
        
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirm");
        String fullName = request.getParameter("txtFullname");
        
        boolean founrErr = false;
        //1. Get context Scope 
        ServletContext context = this.getServletContext();
        //2. Get SITEMAPS
        Properties siteMaps = (Properties) context.getAttribute("SITEMAPS");
        
        String url = siteMaps.getProperty(MyAppConstants.CreateNewAccountFeatures.CREATE_NEW_ACCOUNT_PAGE);
        
        RegistrationCreateError errors = new RegistrationCreateError();
        RegistrationDTO account = new RegistrationDTO();
        try {
            //1. Check all user's contrainsts
            
            if(username.trim().length() < 6 || username.trim().length() > 20){
                founrErr = true;
                errors.setUsernameLengthErr("Username is required input from 6 to 20 characters");
            }
            if(password.trim().length() < 6 || username.trim().length() > 30){
                founrErr = true;
                errors.setPasswordLengthErr("Password is required input from 6 to 30 characters");
            }else if(!confirm.trim().equals(password.trim())){
                founrErr = true;
                errors.setConfirmNotMatched("Confirm must match password");
            }
            if(fullName.trim().length() < 2 || username.trim().length() > 50){
                founrErr = true;
                errors.setFullNameLengthErr("Full name is required input from 2 to 50 characters");
            }
            if(founrErr){
                request.setAttribute("CREATE_ERRORS", errors);
            } else{
                //2. Call DAO
                RegistrationDAO dao = new RegistrationDAO();
                account = new RegistrationDTO(username, password, fullName, false);
                boolean result = dao.createAccount(account);
                //3. process result
                System.out.println(result); 
                if(result){
                    url = siteMaps.getProperty(MyAppConstants.CreateNewAccountFeatures.LOGIN_PAGE);
                }//Creating account is successfully
            }// end error is not occured
            
        } catch (SQLException ex){
            String msg = ex.getMessage();
            log("CreateAccountServlet _ SQL " + msg);
            if(msg.contains("duplicate")){
                errors.setUsernameIsExisted(username + " is existed!!!");
                request.setAttribute("CREATE_ERRORS", errors);
            }
        } catch (NamingException ex){
            log("CreateAccountServlet _ NAMING " + ex.getMessage());
        }
        finally{
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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

}
