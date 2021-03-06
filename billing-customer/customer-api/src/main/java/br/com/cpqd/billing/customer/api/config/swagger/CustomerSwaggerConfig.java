package br.com.cpqd.billing.customer.api.config.swagger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * This class is responsible for configuring the Swagger documentation to application.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@Configuration
public class CustomerSwaggerConfig {

    /**
     * Method responsible for defining a {@link Bean} to application API.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The {@link Bean} to application API
     */
    @Bean
    public Docket customerApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                        .apiInfo(apiInfo())
                        .securityContexts(Arrays.asList(securityContext()))
                        .securitySchemes(Arrays.asList(apiKey()))
                        .groupName("Cliente")
                        .useDefaultResponseMessages(false)
                            .globalResponses(HttpMethod.GET, globalResponse())
                            .globalResponses(HttpMethod.POST, postResponse())
                            .globalResponses(HttpMethod.PUT, globalResponse())
                            .globalResponses(HttpMethod.DELETE, globalResponse())
                        .tags(tagCustomer())
                        .select()
                            .apis(RequestHandlerSelectors.basePackage("br.com.cpqd.billing.customer"))
                            .paths(PathSelectors.any())
                        .build();
    }

    /**
     * Method responsible for defining the API spec.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The {@link ApiInfo} object
     */
    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                        .title("CPqD Billing Customer Microservice API Documentation")
                        .description("Documenta????o da API de integra????o entre sistemas externos e o microservi??o de Cliente do CPqD Billing")
                        .termsOfServiceUrl("http://www.cpqd.com.br")
                        .contact(new Contact("Arquiteto", "", "raphaelb@cpqd.com.br"))
                        .license("Apache License Version 2.0")
                        .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
                        .version("1.0")
                    .build();
    }

    /**
     * Method responsible for defining the {@link Tag} for customer entry points.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The new {@link Tag} representing the group for customer entry points
     */
    private Tag tagCustomer() {

        return new Tag("Cliente", "Opera????es relacionadas a clientes", 0);
    }
    
    /**
     * Method responsible for defining a global messages to {@link HttpMethod} - {@link HttpMethod#GET},
     * {@link HttpMethod#PUT} and {@link HttpMethod#DELETE}.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The list of {@link Response} objects
     */
    private List<Response> globalResponse() {

        List<Response> globalResponse = new ArrayList<>();
        globalResponse.add(new ResponseBuilder().code("200").description("OK").build());
        globalResponse.add(new ResponseBuilder().code("400").description("Requisi????o inv??lida").build());
        globalResponse.add(new ResponseBuilder().code("401").description("Acesso n??o autorizado").build());
        globalResponse.add(new ResponseBuilder().code("403").description("Acesso n??o permitido").build());
        globalResponse.add(new ResponseBuilder().code("404").description("Recurso n??o encontrado").build());
        globalResponse
                .add(new ResponseBuilder().code("500").description("Erro interno na aplica????o").build());
        return globalResponse;
    }

    /**
     * Method responsible for defining a global messages to {@link HttpMethod} - {@link HttpMethod#POST}.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The list of {@link Response} objects
     */
    private List<Response> postResponse() {

        List<Response> postResponse = new ArrayList<>();
        postResponse.add(new ResponseBuilder().code("201").description("Criado").build());
        postResponse.add(new ResponseBuilder().code("400").description("Requisi????o inv??lida").build());
        postResponse.add(new ResponseBuilder().code("401").description("Acesso n??o autorizado").build());
        postResponse.add(new ResponseBuilder().code("403").description("Acesso n??o permitido").build());
        postResponse.add(new ResponseBuilder().code("404").description("Recurso n??o encontrado").build());
        postResponse.add(new ResponseBuilder().code("500").description("Erro interno na aplica????o").build());
        return postResponse;
    }
    
    /**
     * Method responsible for defining the JWT Token on request header.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The new {@link ApiKey} object
     */
    private ApiKey apiKey() {

        return new ApiKey("Authorization", "Authorization", "header");
    }

    /**
     * Method responsible for defining the paths where the protection is enabled.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The {@link SecurityContext} object
     */
    private SecurityContext securityContext() {

        return SecurityContext.builder().securityReferences(defaultAuth()).build(); 
    }

    private List<SecurityReference> defaultAuth() {
        
        var authorizationScope = new AuthorizationScope("global", "accessEverything");
        var authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes)); 
    }

}
