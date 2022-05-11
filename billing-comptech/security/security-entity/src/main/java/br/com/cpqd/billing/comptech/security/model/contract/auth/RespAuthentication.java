package br.com.cpqd.billing.comptech.security.model.contract.auth;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * This class represents a response contract to authentication REST API service on application. The
 * authentication methods available are LDAP and database.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@ApiModel(value = "Response Authentication", 
          description = "Modelo de entidade responsável pela estrutura da resposta do processo de autenticação de usuários")
@Data
@SuppressWarnings("serial")
public class RespAuthentication implements Serializable {

    /**
     * Attribute that represents the full username
     */
    @ApiModelProperty(name = "name", notes = "Nome completo do usuário para fins de exibição", position = 1)
    private String name;

    /**
     * Attribute that represents the flag if is the first access of the user or not
     */
    @ApiModelProperty(name = "first_access", notes = "Indicador se é o primeiro acesso do usuário à aplicação para redirecionamento ao frontend de troca de senha", position = 2)
    @JsonProperty("first_access")
    private boolean firstAccess;

    /**
     * Attribute that represents the JWT token generated for session of the user
     */
    @ApiModelProperty(name = "authorization", notes = "Cadeia de caracteres representativa do token JWT gerado", position = 3)
    private String authorization;

    /**
     * Attribute that represents the authorities of the user (permissions)
     */
    @ApiModelProperty(name = "authorities", notes = "Lista de permissões de acesso às funcionalidades da aplicação associadas ao usuário", position = 4)
    private Collection<? extends GrantedAuthority> authorities;

}
