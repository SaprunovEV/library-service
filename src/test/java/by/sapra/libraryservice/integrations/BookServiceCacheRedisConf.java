package by.sapra.libraryservice.integrations;

import by.sapra.libraryservice.repository.BookRepository;
import by.sapra.libraryservice.services.BookService;
import by.sapra.libraryservice.services.impl.DatabaseBookService;
import by.sapra.libraryservice.services.mappers.BookServiceMapper;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

@TestConfiguration
@MockBean(value = BookRepository.class)
public class BookServiceCacheRedisConf {

    @Bean
    public BookServiceMapper mapper() {
        return Mockito.mock(BookServiceMapper.class);
    }


    @Bean
    public BookService bookService(BookRepository repo, BookServiceMapper mapper) {
        return new DatabaseBookService(repo, mapper);
    }
}
