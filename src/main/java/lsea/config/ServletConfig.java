package lsea.config;

import eeapp.servlets.UserServlet;
import eeapp.servlets.WebsiteServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The ServletConfig class implements a servlet that use java EE app
 * in the eeapp package.
 */
@Configuration
public class ServletConfig {

    /**
     * Register the UserServlet
     * @return ServletRegistrationBean&lt;UserServlet&gt;
     */
    @Bean
    public ServletRegistrationBean<UserServlet> userServletRegistrationBean() {
        ServletRegistrationBean<UserServlet> bean = new ServletRegistrationBean<>(new UserServlet());
        bean.addUrlMappings("/users/*");
        bean.setLoadOnStartup(1);
        return bean;
    }

    /**
     * Register the WebsiteServlet
     * @return ServletRegistrationBean&lt;WebsiteServlet&gt;
     */
    @Bean
    public ServletRegistrationBean<WebsiteServlet> websiteServletServletRegistrationBean() {
        ServletRegistrationBean<WebsiteServlet> bean = new ServletRegistrationBean<>(new WebsiteServlet());
        bean.addUrlMappings("/websites/*");
        bean.setLoadOnStartup(1);
        return bean;
    }
}
