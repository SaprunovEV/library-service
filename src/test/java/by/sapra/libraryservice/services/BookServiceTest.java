package by.sapra.libraryservice.services;

import by.sapra.libraryservice.config.AbstractDataTest;
import by.sapra.libraryservice.models.BookEntity;
import by.sapra.libraryservice.services.mappers.BookServiceMapper;
import by.sapra.libraryservice.services.model.BookModel;
import by.sapra.libraryservice.services.model.ServiceFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static by.sapra.libraryservice.testUtils.builders.models.BookEntityTestDataBuilder.aBookEntity;
import static by.sapra.libraryservice.testUtils.builders.service.BookModelBuilder.aBook;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = BookServiceConf.class)
class BookServiceTest extends AbstractDataTest {
    @Autowired
    private BookService service;

    @MockBean
    private BookServiceMapper mapper;

    @Test
    void whenFilterBook_thenReturnModel() throws Exception {
        BookEntity entity = getFacade().save(aBookEntity());

        ServiceFilter filter = new ServiceFilter();
        filter.setTitle(entity.getTitle());
        filter.setAuthor(entity.getAuthor());

        BookModel model = aBook()
                .withId(entity.getId())
                .withAuthor(entity.getAuthor())
                .withTitle(entity.getTitle())
                .build();
        when(mapper.entityToModel(eq(entity)))
                .thenReturn(model);

        BookModel actual = service.filterBook(filter);

        assertAll(() -> {
            assertNotNull(actual);
            assertEquals(model, actual);
        });

        verify(mapper, times(1)).entityToModel(eq(entity));
    }

    @Test
    void whenFilterBookNotFound_thenReturnModel() throws Exception {
        BookEntity entity = aBookEntity().build();

        ServiceFilter filter = new ServiceFilter();
        filter.setTitle(entity.getTitle());
        filter.setAuthor(entity.getAuthor());

        BookModel actual = service.filterBook(filter);

        assertAll(() -> {
            assertNull(actual);
        });

        verify(mapper, times(0)).entityToModel(eq(entity));
    }
}