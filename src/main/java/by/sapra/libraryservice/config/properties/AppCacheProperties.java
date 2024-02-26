package by.sapra.libraryservice.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "app.cache")
public class AppCacheProperties {

    private final List<String> cacheNames = new ArrayList<>();
    private final Map<String, CacheProperties> caches = new HashMap<>();
    private CacheType cacheType;

    @Data
    public static class CacheProperties {
        private Duration expire = Duration.ZERO;
    }

    public interface CacheNames {
        String BOOKS_BY_CATEGORY_NAME = "bookByCategoryName";
        String BOOKS_BY_AUTHOR_AND_TITLE = "booksByAuthorAndTitle";
    }

    public enum CacheType {
        REDIS
    }
}
