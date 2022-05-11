package br.com.cpqd.billing.comptech.security.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cpqd.billing.comptech.audit.core.thread.CurrentAuditor;
import br.com.cpqd.billing.comptech.audit.core.thread.CurrentIP;
import br.com.cpqd.billing.comptech.security.model.contract.password.ReqChangePasswordContract;
import br.com.cpqd.billing.comptech.security.model.contract.profile.ReqUpdateProfileContract;
import br.com.cpqd.billing.comptech.security.model.entity.ActivePassword;
import br.com.cpqd.billing.comptech.security.model.entity.InactivePassword;
import br.com.cpqd.billing.comptech.security.model.entity.User;
import br.com.cpqd.billing.comptech.security.service.ChangePasswordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * This class represents a REST Controller and is responsible for exposing {@link ActivePassword} and
 * {@link InactivePassword} entry points to {@link User} on application.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@Api(value = "ChangePasswordRestController", 
     consumes = "application/json", 
     produces = "application/json", 
     protocols = "http,https", 
     tags = "Alteração de senha")
@RestController
@Validated
@RequestMapping(path = "/api/v0/public/change_password")
public class ChangePasswordRestController {

    /**
     * Injection of dependency of {@link ChangePasswordService}
     */
    @Autowired
    private ChangePasswordService changePasswordService;


    /**
     * Method responsible for exposing an entry point for change the password of the {@link User} on
     * application.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param reqChangePasswordContract The {@link ReqUpdateProfileContract} object
     * @param request The {@link HttpServletRequest} object
     * @return The {@link HttpStatus} {@code OK} when the operation was made successfully
     */
    @ApiOperation(value = "Alterar senha de acesso", 
                  notes = "Altera a senha de acesso de um determinado usuário na aplicação", 
                  response = Object.class)
    @PutMapping(path = "/update", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void updatePassword(
            @Valid @RequestBody(required = true) ReqChangePasswordContract reqChangePasswordContract, 
            HttpServletRequest request) {

        CurrentAuditor.INSTANCE.store(reqChangePasswordContract.getLogin());
        CurrentIP.INSTANCE.store(request.getRemoteAddr());
        
        this.changePasswordService.update(reqChangePasswordContract);
    }

}
