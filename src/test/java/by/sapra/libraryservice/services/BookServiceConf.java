package by.sapra.libraryservice.services;

import by.sapra.libraryservice.services.impl.DatabaseBookService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class BookServiceConf {
    @Bean
    public BookService service() {
        return new DatabaseBookService();
    }
}
