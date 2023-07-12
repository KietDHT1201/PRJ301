/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiet.controller;

import kiet.registration.RegistrationDAO;
import kiet.registration.RegistrationDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "SearchLastNameServlet", urlPatterns = {"/SearchLastNameServlet"})
public class SearchLastNameServlet extends HttpServlet {
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
        
        String searchValue = request.getParameter("txtSearchValue");
        
        //1. Get context Scope 
        ServletContext context = this.getServletContext();
        //2. Get SITEMAPS
        Properties siteMaps = (Properties) context.getAttribute("SITEMAPS");
        
        String url = siteMaps.getProperty(MyAppConstants.SearchFeatures.SEARCH_PAGE);
        try{
            if (!searchValue.trim().isEmpty() /*|| searchValue.trim().length() >0*/ ){
                //1 Call Model - DAO
                //1.1 new DAO object    
                RegistrationDAO dao = new RegistrationDAO();
                //1.2 call method of DAO
                dao.searchLastName(searchValue);
                //2. Process result
                List<RegistrationDTO> result = dao.getListAccounts();
                
                request.setAttribute("SEARCH_RESULT", result);
                url = siteMaps.getProperty(MyAppConstants.SearchFeatures.SEARCH_RESULT_PAGE);
            } //end user had type a valid keyword
            
        } catch (SQLException ex ){
            log("SearchLastNameServlet _ SQL " + ex.getMessage());
        } catch (NamingException ex){
            log("SearchLastNameServlet _ Naming " + ex.getMessage());
        }finally {
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
