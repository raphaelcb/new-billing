package br.com.cpqd.billing.comptech.audit.model.entity;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * This class represents a model for {@link AuditEntityResult} entity.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@ApiModel(value = "Response Audit", 
          description = "Modelo de entidade padrão responsável pela estrutura de resposta da auditoria de todas as entidades de negócio envolvidas na aplicação")
@Data
@SuppressWarnings("serial")
public class AuditEntityResult implements Serializable {

    /**
     * Attribute that represents the username of who made the operation (ADD/UPDATE/DELETE) on application
     */
    @ApiModelProperty(name = "username", value = "Identificador (login) do usuário quem fez a operação na aplicação", position = 1)
    private String username;

    /**
     * Attribute that represents the IP address of the client machine
     */
    @ApiModelProperty(name = "ip", value = "IP do terminal da máquina cliente que fez a operação na aplicação", position = 2)
    private String ip;

    /**
     * Attribute that represents the timestamp of the operation (ADD/UPDATE/DELETE)
     */
    @ApiModelProperty(name = "timestamp", value = "Data/Hora da operação de auditoria", position = 3)
    private Date timestamp;

    /**
     * Attribute that represents the informations field of an entity that had the values ADD/UPDATE/DELETE
     */
    @ApiModelProperty(name = "details", value = "Registro completo com todos os detalhes em um determinado espaço/tempo", position = 4)
    private String details;

    /**
     * Attribute that represents the kind of operation (ADD/UPDATE/DELETE)
     */
    @ApiModelProperty(name = "operation", value = "Tipo da operação. Valores válidos: Inclusão / Alteração / Exclusão", position = 5)
    private String operation;

    /**
     * Attribute that represents the revision for audit history
     */
    @ApiModelProperty(name = "revision", value = "Identificador da revisão da auditoria", position = 6)
    private Integer revision;

}
