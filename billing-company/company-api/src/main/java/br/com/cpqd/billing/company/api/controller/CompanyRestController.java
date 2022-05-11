package br.com.cpqd.billing.company.api.controller;

import java.util.stream.Collectors;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cpqd.billing.company.api.util.CompanyModelMapperUtil;
import br.com.cpqd.billing.company.model.contract.CompanyPermissionConstants;
import br.com.cpqd.billing.company.model.contract.RespCompanyContract;
import br.com.cpqd.billing.company.model.entity.Company;
import br.com.cpqd.billing.company.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * This class represents a REST Controller and is responsible for exposing {@link Company} entry points on
 * application.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@Api(value = "CompanyRestController", 
     consumes = "application/json", 
     produces = "application/json", 
     protocols = "http,https", 
     tags = "Empresa")
@RestController
@Validated
@RequestMapping(path = "/api/v0/protected")
public class CompanyRestController {

    /**
     * Injection of dependency of {@link CompanyService}
     */
    @Autowired
    private CompanyService companyService;


    /**
     * Method responsible for exposing an entry point for searching {@link Company} on application.
     * <p>
     * To use this entry point the user must have the corresponding permission.
     * </p>
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param page The desired page number to pagination. Start with 0. Default is 0
     * @param pageSize The desired page size to pagination. Start with 500. Default is 1000
     * @return The {@link HttpStatus} {@code OK} when the operation was made successfully
     */
    @ApiOperation(value = "Listar empresas", 
                  notes = "Lista as empresas disponíveis na aplicação", 
                  response = RespCompanyContract.class)
    @PreAuthorize("hasRole('" + CompanyPermissionConstants.ROLE_SEARCH_COMPANY + "')")
    @GetMapping(path = "/find", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public RespCompanyContract findCompany(
            @ApiParam(name = "page", value = "Número da página desejada a ser paginada. Valor mínimo: 0. Valor padrão: 0", defaultValue = "0")
            @RequestParam(name = "page", defaultValue = "0", required = false) @Min(value = 0, message = "{javax.validation.pagination.page}") Integer page,
            @ApiParam(name = "pageSize", value = "Quantidade de registros a serem exibidos por página que está sendo paginada. Valor mínimo: 500. Valor padrão: 1000", defaultValue = "1000")
            @RequestParam(name = "pageSize", defaultValue = "1000", required = false) @Min(value = 500, message = "{javax.validation.pagination.pageSize}") Integer pageSize) {

        var respCompanyContract = new RespCompanyContract();
        respCompanyContract.setCompany(this.companyService.findAll(page, pageSize).stream()
                .map(CompanyModelMapperUtil::toCompanyDTOMapper).collect(Collectors.toList()));
        return respCompanyContract;
    }

}
