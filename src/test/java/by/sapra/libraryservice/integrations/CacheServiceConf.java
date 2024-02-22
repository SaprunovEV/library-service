package by.sapra.libraryservice.integrations;

import by.sapra.libraryservice.config.RedisConfig;
import by.sapra.libraryservice.repository.BookRepository;
import by.sapra.libraryservice.services.BookService;
import by.sapra.libraryservice.services.impl.DatabaseBookService;
import by.sapra.libraryservice.services.mappers.BookServiceMapper;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@TestConfiguration
@AutoConfigureCache
@ImportAutoConfiguration
@EnableConfigurationProperties(RedisProperties.class)
@MockBean(value = BookRepository.class)
@Import(value = RedisConfig.class)
public class CacheServiceConf extends AbstractCacheConfig{

    @Bean
    public BookServiceMapper mapper() {
        return Mockito.mock(BookServiceMapper.class);
    }


    @Bean
    public BookService bookService(BookRepository repo, BookServiceMapper mapper) {
        return new DatabaseBookService(repo, mapper);
    }
}
