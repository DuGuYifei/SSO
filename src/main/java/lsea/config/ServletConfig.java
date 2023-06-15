package lsea.config;

import eeapp.servlets.UserServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The ServletConfig class implements a servlet that use java EE app
 * in the eeapp package.
 */

@Configuration
public class ServletConfig {

    @Bean
    public ServletRegistrationBean<UserServlet> userServletRegistrationBean() {
        ServletRegistrationBean<UserServlet> bean = new ServletRegistrationBean<>(new UserServlet());
        bean.addUrlMappings("/users");
        bean.setLoadOnStartup(1);
        return bean;
    }
}
