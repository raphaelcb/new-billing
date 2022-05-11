package br.com.cpqd.billing.comptech.security.api.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import br.com.cpqd.billing.comptech.commons.entities.model.entity.enums.StatusEnum;
import br.com.cpqd.billing.comptech.security.api.util.SecurityModelMapperUtil;
import br.com.cpqd.billing.comptech.security.model.contract.permission.SecurityPermissionConstants;
import br.com.cpqd.billing.comptech.security.model.contract.profile.ReqAddProfileContract;
import br.com.cpqd.billing.comptech.security.model.contract.profile.ReqUpdateProfileContract;
import br.com.cpqd.billing.comptech.security.model.contract.profile.RespProfileContract;
import br.com.cpqd.billing.comptech.security.model.contract.profile.RespProfileSummaryContract;
import br.com.cpqd.billing.comptech.security.model.entity.Profile;
import br.com.cpqd.billing.comptech.security.service.ProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * This class represents a REST Controller and is responsible for exposing {@link Profile} entry points on
 * application.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @see br.com.cpqd.tem.tech.components.audit.web.controller.AuditRestController
 */
@Api(value = "ProfileRestController", 
     consumes = "application/json", 
     produces = "application/json", 
     protocols = "http,https", 
     tags = "Perfil")
@RestController
@Validated
@RequestMapping(path = "/api/v0/protected/profiles")
public class ProfileRestController extends AuditRestController<Profile> {

    /**
     * Injection of dependency of {@link ProfileService}
     */
    @Autowired
    private ProfileService profileService;

    /**
     * Constructor
     */
    public ProfileRestController() {

        super(new Profile());
    }

    /**
     * Method responsible for exposing an entry point for adding a new {@link Profile} on application.
     * <p>
     * To use this entry point the user must have the corresponding permission.
     * </p>
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param reqAddProfileContract The {@link ReqAddProfileContract} object
     * @param request The {@link HttpServletRequest} object
     * @return The {@link HttpStatus} {@code CREATED} when the operation was made successfully
     */
    @ApiOperation(value = "Adicionar perfil de acesso", 
                  notes = "Adiciona um novo perfil de acesso na aplicação para futura associação a um usuário", 
                  response = Object.class)
    @PreAuthorize("hasRole('" + SecurityPermissionConstants.ROLE_ADD_PROFILE + "')")
    @PostMapping(path = "/add", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addProfile(
            @Valid @RequestBody(required = true) ReqAddProfileContract reqAddProfileContract, 
            HttpServletRequest request) {

        var setAuthorities = new HashSet<>(reqAddProfileContract.getAuthorities());

        CurrentAuditor.INSTANCE.store(reqAddProfileContract.getUsername());
        CurrentIP.INSTANCE.store(request.getRemoteAddr());

        this.profileService.addProfile(SecurityModelMapperUtil.toProfileMapper(reqAddProfileContract),
                setAuthorities);
    }

    /**
     * Method responsible for exposing an entry point for updating a {@link Profile} on application.
     * <p>
     * To use this entry point the user must have the corresponding permission.
     * </p>
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param reqUpdateProfileContract The {@link ReqUpdateProfileContract} object
     * @param request The {@link HttpServletRequest} object
     * @return The {@link HttpStatus} {@code OK} when the operation was made successfully
     */
    @ApiOperation(value = "Atualizar perfil de acesso", 
                  notes = "Atualiza um perfil de acesso na aplicação", 
                  response = Object.class)
    @PreAuthorize("hasRole('" + SecurityPermissionConstants.ROLE_UPDATE_PROFILE + "')")
    @PutMapping(path = "/update", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateProfile(
            @Valid @RequestBody(required = true) ReqUpdateProfileContract reqUpdateProfileContract, 
            HttpServletRequest request) {

        var setAuthorities = new HashSet<>(reqUpdateProfileContract.getAuthorities());

        CurrentAuditor.INSTANCE.store(reqUpdateProfileContract.getUsername());
        CurrentIP.INSTANCE.store(request.getRemoteAddr());

        this.profileService.updateProfile(
                SecurityModelMapperUtil.toProfileMapper(reqUpdateProfileContract), setAuthorities);
    }

    /**
     * Method responsible for exposing an entry point for deleting a {@link Profile} on application.
     * <p>
     * To use this entry point the user must have the corresponding permission.
     * </p>
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param id The {@link Profile} {@code id} attribute
     * @param auditUsername The {@link AuditUsername} object
     * @param request The {@link HttpServletRequest} object
     * @return The {@link HttpStatus} {@code OK} when the operation was made successfully
     */
    @ApiOperation(value = "Remover perfil de acesso", 
                  notes = "Remove um perfil de acesso da aplicação", 
                  response = Object.class)
    @PreAuthorize("hasRole('" + SecurityPermissionConstants.ROLE_DELETE_PROFILE + "')")
    @DeleteMapping(path = "/delete", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteProfile(
            @ApiParam(name = "id", value = "Identificador único do perfil de acesso", required = true)
            @RequestParam(name = "id", required = true) Long id, 
            @Valid @RequestBody(required = true) AuditUsername auditUsername, 
            HttpServletRequest request) {

        CurrentAuditor.INSTANCE.store(auditUsername.getUsername());
        CurrentIP.INSTANCE.store(request.getRemoteAddr());

        this.profileService.deleteProfile(id);
    }

    /**
     * Method responsible for exposing an entry point for searching a {@link Profile} on application.
     * <p>
     * To use this entry point the user must have the corresponding permission.
     * </p>
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The {@link HttpStatus} {@code OK} when the operation was made successfully
     */
    @ApiOperation(value = "Buscar perfis de acesso", 
                  notes = "Busca um ou mais perfis de acesso disponíveis na aplicação", 
                  responseContainer = "List", 
                  response = RespProfileSummaryContract.class)
    @PreAuthorize("hasRole('" + SecurityPermissionConstants.ROLE_SEARCH_PROFILE + "')")
    @GetMapping(path = "/find", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public List<RespProfileSummaryContract> findProfile() {

        return this.profileService.findProfileByStatusEnumAndVisibleTrue(StatusEnum.ACTIVE).stream()
                .map(SecurityModelMapperUtil::toResponseProfileSummaryContractMapper)
                .collect(Collectors.toList());
    }

    /**
     * Method responsible for exposing an entry point for searching a specific {@link Profile} on application.
     * <p>
     * To use this entry point the user must have the corresponding permission.
     * </p>
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param id The {@link Profile} {@code id} attribute
     * @return The {@link HttpStatus} {@code OK} when the operation was made successfully
     */
    @ApiOperation(value = "Buscar perfil de acesso específico", 
                  notes = "Busca um perfil de acesso específico na aplicação de acordo com seu identificador único", 
                  response = RespProfileContract.class)
    @PreAuthorize("hasRole('" + SecurityPermissionConstants.ROLE_SEARCH_PROFILE + "')")
    @GetMapping(path = "/find/id", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public RespProfileContract findProfileById(
            @ApiParam(name = "id", value = "Identificador do perfil de acesso a ser pesquisado", required = true)
            @RequestParam(name = "id", required = true) Long id) {

        var profile = this.profileService.findProfileById(id);

        var responseProfileContract = SecurityModelMapperUtil
                .toResponseProfileContractMapper(profile);
        responseProfileContract.setPermission(profile.getPermission().stream()
                .map(SecurityModelMapperUtil::toPermissionDTOMapper).collect(Collectors.toList()));

        return responseProfileContract;
    }

    /**
     * @see br.com.cpqd.billing.comptech.audit.api.controller.AuditRestController#findEntityHistory(Date, Date, List)
     */
    @Override
    @ApiOperation(value = "Buscar histórico completo de auditoria", 
                  notes = "Busca o histórico completo de auditoria de todos os registros relacionados a perfil de acesso por meio de parâmetros", 
                  responseContainer = "List", 
                  response = AuditEntityResult.class)
    @PreAuthorize("hasRole('" + SecurityPermissionConstants.ROLE_VIEW_AUDIT_PROFILE + "')")
    @GetMapping(path = {"/audit/full"}, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public List<AuditEntityResult> findEntityHistory(
            @ApiParam(name = "from", value = "Data de início do período de busca da auditoria do perfil de acesso. Formato da data: yyyy-MM-ddTHH:mm:ss - Exemplo: 2022-01-01T00:00:00")
            @RequestParam(name = "from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date from,
            @ApiParam(name = "to", value = "Data de fim do período de busca da auditoria do perfil de acesso. Formato da data: yyyy-MM-ddTHH:mm:ss - Exemplo: 2022-01-31T23:59:59")
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
                  notes = "Busca um histórico específico de um item de perfil de acesso na auditora da aplicação de acordo com seu identificador interno", 
                  response = AuditEntityResult.class)
    @PreAuthorize("hasRole('" + SecurityPermissionConstants.ROLE_VIEW_AUDIT_PROFILE + "')")
    @GetMapping(path = {"/audit/item"}, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public List<AuditEntityResult> findEntityHistory(
            @ApiParam(name = "id", value = "Identificador interno do item relacionado ao perfil de acesso", required = true) 
            @RequestParam(name = "id", required = true) Long id) {

        return super.findEntityHistory(id);
    }
    
    /**
     * @see br.com.cpqd.billing.comptech.audit.api.controller.AuditRestController#findEntityHistory(Integer)
     */
    @Override
    @ApiOperation(value = "Detalhar item específico de auditoria", 
                  notes = "Busca e detalha o dado histórico de um item de perfil de acesso registrado na auditoria da aplicação de acordo com seu identificador de revisão", 
                  response = AuditEntityResult.class)
    @PreAuthorize("hasRole('" + SecurityPermissionConstants.ROLE_VIEW_AUDIT_PROFILE + "')")
    @GetMapping(path = {"/audit/item/detail"}, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public List<AuditEntityResult> findEntityHistory(
            @ApiParam(name = "revision", value = "Identificador único da revisão da auditoria de perfil de acesso", required = true) 
            @RequestParam(name = "revision", required = true) Integer revision) {

        return super.findEntityHistory(revision);
    }

}
