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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import kiet.registration.RegistrationDAO;
import kiet.registration.RegistrationDTO;
import kiet.util.MyAppConstants;

/**
 *
 * @author TuanKiet
 */
@WebServlet(name = "StartUpServlet", urlPatterns = {"/StartUpServlet"})
public class StartUpServlet extends HttpServlet {
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
        
        //1. Get context Scope 
        ServletContext context = this.getServletContext();
        //2. Get SITEMAPS
        Properties siteMaps = (Properties) context.getAttribute("SITEMAPS");
        
        String url = siteMaps.getProperty(MyAppConstants.DispatchFeatures.LOGIN_PAGE);
        try {
            //1. Get all cookies
            Cookie[] cookies = request.getCookies();
            //2. Check existed cookies
            if(cookies != null){
                //3. Get Username and Password form cookies
                Cookie newestCookie = cookies[cookies.length - 1];
                String username = newestCookie.getName();
                String password = newestCookie.getValue();
                //4. call DAO
                RegistrationDAO dao = new RegistrationDAO();
                RegistrationDTO result = dao.checkLogin(username, password);
                //5. process result
                if(result != null){
                    url = siteMaps.getProperty(MyAppConstants.SearchFeatures.SEARCH_RESULT_PAGE);
                    
                    HttpSession session = request.getSession();
                    session.setAttribute("USER", result);
                }//end user is authenticated
                //               for (Cookie cookie : cookies) {
//                    String username = cookie.getName();
//                    String password = cookie.getValue();
//                    
//                    //Call DAO
//                    RegistrationDAO dao = new RegistrationDAO();
//                    boolean result = dao.checkLogin(username, password);
//                    
//                    if (result) {
//                        url = SEARCH_PAGE;
//                    }//end user is authenticated
//                }
            }//cookies have existed
        }catch (SQLException ex){
            log("StartUpServlet _ SQL " + ex.getMessage());
        } catch (NamingException ex){
            log("StartUpServlet _ Naming " + ex.getMessage());
        }finally{
            response.sendRedirect(url); // Dùng gì cũng được vì cookies tồn tại vĩnh viễn
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
