package br.com.cpqd.billing.comptech.cache.service.core;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.benmanes.caffeine.cache.Cache;

import br.com.cpqd.billing.comptech.cache.service.core.config.CacheConfig;
import br.com.cpqd.billing.comptech.exception.core.ObjectNotFoundException;
import lombok.extern.log4j.Log4j2;

/**
 * This class implements a common cache bean to all services in Spring that are using cache feature.
 * <p>
 * Each class that uses the cache feature must extends this abstract class and override some required methods
 * to manipulate the objects in the cache. The {@link CacheManager} object already is available to all classes
 * that use this feature.
 * </p>
 * <p>
 * The class is generic and the type of object that will be manipulated must be passed when this class is
 * extended.
 * </p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@Log4j2
@Service
@Transactional
public abstract class ServiceCacheCommon<T> {

    /**
     * Injection of dependency of {@link CacheManager}
     */
    @Autowired
    protected CacheManager cacheManager;

    /**
     * Injection of dependency of {@link MessageSource}
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * Method responsible for getting the requested cache.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param cacheName The cache that will be used
     * @return The object that represents the cache or null if the cache was not found in the context
     */
    protected Cache<Object, Object> getNativeCaffeineCache(String cacheName) {

        try {
            return CacheConfig.getNativeCaffeineCache(this.cacheManager, cacheName);
        } catch (ObjectNotFoundException e) {
            return null;
        }
    }

    /**
     * Method responsible for getting all elements present in the cache.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param cacheName The cache that will be used to retrieve all elements in cache
     * @return The list with all elements found in cache
     * @throws ObjectNotFoundException Exception thrown when the cache is empty
     */
    @SuppressWarnings("unchecked")
    protected List<T> getAllObjectsInCache(String cacheName) throws ObjectNotFoundException {

        // Getting the requested cache
        Cache<Object, Object> nativeCaffeineCache = getNativeCaffeineCache(cacheName);

        // Getting the cache as map to iterate the elements present in the cache
        Collection<?> colObjects = nativeCaffeineCache.asMap().values();
        List<T> lstElements = (List<T>) colObjects.stream().collect(Collectors.toList());

        // Verify if there are elements in the cache
        if ((lstElements != null) && (!lstElements.isEmpty())) {
            log.warn(this.messageSource.getMessage("comptech.cache.component.getting.all.elements.in.cache",
                    null, LocaleContextHolder.getLocale()) + " " + cacheName);

            return lstElements;
        }

        // Cache haven't elements for type requested, so throws an exception
        throw new ObjectNotFoundException(
                this.messageSource.getMessage("comptech.cache.component.no.objects.in.cache", null,
                        LocaleContextHolder.getLocale()) + " " + cacheName);
    }

    /**
     * Method responsible for getting an object in the cache.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param cacheKey The cache key
     * @param cacheName The cache that will be used to retrieve the request object
     * @return The object found
     * @throws ObjectNotFoundException Exception thrown when the object was not found in cache
     */
    protected Object getObjectInCache(Object cacheKey, String cacheName) throws ObjectNotFoundException {

        // Getting the cache
        Cache<Object, Object> nativeCaffeineCache = getNativeCaffeineCache(cacheName);

        // Verify if the object is in cache and return it
        var object = nativeCaffeineCache.getIfPresent(cacheKey);
        if (object != null) {
            log.warn(this.messageSource.getMessage("comptech.cache.component.get.return.object.part1", null,
                    LocaleContextHolder.getLocale()) + " " + cacheKey.toString() + " "
                    + this.messageSource.getMessage("comptech.cache.component.get.return.object.part2", null,
                            LocaleContextHolder.getLocale())
                    + " " + cacheName);
            return object;
        }

        // Object not found, so throws an exception
        throw new ObjectNotFoundException(
                this.messageSource.getMessage("comptech.cache.component.object.not.found.in.cache.part1",
                        null, LocaleContextHolder.getLocale())
                        + " " + cacheKey.toString() + " "
                        + this.messageSource.getMessage(
                                "comptech.cache.component.object.not.found.in.cache.part2", null,
                                LocaleContextHolder.getLocale())
                        + " " + cacheName);
    }

    /**
     * Method responsible for cleaning all entries of one cache configured in the system.
     * <p>
     * Each child class must implement this method.
     * </p>
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     */
    public abstract void evictAllCacheValues();

    /**
     * Method responsible for cleaning a specific entry of one cache configured in the system.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param cacheKey The cache key
     * @param cacheName The cache that will be used to clean the request object
     */
    public final void evictSingleCacheValue(Object cacheKey, String cacheName) {

        var theCache = this.cacheManager.getCache(cacheName);
        if (theCache != null) {
            theCache.evict(cacheKey);
        }
    }

}
