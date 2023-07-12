  /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiet.listener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author TuanKiet
 */
public class MyContextServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("The Application is deploying..............");
        //1. Get current Context Scope
        ServletContext context = sce.getServletContext();
        //2. Get siteMaps
        String siteMapsPath = context.getInitParameter("SITEMAPS_PROPERTIES_FILE");
        //3. Load siteMaps to Attribute of context
        Properties siteMaps = null;
        InputStream is = null;
        
        try{
            siteMaps = new Properties();
            is = context.getResourceAsStream(siteMapsPath);
            siteMaps.load(is);
            context.setAttribute("SITEMAPS", siteMaps);
        } catch(IOException ex){
            context.log("MyContextListener _ IO " + ex.getMessage());
        } finally{
            if(is != null){
                try{
                    is.close();
                } catch(IOException ex){
                    context.log("MyContextListener _ IO " + ex.getMessage());
                }
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("The Application is destroying / undeploying ..........");
    }
}
