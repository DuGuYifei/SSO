package lsea.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration class for Swagger documentation
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Create a Docket bean
     * 
     * @param environment The environment of the application
     * @return A Docket bean
     */
    @Bean
    public Docket createRestApi(Environment environment) {
        Profiles profiles = Profiles.of("dev", "test");
        boolean flag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("sso")
                .enable(flag)
                .select()
                .apis(RequestHandlerSelectors.basePackage("lsea.controllers"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * Create a ApiInfo bean
     * 
     * @return A ApiInfo bean
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Single-sign-on API Doc")
                .description("Api doc of sso")
                .version("v1.0")
                .build();
    }

}
