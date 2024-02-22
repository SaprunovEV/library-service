package by.sapra.libraryservice.integrations;

import by.sapra.libraryservice.services.BookService;
import by.sapra.libraryservice.services.mappers.BookServiceMapper;
import by.sapra.libraryservice.testUtils.builders.service.BookModelBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BookServiceCacheRedisConf.class)
public class CacheServiceTest extends AbstractRedisTest {

    @Autowired
    private BookService bookService;

    @Autowired
    BookServiceMapper mapper;

    @Test
    void shouldCorrectConfigureTest() throws Exception {
        redisTemplate.opsForList().rightPush("key", BookModelBuilder.aBook().build());

        Set<String> keys = redisTemplate.keys("*");

        Object key = redisTemplate.opsForList().rightPop("key");

        System.out.println(keys);
    }
}
