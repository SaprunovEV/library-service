package by.sapra.libraryservice.services;

import by.sapra.libraryservice.repository.BookRepository;
import by.sapra.libraryservice.services.impl.DatabaseBookService;
import by.sapra.libraryservice.services.mappers.BookServiceMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class BookServiceConf {
    @Bean
    public BookService service(BookRepository repo, BookServiceMapper mapper) {
        return new DatabaseBookService(repo, mapper);
    }
}
