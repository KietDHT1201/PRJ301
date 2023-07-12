/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiet.controller;

import kiet.registration.RegistrationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kiet.util.MyAppConstants;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "DeleteServlet", urlPatterns = {"/DeleteServlet"})
public class DeleteServlet extends HttpServlet {
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
        
        String username = request.getParameter("pk");
        String SearchValue = request.getParameter("LastSearchValue");
        
        //1. Get context Scope 
        ServletContext context = this.getServletContext();
        //2. Get SITEMAPS
        Properties siteMaps = (Properties) context.getAttribute("SITEMAPS");
        
        String url = siteMaps.getProperty(MyAppConstants.DeleteFeatures.ERROR_PAGE);
        try{
            //1. CALL MODEL-DAO
            //1.1 new DEO
            RegistrationDAO dao = new RegistrationDAO();
            //1.2 call method of DAO
            boolean result = dao.deteleAccount(username);
            //2. Process result
            if (result) {
                //2.1 call the Search function again using url rewriting tech
                url = "searchLastName?"
                        + "&txtSearchValue=" + SearchValue;
                //2.2 call the error if delete action is fail    
            }//end delete í success
        } catch (SQLException ex){
            log("DeleteServlet _ SQL" + ex.getMessage());
        } catch (NamingException ex){
            log("DeleteServlet _ Naming" + ex.getMessage());
        }finally {
            response.sendRedirect(url);
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
