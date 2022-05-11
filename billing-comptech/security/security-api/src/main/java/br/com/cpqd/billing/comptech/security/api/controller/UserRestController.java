package br.com.cpqd.billing.comptech.security.api.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cpqd.billing.comptech.audit.api.controller.AuditRestController;
import br.com.cpqd.billing.comptech.audit.core.thread.CurrentAuditor;
import br.com.cpqd.billing.comptech.audit.core.thread.CurrentIP;
import br.com.cpqd.billing.comptech.audit.model.entity.AuditEntityResult;
import br.com.cpqd.billing.comptech.audit.model.entity.AuditUsername;
import br.com.cpqd.billing.comptech.security.api.util.SecurityModelMapperUtil;
import br.com.cpqd.billing.comptech.security.model.contract.permission.SecurityPermissionConstants;
import br.com.cpqd.billing.comptech.security.model.contract.user.ReqAddUserContract;
import br.com.cpqd.billing.comptech.security.model.contract.user.ReqFindUserContract;
import br.com.cpqd.billing.comptech.security.model.contract.user.ReqUpdateUserContract;
import br.com.cpqd.billing.comptech.security.model.contract.user.RespUserContract;
import br.com.cpqd.billing.comptech.security.model.contract.user.RespUserSummaryContract;
import br.com.cpqd.billing.comptech.security.model.entity.User;
import br.com.cpqd.billing.comptech.security.model.entity.enums.StatusUserEnum;
import br.com.cpqd.billing.comptech.security.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * This class represents a REST Controller and is responsible for exposing {@link User} entry points on
 * application.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @see br.com.cpqd.tem.tech.components.audit.web.controller.AuditRestController
 */
@Api(value = "UserRestController", 
     consumes = "application/json", 
     produces = "application/json", 
     protocols = "http,https", 
     tags = "Usuário")
@RestController
@Validated
@RequestMapping(path = "/api/v0/protected/users")
public class UserRestController extends AuditRestController<User> {

    /**
     * Injection of dependency of {@link UserService}
     */
    @Autowired
    private UserService userService;

    /**
     * Constructor
     */
    public UserRestController() {

        super(new User());
    }

    /**
     * Method responsible for exposing an entry point for adding a new {@link User} on application.
     * <p>
     * To use this entry point the user must have the corresponding permission.
     * </p>
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param reqAddUserContract The {@link ReqAddUserContract} object
     * @param request The {@link HttpServletRequest} object
     * @return The {@link HttpStatus} {@code CREATED} when the operation was made successfully
     */
    @ApiOperation(value = "Adicionar usuário", 
                  notes = "Adiciona um novo usuário para uso na aplicação", 
                  response = Object.class)
    @PreAuthorize("hasRole('" + SecurityPermissionConstants.ROLE_ADD_USER + "')")
    @PostMapping(path = "/add", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addUser(
            @Valid @RequestBody(required = true) ReqAddUserContract reqAddUserContract, 
            HttpServletRequest request) {

        CurrentAuditor.INSTANCE.store(reqAddUserContract.getUsername());
        CurrentIP.INSTANCE.store(request.getRemoteAddr());

        this.userService.addUser(SecurityModelMapperUtil.toUserMapper(reqAddUserContract));
    }

    /**
     * Method responsible for exposing an entry point for updating a {@link User} on application.
     * <p>
     * To use this entry point the user must have the corresponding permission.
     * </p>
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param reqUpdateUserContract The {@link ReqUpdateUserContract} object
     * @param request The {@link HttpServletRequest} object
     * @return The {@link HttpStatus} {@code OK} when the operation was made successfully
     */
    @ApiOperation(value = "Atualizar usuário", 
                  notes = "Atualizar um usuário na aplicação", 
                  response = Object.class)
    @PreAuthorize("hasRole('" + SecurityPermissionConstants.ROLE_UPDATE_USER + "')")
    @PutMapping(path = "/update", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateUser(
            @Valid @RequestBody(required = true) ReqUpdateUserContract reqUpdateUserContract, 
            HttpServletRequest request) {

        CurrentAuditor.INSTANCE.store(reqUpdateUserContract.getUsername());
        CurrentIP.INSTANCE.store(request.getRemoteAddr());

        this.userService.updateUser(SecurityModelMapperUtil.toUserMapper(reqUpdateUserContract));
    }

    /**
     * Method responsible for exposing an entry point for deleting a {@link User} on application.
     * <p>
     * To use this entry point the user must have the corresponding permission.
     * </p>
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param id The {@link User} {@code id} attribute
     * @param auditUsername The {@link AuditUsername} object
     * @param request The {@link HttpServletRequest} object
     * @return The {@link HttpStatus} {@code OK} when the operation was made successfully
     */
    @ApiOperation(value = "Remover usuário", 
                  notes = "Remove um usuário na aplicação", 
                  response = Object.class)
    @PreAuthorize("hasRole('" + SecurityPermissionConstants.ROLE_DELETE_USER + "')")
    @DeleteMapping(path = "/delete", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteUser(
            @ApiParam(name = "id", value = "Identificador único do usuário", required = true) 
            @RequestParam(name = "id", required = true) Long id, 
            @Valid @RequestBody(required = true) AuditUsername auditUsername, 
            HttpServletRequest request) {

        CurrentAuditor.INSTANCE.store(auditUsername.getUsername());
        CurrentIP.INSTANCE.store(request.getRemoteAddr());

        this.userService.deleteUser(id);
    }

    /**
     * Method responsible for exposing an entry point for searching {@link User} on application.
     * <p>
     * To use this entry point the user must have the corresponding permission.
     * </p>
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param reqFindUserContract The {@link ReqFindUserContract} object
     * @param page The desired page number to pagination
     * @param pageSize The desired page size to pagination
     * @return The {@link HttpStatus} {@code OK} when the operation was made successfully
     */
    @ApiOperation(value = "Buscar usuários", 
                  notes = "Busca um ou mais usuários na aplicação", 
                  responseContainer = "List", 
                  response = RespUserSummaryContract.class)
    @PreAuthorize("hasRole('" + SecurityPermissionConstants.ROLE_SEARCH_USER + "')")
    @GetMapping(path = "/find", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public List<RespUserSummaryContract> findUser(
            @Valid @RequestBody(required = false) ReqFindUserContract reqFindUserContract,
            @ApiParam(name = "page", value = "Número da página desejada a ser paginada. Valor mínimo: 0. Valor padrão: 0", defaultValue = "0")
            @RequestParam(name = "page", defaultValue = "0", required = false) @Min(value = 0, message = "{javax.validation.pagination.page}") Integer page,
            @ApiParam(name = "pageSize", value = "Quantidade de registros a serem exibidos por página que está sendo paginada. Valor mínimo: 500. Valor padrão: 1000", defaultValue = "1000")
            @RequestParam(name = "pageSize", defaultValue = "1000", required = false) @Min(value = 500, message = "{javax.validation.pagination.pageSize}") Integer pageSize) {        
        
        if (reqFindUserContract == null) {
            reqFindUserContract = new ReqFindUserContract();
        }
        
        StatusUserEnum statusUserEnum = null;
        if (reqFindUserContract.getStatus() != null) {
             statusUserEnum = StatusUserEnum.values()[reqFindUserContract.getStatus()];    
        }

        return this.userService
                .findUserByLoginAndNameAndEmailAndStatusUserEnum(reqFindUserContract.getLogin(),
                        reqFindUserContract.getName(), reqFindUserContract.getEmail(), statusUserEnum,
                        page, pageSize)
                .stream().map(SecurityModelMapperUtil::toResponseUserSummaryContractMapper)
                .collect(Collectors.toList());
    }

    /**
     * Method responsible for exposing an entry point for searching a specific {@link User} on application.
     * <p>
     * To use this entry point the user must have the corresponding permission.
     * </p>
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param id The {@link User} {@code id} attribute
     * @return The {@link HttpStatus} {@code OK} when the operation was made successfully
     */
    @ApiOperation(value = "Buscar usuário específico", 
                  notes = "Busca um usuário específico na aplicação de acordo com seu identificador único", 
                  response = RespUserContract.class)
    @PreAuthorize("hasRole('" + SecurityPermissionConstants.ROLE_SEARCH_USER + "')")
    @GetMapping(path = "/find/id", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public RespUserContract findUserById(
            @ApiParam(name = "id", value = "Identificador do usuário a ser pesquisado", required = true)
            @RequestParam(name = "id", required = true) Long id) {
        
        return SecurityModelMapperUtil.toResponseUserContractMapper(this.userService.findUserById(id));
    }

    /**
     * @see br.com.cpqd.billing.comptech.audit.api.controller.AuditRestController#findEntityHistory(Date, Date, List)
     */
    @Override
    @ApiOperation(value = "Buscar histórico completo de auditoria", 
                  notes = "Busca o histórico completo de auditoria de todos os registros relacionados a usuário por meio de parâmetros", 
                  responseContainer = "List", 
                  response = AuditEntityResult.class)
    @PreAuthorize("hasRole('" + SecurityPermissionConstants.ROLE_VIEW_AUDIT_PROFILE + "')")
    @GetMapping(path = {"/audit/full"}, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public List<AuditEntityResult> findEntityHistory(
            @ApiParam(name = "from", value = "Data de início do período de busca da auditoria do usuário. Formato da data: yyyy-MM-ddTHH:mm:ss - Exemplo: 2022-01-01T00:00:00")
            @RequestParam(name = "from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date from,
            @ApiParam(name = "to", value = "Data de fim do período de busca da auditoria do usuário. Formato da data: yyyy-MM-ddTHH:mm:ss - Exemplo: 2022-01-31T23:59:59")
            @RequestParam(name = "to", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date to,
            @ApiParam(name = "revision_type", value = "Lista com os identificadores dos tipos de operações. Valores válidos: 0 - Inclusão; 1 - Alteração; 2 - Exclusão")
            @RequestParam(name = "revision_type", required = false) List<Integer> revisionType) {

        return super.findEntityHistory(from, to, revisionType);
    }

    /**
     * @see br.com.cpqd.billing.comptech.audit.api.controller.AuditRestController#findEntityHistory(Long)
     */
    @Override
    @ApiOperation(value = "Buscar histórico específico de item na auditoria", 
                  notes = "Busca um histórico específico de um item de usuário na auditora da aplicação de acordo com seu identificador interno", 
                  response = AuditEntityResult.class)
    @PreAuthorize("hasRole('" + SecurityPermissionConstants.ROLE_VIEW_AUDIT_PROFILE + "')")
    @GetMapping(path = {"/audit/item"}, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public List<AuditEntityResult> findEntityHistory(
            @ApiParam(name = "id", value = "Identificador interno do item relacionado ao usuário", required = true) 
            @RequestParam(name = "id", required = true) Long id) {

        return super.findEntityHistory(id);
    }
    
    /**
     * @see br.com.cpqd.billing.comptech.audit.api.controller.AuditRestController#findEntityHistory(Integer)
     */
    @Override
    @ApiOperation(value = "Detalhar item específico de auditoria", 
                  notes = "Busca e detalha o dado histórico de um item de usuário registrado na auditoria da aplicação de acordo com seu identificador de revisão", 
                  response = AuditEntityResult.class)
    @PreAuthorize("hasRole('" + SecurityPermissionConstants.ROLE_VIEW_AUDIT_PROFILE + "')")
    @GetMapping(path = {"/audit/item/detail"}, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public List<AuditEntityResult> findEntityHistory(
            @ApiParam(name = "revision", value = "Identificador único da revisão da auditoria de usuário", required = true) 
            @RequestParam(name = "revision", required = true) Integer revision) {

        return super.findEntityHistory(revision);
    }

}
