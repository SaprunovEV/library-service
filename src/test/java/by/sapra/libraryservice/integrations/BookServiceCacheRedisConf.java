package by.sapra.libraryservice.integrations;

import by.sapra.libraryservice.services.BookService;
import by.sapra.libraryservice.services.impl.CacheBookServiceDecorator;
import by.sapra.libraryservice.services.impl.NotCachedBookServiceQualifier;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class BookServiceCacheRedisConf {

    @Bean
    @NotCachedBookServiceQualifier
    public BookService notCacheBook() {
        return Mockito.mock(BookService.class);
    }


    @Bean
    public BookService bookService(BookService bs) {
        return new CacheBookServiceDecorator(bs);
    }
}
