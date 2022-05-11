package br.com.cpqd.billing.comptech.security.api.util;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import br.com.cpqd.billing.comptech.commons.entities.model.entity.enums.StatusEnum;
import br.com.cpqd.billing.comptech.security.model.contract.permission.PermissionItem;
import br.com.cpqd.billing.comptech.security.model.contract.profile.ReqAddProfileContract;
import br.com.cpqd.billing.comptech.security.model.contract.profile.ReqUpdateProfileContract;
import br.com.cpqd.billing.comptech.security.model.contract.profile.RespProfileContract;
import br.com.cpqd.billing.comptech.security.model.contract.profile.RespProfileSummaryContract;
import br.com.cpqd.billing.comptech.security.model.contract.user.ReqAddUserContract;
import br.com.cpqd.billing.comptech.security.model.contract.user.ReqUpdateUserContract;
import br.com.cpqd.billing.comptech.security.model.contract.user.RespUserContract;
import br.com.cpqd.billing.comptech.security.model.contract.user.RespUserSummaryContract;
import br.com.cpqd.billing.comptech.security.model.entity.ActivePassword;
import br.com.cpqd.billing.comptech.security.model.entity.Permission;
import br.com.cpqd.billing.comptech.security.model.entity.Profile;
import br.com.cpqd.billing.comptech.security.model.entity.User;
import br.com.cpqd.billing.comptech.security.model.entity.enums.StatusUserEnum;

/**
 * This class is responsible for configuring the {@link ModelMapper} library to convert classes on component.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
public class SecurityModelMapperUtil {

    /**
     * Attribute that represents the instance to {@link ModelMapper}
     */
    private static ModelMapper modelMapper;

    /**
     * Static block for initializing the {@link ModelMapper} object and define the mapping strategy
     */
    static {
        // ModelMapper global configuration
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // Converter's - Profile
        Converter<Integer, StatusEnum> converterStatusIntegerToStatusEnum = ctx -> ctx.getSource() == 0
                ? StatusEnum.INACTIVE
                : StatusEnum.ACTIVE;

        Converter<StatusEnum, String> converterStatusEnumToStatusString = ctx -> ctx.getSource()
                .ordinal() == 0 ? StatusEnum.INACTIVE.name() : StatusEnum.ACTIVE.name();

        // Converter's - User
        Converter<Long, Profile> converterProfileLongToProfileObject = ctx -> new Profile(ctx.getSource());

        Converter<String, ActivePassword> converterPasswordStringToActivePasswordObject = ctx -> new ActivePassword(
                ctx.getSource());

        Converter<Integer, StatusUserEnum> converterStatusIntegerToStatusUserEnum = ctx -> ctx
                .getSource() == 0 ? StatusUserEnum.INACTIVE
                        : ctx.getSource() == 1 ? StatusUserEnum.ACTIVE : StatusUserEnum.BLOCKED;

        Converter<StatusUserEnum, String> converterStatusUserEnumToStatusString = ctx -> ctx.getSource()
                .ordinal() == 0 ? StatusUserEnum.INACTIVE.name()
                        : ctx.getSource().ordinal() == 1 ? StatusUserEnum.ACTIVE.name()
                                : StatusUserEnum.BLOCKED.name();

        // Mapper's - Permission
        modelMapper.createTypeMap(Permission.class, PermissionItem.class)
                .<String> addMapping(src -> src.getKey(), (dest, v) -> dest.setKey(v))
                .<String> addMapping(src -> src.getDescription(), (dest, v) -> dest.setDescription(v));

        // Mapper's - Profile
        modelMapper.createTypeMap(ReqAddProfileContract.class, Profile.class)
                .addMappings(mapper -> mapper.using(converterStatusIntegerToStatusEnum)
                        .map(ReqAddProfileContract::getStatus, Profile::setStatusEnum));

        modelMapper.createTypeMap(ReqUpdateProfileContract.class, Profile.class)
                .addMappings(mapper -> mapper.using(converterStatusIntegerToStatusEnum)
                        .map(ReqUpdateProfileContract::getStatus, Profile::setStatusEnum));

        modelMapper.createTypeMap(Profile.class, RespProfileSummaryContract.class)
                .addMappings(mapper -> mapper.using(converterStatusEnumToStatusString)
                        .map(Profile::getStatusEnum, RespProfileSummaryContract::setStatus));

        modelMapper.createTypeMap(Profile.class, RespProfileContract.class)
                .addMappings(mapper -> mapper.using(converterStatusEnumToStatusString)
                        .map(Profile::getStatusEnum, RespProfileContract::setStatus));

        // Mapper's - User
        modelMapper.createTypeMap(ReqAddUserContract.class, User.class)
                .addMappings(mapper -> mapper.using(converterPasswordStringToActivePasswordObject)
                        .map(ReqAddUserContract::getPassword, User::setActivePassword))
                .addMappings(mapper -> mapper.using(converterProfileLongToProfileObject)
                        .map(ReqAddUserContract::getProfile, User::setProfile));

        modelMapper.createTypeMap(ReqUpdateUserContract.class, User.class)
                .addMappings(mapper -> mapper.using(converterStatusIntegerToStatusUserEnum)
                        .map(ReqUpdateUserContract::getStatus, User::setStatusUserEnum));

        modelMapper.createTypeMap(User.class, RespUserSummaryContract.class)
                .addMappings(mapper -> mapper.using(converterStatusUserEnumToStatusString)
                        .map(User::getStatusUserEnum, RespUserSummaryContract::setStatus))
                .<String> addMapping(src -> src.getProfile().getName(), (dest, v) -> dest.setProfile(v));

        modelMapper.createTypeMap(User.class, RespUserContract.class)
                .addMappings(mapper -> mapper.using(converterStatusUserEnumToStatusString)
                        .map(User::getStatusUserEnum, RespUserContract::setStatus))
                .<String> addMapping(src -> src.getProfile().getName(), (dest, v) -> dest.setProfile(v));
    }

    /**
     * Private constructor
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     */
    private SecurityModelMapperUtil() {

        // Private constructor
    }

    /**
     * Method responsible for converting the entity {@link Permission} to contract {@link PermissionItem}.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param permission The {@link Permission} object
     * @return The contract object converted
     */
    public static PermissionItem toPermissionDTOMapper(Permission permission) {

        return modelMapper.map(permission, PermissionItem.class);
    }

    /**
     * Method responsible for converting the contract {@link RequestAddProfileContract} to entity
     * {@link Profile}.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param reqAddProfileContract The {@link RequestAddProfileContract} object
     * @return The entity object converted
     */
    public static Profile toProfileMapper(ReqAddProfileContract reqAddProfileContract) {

        return modelMapper.map(reqAddProfileContract, Profile.class);
    }

    /**
     * Method responsible for converting the contract {@link RequestUpdateProfileContract} to entity
     * {@link Profile}.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param reqUpdateProfileContract The {@link RequestUpdateProfileContract} object
     * @return The entity object converted
     */
    public static Profile toProfileMapper(ReqUpdateProfileContract reqUpdateProfileContract) {

        return modelMapper.map(reqUpdateProfileContract, Profile.class);
    }

    /**
     * Method responsible for converting the entity {@link Profile} to contract
     * {@link ResponseProfileSummaryContract}.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param profile The {@link Profile} object
     * @return The contract object converted
     */
    public static RespProfileSummaryContract toResponseProfileSummaryContractMapper(Profile profile) {

        return modelMapper.map(profile, RespProfileSummaryContract.class);
    }

    /**
     * Method responsible for converting the entity {@link Profile} to contract
     * {@link ResponseProfileContract}.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param profile The {@link Profile} object
     * @return The contract object converted
     */
    public static RespProfileContract toResponseProfileContractMapper(Profile profile) {

        return modelMapper.map(profile, RespProfileContract.class);
    }

    /**
     * Method responsible for converting the contract {@link RequestAddUserContract} to entity {@link User}.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param reqAddUserContract The {@link RequestAddUserContract} object
     * @return The entity object converted
     */
    public static User toUserMapper(ReqAddUserContract reqAddUserContract) {

        return modelMapper.map(reqAddUserContract, User.class);
    }

    /**
     * Method responsible for converting the contract {@link RequestUpdateUserContract} to entity
     * {@link User}.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param reqUpdateUserContract The {@link RequestUpdateUserContract} object
     * @return The entity object converted
     */
    public static User toUserMapper(ReqUpdateUserContract reqUpdateUserContract) {

        return modelMapper.map(reqUpdateUserContract, User.class);
    }

    /**
     * Method responsible for converting the entity {@link User} to contract
     * {@link ResponseUserSummaryContract}.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param user The {@link User} object
     * @return The contract object converted
     */
    public static RespUserSummaryContract toResponseUserSummaryContractMapper(User user) {

        return modelMapper.map(user, RespUserSummaryContract.class);
    }

    /**
     * Method responsible for converting the entity {@link User} to contract {@link ResponseUserContract}.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param user The {@link User} object
     * @return The contract object converted
     */
    public static RespUserContract toResponseUserContractMapper(User user) {

        return modelMapper.map(user, RespUserContract.class);
    }

}
