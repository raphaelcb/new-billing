package br.com.cpqd.billing.comptech.security.model.contract.password;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonAlias;

import br.com.cpqd.billing.comptech.security.model.entity.ActivePassword;
import br.com.cpqd.billing.comptech.security.model.entity.InactivePassword;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This class represents a request contract to {@link ActivePassword} and {@link InactivePassword} REST API
 * service when the operation is CHANGE.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@ApiModel(value = "Request Change Password Contract", 
          description = "Modelo de entidade responsável pela estrutura da requisição para o serviço de alteração de senha de um usuário na aplicação")
@Data
@EqualsAndHashCode
@SuppressWarnings("serial")
public class ReqChangePasswordContract implements Serializable {

    /**
     * Attribute that represents the login of the user
     */
    @ApiModelProperty(name = "login", value = "Login do usuário", required = true, example = "login_user_teste", position = 1)
    @NotEmpty(message = "{comptech.security.component.validation.user.login.not.empty}")
    private String login;
    
    /**
     * Attribute that represents the current password of the user
     */
    @ApiModelProperty(name = "current_password", value = "Senha atual do usuário", required = true, example = "Senha@2021", position = 2)
    @NotEmpty(message = "{comptech.security.component.validation.change.password.current.password}")
    @JsonAlias("current_password")
    private String currentPassword;
    
    /**
     * Attribute that represents the new password of the user
     */
    @ApiModelProperty(name = "new_password", value = "Nova senha do usuário", required = true, example = "NovaSenha@2021", position = 3)
    @NotEmpty(message = "{comptech.security.component.validation.change.password.new.password}")
    @JsonAlias("new_password")
    private String newPassword;
    
    /**
     * Attribute that represents the new password of the user
     */
    @ApiModelProperty(name = "confirm_new_password", value = "Confirmação da nova senha do usuário", required = true, example = "NovaSenha@2021", position = 4)
    @NotEmpty(message = "{comptech.security.component.validation.change.password.confirm.new.password}")
    @JsonAlias("confirm_new_password")
    private String confirmNewPassword;

}
