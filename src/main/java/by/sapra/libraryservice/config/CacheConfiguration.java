package by.sapra.libraryservice.config;

import by.sapra.libraryservice.config.properties.AppCacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.HashMap;

@Configuration
@EnableCaching
@EnableConfigurationProperties(AppCacheProperties.class)
public class CacheConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "app.redis", name = "enable", havingValue = "true")
    public CacheManager redisCacheManager(AppCacheProperties properties, LettuceConnectionFactory lettuceConnectionFactory) {
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();

        HashMap<String, RedisCacheConfiguration> redisCacheConfiguration = new HashMap<>();

        properties.getCacheNames().forEach(cacheName ->
                redisCacheConfiguration.put(cacheName, RedisCacheConfiguration.defaultCacheConfig().entryTtl(
                            properties.getCaches().get(cacheName).getExpire()
                        )
                ));

        return RedisCacheManager.builder(lettuceConnectionFactory)
                .cacheDefaults(defaultCacheConfig)
                .withInitialCacheConfigurations(redisCacheConfiguration)
                .build();
    }
}
