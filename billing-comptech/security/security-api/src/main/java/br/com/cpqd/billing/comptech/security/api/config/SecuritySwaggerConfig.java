package br.com.cpqd.billing.comptech.security.api.config;

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
 * This class is responsible for configuring the Swagger documentation to Security component.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@Configuration
public class SecuritySwaggerConfig {

    /**
     * Method responsible for defining a {@link Bean} to security API.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The {@link Bean} to security API
     */
    @Bean
    public Docket securityApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                        .apiInfo(apiInfo())
                        .securityContexts(Arrays.asList(securityContext()))
                        .securitySchemes(Arrays.asList(apiKey()))
                        .groupName("AutenticacaoAutorizacao")
                        .useDefaultResponseMessages(false)
                            .globalResponses(HttpMethod.GET, globalResponse())
                            .globalResponses(HttpMethod.POST, postResponse())
                            .globalResponses(HttpMethod.PUT, globalResponse())
                            .globalResponses(HttpMethod.DELETE, globalResponse())
                        .tags(tagAuthentication(), 
                              tagPermission(), 
                              tagProfile(),
                              tagUser(),
                              tagChangePassword())
                        .select()
                            .apis(RequestHandlerSelectors.basePackage("br.com.cpqd.billing.comptech"))
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
                        .title("CPqD Billing Authentication and Authorization Microservice API Documentation")
                        .description("Documentação da API de integração entre sistemas externos e o microserviço de Autenticação e Autorização do CPqD Billing")
                        .termsOfServiceUrl("http://www.cpqd.com.br")
                        .contact(new Contact("Arquiteto", "", "raphaelb@cpqd.com.br"))
                        .license("Apache License Version 2.0")
                        .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
                        .version("1.0")
                    .build();
    }
    
    /**
     * Method responsible for defining the {@link Tag} for authentication security entry points.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The new {@link Tag} representing the group for authentication entry points
     */
    private Tag tagAuthentication() {

        return new Tag("Autenticação", "Operações relacionadas a autenticação de usuários", 0);
    }
    
    /**
     * Method responsible for defining the {@link Tag} for permission entry points.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The new {@link Tag} representing the group for permission entry points
     */
    private Tag tagPermission() {

        return new Tag("Permissão", "Operações relacionadas a permissões", 0);
    }
    
    /**
     * Method responsible for defining the {@link Tag} for profile entry points.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The new {@link Tag} representing the group for profile entry points
     */
    private Tag tagProfile() {

        return new Tag("Perfil", "Operações relacionadas a perfil de acesso", 0);
    }
    
    /**
     * Method responsible for defining the {@link Tag} for user entry points.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The new {@link Tag} representing the group for user entry points
     */
    private Tag tagUser() {

        return new Tag("Usuário", "Operações relacionadas a usuário", 0);
    }
    
    /**
     * Method responsible for defining the {@link Tag} for change password entry points.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The new {@link Tag} representing the group for user entry points
     */
    private Tag tagChangePassword() {

        return new Tag("Alteração de senha", "Operações relacionadas a troca de senha do usuário", 0);
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
        globalResponse.add(new ResponseBuilder().code("400").description("Requisição inválida").build());
        globalResponse.add(new ResponseBuilder().code("401").description("Acesso não autorizado").build());
        globalResponse.add(new ResponseBuilder().code("403").description("Acesso não permitido").build());
        globalResponse.add(new ResponseBuilder().code("404").description("Recurso não encontrado").build());
        globalResponse
                .add(new ResponseBuilder().code("500").description("Erro interno na aplicação").build());
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
        postResponse.add(new ResponseBuilder().code("400").description("Requisição inválida").build());
        postResponse.add(new ResponseBuilder().code("401").description("Acesso não autorizado").build());
        postResponse.add(new ResponseBuilder().code("403").description("Acesso não permitido").build());
        postResponse.add(new ResponseBuilder().code("404").description("Recurso não encontrado").build());
        postResponse.add(new ResponseBuilder().code("500").description("Erro interno na aplicação").build());
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
