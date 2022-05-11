package br.com.cpqd.billing.comptech.cache.service.core.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.cpqd.billing.comptech.exception.core.ObjectNotFoundException;
import lombok.extern.log4j.Log4j2;

/**
 * This class is responsible for configuring all caches for application and providing a generic method to
 * retrieve the objects in cache.
 * <p>
 * The informations that are most frequently used in application will be stay on caches to fast retrieve and
 * the cache library chosen to provide this feature was the {@link CaffeineCache}. When a value is asked for
 * some functionality so a method defined in this class will try retrieve this value through cache key. In
 * case that object is not in cache the default behavior is find it in database.
 * </p>
 * <p>
 * Each cache is defined through {@link Bean}, that is managed by Spring container and has the particular
 * features of use to caches configured.
 * <p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @see <a href="https://spring.io/guides/gs/caching">Caching Data with Spring -
 *      https://spring.io/guides/gs/caching</a>
 * @see <a href=
 *      "https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-caching.html">Chapter 33
 *      - Caching - Part IV: Spring boot features -
 *      https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-caching.html</a>
 * @see <a href= "https://www.baeldung.com/spring-cache-tutorial">A Guide To Caching in Spring -
 *      https://www.baeldung.com/spring-cache-tutorial</a>
 * @see <a href="https://github.com/ben-manes/caffeine/wiki">Caffeine Wiki -
 *      https://github.com/ben-manes/caffeine/wiki</a>
 * @see <a href="https://github.com/ben-manes/caffeine">Caffeine source code -
 *      https://github.com/ben-manes/caffeine</a>
 * @see <a href= "https://www.javadoc.io/doc/com.github.ben-manes.caffeine/caffeine/2.6.2">Caffeine API Docs -
 *      https://www.javadoc.io/doc/com.github.ben-manes.caffeine/caffeine/2.6.2</a>
 */
@Log4j2
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Method responsible for getting the native caffeine cache object to use in much places of the
     * application.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param cacheManager The {@link CacheManager} object
     * @param caffeineCache The string that represents the type of cache. The valid values are present in this
     *            class with private attributes
     * @return The native caffeine cache object
     * @throws ObjectNotFoundException Exception thrown when the cache was not found
     */
    @SuppressWarnings("unchecked")
    public static com.github.benmanes.caffeine.cache.Cache<Object, Object> getNativeCaffeineCache(
            CacheManager cacheManager, String caffeineCache) throws ObjectNotFoundException {

        var cache = cacheManager.getCache(caffeineCache);
        if (cache == null) {
            log.error("The requested cache " + caffeineCache + " was not found. Nothing to do.");
            throw new ObjectNotFoundException("The cache was not found");
        }

        // Retrieving and return the cache
        return (com.github.benmanes.caffeine.cache.Cache<Object, Object>) cache.getNativeCache();
    }

    /**
     * Method responsible for retrieving any type of object in cache present in the application.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param key The cache key
     * @param cache The cache that will be used to retrieve the request object
     * @return The object found
     * @throws ObjectNotFoundException Exception thrown when the object was not found in cache or the key
     *             doesn't exist
     */
    @SuppressWarnings("unchecked")
    public static Object getObjectInCache(Object key, org.springframework.cache.Cache cache)
            throws ObjectNotFoundException {

        if (key != null) {
            com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCaffeineCache = (com.github.benmanes.caffeine.cache.Cache<Object, Object>) cache
                    .getNativeCache();
            var object = nativeCaffeineCache.getIfPresent(key);

            if (object == null) {
                throw new ObjectNotFoundException(
                        "Object with key " + key.toString() + " not found in cache");
            } else {
                return object;
            }
        }

        throw new ObjectNotFoundException("Key to find the object in cache is null");
    }

}
