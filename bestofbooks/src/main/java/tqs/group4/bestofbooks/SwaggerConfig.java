
package tqs.group4.bestofbooks;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

import static io.swagger.models.auth.In.HEADER;
import static java.util.Collections.singletonList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final String X_AUTH_TOKEN = "x-auth-token";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(Lists.newArrayList(
                        new ApiKey(X_AUTH_TOKEN, X_AUTH_TOKEN, HEADER.name()),
                        basicAuthScheme()))
                .securityContexts(Lists.newArrayList(
                        SecurityContext.builder()
                                       .securityReferences(
                                               singletonList(SecurityReference.builder()
                                                                              .reference(X_AUTH_TOKEN)
                                                                              .scopes(new AuthorizationScope[0])
                                                                              .build()))
                                       .forPaths(pathsApiKey())
                                       .build(),
                        securityContext()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("tqs.group4.bestofbooks.controller"))
                .build()
                .apiInfo(apiInfo());
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                              .securityReferences(Arrays.asList(basicAuthReference()))
                              .forPaths(pathsBasicAuth())
                              .build();
    }

    private Predicate<String> pathsBasicAuth() {
        return s -> PathSelectors.ant("/api/session/login").test(s)
                || PathSelectors.ant("/api/session/register").test(s);
    }

    private SecurityScheme basicAuthScheme() {
        return new BasicAuth("basicAuth");
    }

    private SecurityReference basicAuthReference() {
        return new SecurityReference("basicAuth", new AuthorizationScope[0]);
    }

    private Predicate<String> pathsApiKey() {
        return s -> PathSelectors.ant("/api/publisher/**").test(s)
                || PathSelectors.ant("/api/admin/**").test(s)
                || PathSelectors.ant("/api/buyer/**").test(s)
                || PathSelectors.ant("/api/order/**").test(s)
                || PathSelectors.ant("/api/session/user-info").test(s);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("BestOfBooks REST API")
                .description(fullDescription)
                .build();
    }

    String fullDescription = "TQS course final project: Marketplace app specializing in book sales. " +
            "\n\n\n Suggestions for user profiles to try: " +
            "\n Admin Profile: username - admin, password - pw" +
            "\n Buyer Profile: username - buyer1, password - pw" +
            "\n Publisher Profile: username - pub1, password - pw, publisherName - Publisher 1" +
            "\n\n\n Endpoints available for each user profile: " +
            "\n Admin Profile: admin-controller, session-controller: api/session/user-info" +
            "\n Buyer Profile: buyer-controller, orders-controller, session-controller: api/session/user-info" +
            "\n Publisher Profile: publisher-controller, session-controller: api/session/user-info" +
            "\n Open access without needing to be logged in: book-controller, session-controller: api/session/login" +
            "\n\n\n Authentication Guide: " +
            "\n 1) Fill the Basic Authorization fields with the user credentials of your choice;" +
            "\n 2) Place a GET Request in the api/session/login endpoint;" +
            "\n 3) A successful request will return you a x-auth-token in the response headers;" +
            "\n 4) Copy the x-auth-token and place it in the x-auth-token (apiKey) authorization;" +
            "\n 5) Now you will be able to access all the endpoints that are protected.";
}
