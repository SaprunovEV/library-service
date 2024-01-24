package by.sapra.libraryservice.services;

import by.sapra.libraryservice.config.AbstractDataTest;
import by.sapra.libraryservice.services.mappers.BookServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = BookServiceConf.class)
class BookServiceTest extends AbstractDataTest {
    @Autowired
    private BookService service;

    @MockBean
    private BookServiceMapper mapper;


}