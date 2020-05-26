
package tqs.group4.bestofbooks;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.Arrays;

@Configuration
@EnableSwagger2WebMvc
@Import(SpringDataRestConfiguration.class)
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("tqs.group4.bestofbooks.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(basicAuthScheme()));
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                              .securityReferences(Arrays.asList(basicAuthReference()))
                              .forPaths(PathSelectors.ant("api/admin/**"))
                              .forPaths(PathSelectors.ant("api/order/**"))
                              .forPaths(PathSelectors.ant("api/buyer/**"))
                              .forPaths(PathSelectors.ant("api/publisher/**"))
                              .build();
    }

    private SecurityScheme basicAuthScheme() {
        return new BasicAuth("basicAuth");
    }

    private SecurityReference basicAuthReference() {
        return new SecurityReference("basicAuth", new AuthorizationScope[0]);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("BestOfBooks REST API")
                .description("TQS course final project: Marketplace app specializing in book sales.")
                .build();
    }
}

