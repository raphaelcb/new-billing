package br.com.cpqd.billing.comptech.security.service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.benmanes.caffeine.cache.Cache;

import br.com.cpqd.billing.comptech.cache.service.core.ServiceCacheCommon;
import br.com.cpqd.billing.comptech.commons.entities.model.entity.enums.StatusEnum;
import br.com.cpqd.billing.comptech.exception.core.ObjectNotFoundException;
import br.com.cpqd.billing.comptech.exception.core.rest.RestObjectNotFoundException;
import br.com.cpqd.billing.comptech.security.model.entity.Permission;
import br.com.cpqd.billing.comptech.security.model.entity.Profile;
import br.com.cpqd.billing.comptech.security.repository.ProfileRepository;
import br.com.cpqd.billing.comptech.security.service.config.SecurityCacheConfig;
import lombok.extern.log4j.Log4j2;

/**
 * This class is responsible for Service layer of the application.
 * <p>
 * It contains the methods to manipulate the data for {@link Profile}.
 * </p>
 * <p>
 * This service will use data in cache and for this it need to extends the {@link ServiceCacheCommon}.
 * </p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @see br.com.cpqd.billing.comptech.cache.service.core.ServiceCacheCommon
 */
@Log4j2
@Service
@Transactional
@CacheConfig(cacheNames = {SecurityCacheConfig.CACHE_PROFILE})
public class ProfileService extends ServiceCacheCommon<Profile> {

    /**
     * Injection of dependency of {@link PermissionService}
     */
    @Autowired
    private PermissionService permissionService;

    /**
     * Injection of dependency of {@link ProfileRepository}
     */
    @Autowired
    private ProfileRepository profileRepository;

    /**
     * Injection of dependency of {@link MessageSource}
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * Method responsible for adding a new {@link Profile}.
     * <p>
     * The result of this operation is put the element saved in cache to future use.
     * </p>
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param profile The {@link Profile} object to persist
     * @param setPermissionKey The set with all {@link Permission} {@code key} attribute values
     * @return The {@link Profile} object persisted
     * @throws DataAccessException Exception thrown when some persistence error occurs
     */
    @CachePut(key = "#profile.id")
    public Profile addProfile(Profile profile, Set<String> setPermissionKey) throws DataAccessException {

        // Getting all permission and associate with profile
        profile.setPermission(this.permissionService.findByKeyIn(setPermissionKey));

        // Save the profile
        return this.profileRepository.saveAndFlush(profile);
    }

    /**
     * Method responsible for updating a {@link Profile}.
     * <p>
     * The result of this operation is put the element saved in cache to future use.
     * </p>
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param profile The {@link Profile} object to persist
     * @param setPermissionKey The set with all {@link Permission} {@code key} attribute values
     * @return The {@link Profile} object persisted
     * @throws DataAccessException Exception thrown when some persistence error occurs
     */
    @CachePut(key = "#profile.id")
    public Profile updateProfile(Profile profile, Set<String> setPermissionKey) throws DataAccessException {

        this.findProfileById(profile.getId());
        return addProfile(profile, setPermissionKey);
    }

    /**
     * Method responsible for deleting a {@link Profile}.
     * <p>
     * The result of this operation is remove the element from cache.
     * </p>
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param id The {@link Profile} {@code id} attribute
     * @throws DataAccessException Exception thrown when some persistence error occurs
     */
    @CacheEvict(key = "#id")
    public void deleteProfile(Long id) throws DataAccessException {

        // Getting the profile
        var profile = this.findProfileById(id);

        // Delete the profile
        this.profileRepository.delete(profile);
    }

    /**
     * Method responsible for getting the {@link Profile} through {@code id} attribute.
     * <p>
     * First the object will be search in the cache associated with this type of object and if it not found so
     * the search will be made in database through {@link ProfileRepository}.
     * </p>
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param id The {@link Profile} {@code id} attribute
     * @return The {@link Profile} found
     * @throws RestObjectNotFoundException Exception thrown if the profile was not found
     */
    @Cacheable(key = "#result.id", condition = "#result != null", sync = true)
    public Profile findProfileById(Long id) {

        try {
            // Looking for the object in the cache
            return (Profile) super.getObjectInCache(id, SecurityCacheConfig.CACHE_PROFILE);
        } catch (ObjectNotFoundException e) {
            log.warn(
                    this.messageSource.getMessage("comptech.security.component.profile.getting.from.database",
                            null, LocaleContextHolder.getLocale()));
            return this.profileRepository.findById(id)
                    .orElseThrow(() -> new RestObjectNotFoundException(
                            this.messageSource.getMessage("comptech.security.component.profile.notfound",
                                    null, LocaleContextHolder.getLocale())));
        }
    }

    /**
     * Method responsible for getting all {@link Profile} through {@code status} attribute.
     * <p>
     * First the objects will be search in the cache associated with this type of object and if it not found
     * so the search will be made in database through {@link ProfileRepository}.
     * </p>
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param statusEnum The {@link StatusEnum} values available
     * @return The list with all {@link Profile} found and ordered by your name ASC
     * @throws RestObjectNotFoundException Exception thrown if there are not profiles to return
     */
    public List<Profile> findProfileByStatusEnumAndVisibleTrue(StatusEnum statusEnum) {

        try {
            // Getting the list of profile in cache, sorted the elements found and return it
            var lstProfile = super.getAllObjectsInCache(SecurityCacheConfig.CACHE_PROFILE);
            log.info(this.messageSource.getMessage("comptech.security.component.profile.getting.from.cache",
                    null, LocaleContextHolder.getLocale()));
            return lstProfile.stream().sorted(Comparator.comparing(Profile::getName))
                    .collect(Collectors.toList());
        } catch (ObjectNotFoundException e) {
            // The cache haven't elements, so fire the action to find the elements in database
            Cache<Object, Object> nativeCaffeineCache = super.getNativeCaffeineCache(
                    SecurityCacheConfig.CACHE_PROFILE);

            /*
             * Call the method to search all profile of the application with status active because there are
             * not elements in the cache
             */
            log.info(
                    this.messageSource.getMessage("comptech.security.component.profile.getting.from.database",
                            null, LocaleContextHolder.getLocale()));
            var lstProfile = this.profileRepository
                    .findByStatusEnumAndVisibleTrueOrderByNameAsc(StatusEnum.ACTIVE);

            if ((lstProfile == null) || (lstProfile.isEmpty())) {
                throw new RestObjectNotFoundException(this.messageSource.getMessage("global.no.data.found",
                        null, LocaleContextHolder.getLocale()));
            }

            if (nativeCaffeineCache != null) {
                // Run for each element found and put it in cache
                lstProfile.forEach(element -> nativeCaffeineCache.put(element.getId(), element));
            }

            // Return the list of profile found
            return lstProfile;
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see br.com.cpqd.cache.service.ServiceCacheCommon#evictAllCacheValues()
     */
    @Override
    @CacheEvict(allEntries = true)
    public void evictAllCacheValues() {

        // Override
    }

}
