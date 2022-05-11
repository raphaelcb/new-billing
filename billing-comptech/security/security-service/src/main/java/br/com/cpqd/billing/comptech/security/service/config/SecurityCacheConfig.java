package br.com.cpqd.billing.comptech.security.service.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import br.com.cpqd.billing.comptech.cache.service.core.config.CacheConfig;
import br.com.cpqd.billing.comptech.security.model.entity.Profile;

/**
 * This class is responsible for configuring the cache use for this component.
 * <p>
 * For this component the caches that will be configured and used are:
 * <ul>
 * <li>{@link Profile}</li>
 * </ul>
 * </p>
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@Configuration
public class SecurityCacheConfig {

    // Constant field values that represents all available caches to component
    public static final String CACHE_PROFILE = "profileCache";

    /**
     * Attribute that represents a cache to {@link Profile}
     */
    private CaffeineCache caffeineCacheProfile;

    /**
     * Bean responsible for defining a cache to {@link Profile}.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The {@link CaffeineCache} loaded
     */
    @Bean
    public CaffeineCache cacheProfile() {

        /*
         * Initialize the cache parameters and the default method that will retrieve the object in cache when
         * necessary. The default parameters to cache are initial and maximum capacity of the cache, the time
         * expiration of the entries, the time to refresh the cache and the statistics of the cache
         */
        LoadingCache<Object, Object> cache = Caffeine.newBuilder().initialCapacity(10).maximumSize(100)
                .expireAfterAccess(8, TimeUnit.HOURS).refreshAfterWrite(1, TimeUnit.SECONDS).weakKeys()
                .recordStats().build(key -> CacheConfig.getObjectInCache(key, this.caffeineCacheProfile));

        /*
         * Define the Caffeine cache with your name, the properties defined previously and the condition that
         * null values cannot be in cache
         */
        this.caffeineCacheProfile = new CaffeineCache(CACHE_PROFILE, cache, false);
        return this.caffeineCacheProfile;
    }

}
