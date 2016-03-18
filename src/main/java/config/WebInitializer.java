package config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext annConWebAppCtx = new AnnotationConfigWebApplicationContext();

        annConWebAppCtx.setConfigLocation(this.getClass().getPackage().getName());

        annConWebAppCtx.setServletContext(servletContext);

        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(annConWebAppCtx));

        servlet.setLoadOnStartup(1);

        servlet.addMapping("/");
    }

}
