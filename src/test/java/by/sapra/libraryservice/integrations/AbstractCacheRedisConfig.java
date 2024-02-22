package by.sapra.libraryservice.integrations;

import by.sapra.libraryservice.config.RedisConfig;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@ImportAutoConfiguration
@EnableConfigurationProperties(RedisProperties.class)
@Import(value = RedisConfig.class)
public class AbstractCacheRedisConfig  extends AbstractCacheConfig {
}
