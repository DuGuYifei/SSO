# Lab10-Description


## Introduction
In this lab, we should use java EE to build graphical user interface.

Technically, our original project which is built by spring boot is somehow a java EE project which uses
servlet dispatcher to handle the request in an embedded server. 

However, as requirement, we need a native java EE platform. So we need to set up the environment.

## Setup Environment
There are two ways to set up the environment:

Due to the requirement said, it needs to be treated as **an extension of our origin project**, we choose the second way.

1. We can package the project into a `war` file and deploy it to the server. As we have had a spring
boot project, we can then add this war into the server.
   ```java
    @SpringBootApplication
    public class YourApplication extends SpringBootServletInitializer {

        public static void main(String[] args) {
            SpringApplication.run(YourApplication.class, args);
        }

        @Override
        protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
            return builder.sources(YourApplication.class);
        }
    }
    ```
    But then this is like build a new project not an extension of the original.

2. We can add the dependency of java EE into the `pom.xml` file to use java EE in the spring boot project.
    ```xml
    <dependencies>
        <dependency>
            <groupId>org.apache.tomcat.embed</groupId>
            <artifactId>tomcat-embed-jasper</artifactId>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
        </dependency>
    </dependencies>
    ```
    Then we can use the java EE in the spring boot project.
   
    Also, we need configure the java EE package into the embedded server. For example, our project structure is:
   
    ```bash
    src/
    └── main
    ├── java
    │   ├── eeapp (Java EE)
    │   └── lsea  (Spring Boot)
    └── resources
    └── webapp    (JSP)
    ```
    Then we need to configure them in the spring embedded server. Here is an example using `UserServlet`:
    ```java
    @Configuration
    public class ServletConfig {
    
        @Bean
        public ServletRegistrationBean<UserServlet> userServletRegistrationBean() {
            ServletRegistrationBean<UserServlet> bean = new ServletRegistrationBean<>(new UserServlet());
            bean.addUrlMappings("/users");
            return bean;
        }
    }
    ```
   
Then we can use the java EE package as an extension of our spring boot project now.

## Run the project

To run the project as EE:
1. Make sure you have `java 11`
2. run "mvn clean install" as previously.
3. Visit webpages at 'http://localhost:8081/users'.
