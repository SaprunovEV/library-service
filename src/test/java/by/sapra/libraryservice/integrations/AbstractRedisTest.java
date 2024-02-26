package by.sapra.libraryservice.integrations;

import com.redis.testcontainers.RedisContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

@Testcontainers
@ContextConfiguration(classes = CacheRedisConfig.class)
public abstract class AbstractRedisTest {
    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

    @Container
    protected static RedisContainer redisContainer =
            new RedisContainer(DockerImageName.parse("redis:7.0.12"))
                    .withReuse(true)
                    .withExposedPorts(6379);

    @DynamicPropertySource
    public static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("app.redis.enable", () -> "true");
        registry.add("app.cache.type", () -> "redis");
        registry.add("app.cache.cache-names", () -> List.of("bookByCategoryName", "booksByAuthorAndTitle"));
        registry.add("app.cache.caches.bookByCategoryName.expire", () -> "10m");
        registry.add("app.cache.caches.booksByAuthorAndTitle.expire", () -> "10m");

        registry.add("spring.data.redis.host", redisContainer::getHost);
        registry.add("spring.data.redis.port", () -> redisContainer.getMappedPort(6379).toString());
    }
}
