package by.sapra.libraryservice.integrations;

import by.sapra.libraryservice.config.CacheConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@AutoConfigureCache
@ImportAutoConfiguration
@Import(value = CacheConfiguration.class)
public class AbstractCacheConfig {
}
