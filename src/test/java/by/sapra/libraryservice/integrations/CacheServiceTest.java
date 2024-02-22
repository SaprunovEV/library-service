package by.sapra.libraryservice.integrations;

import by.sapra.libraryservice.services.BookService;
import by.sapra.libraryservice.services.mappers.BookServiceMapper;
import by.sapra.libraryservice.testUtils.builders.service.BookModelBuilder;
import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CacheServiceConf.class)
@Testcontainers
public class CacheServiceTest {

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


    @Autowired
    private BookService bookService;

    @Autowired
    BookServiceMapper mapper;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Test
    void shouldCorrectConfigureTest() throws Exception {
        redisTemplate.opsForList().rightPush("key", BookModelBuilder.aBook().build());

        Set<String> keys = redisTemplate.keys("*");

        Object key = redisTemplate.opsForList().rightPop("key");

        System.out.println(keys);
    }
}
