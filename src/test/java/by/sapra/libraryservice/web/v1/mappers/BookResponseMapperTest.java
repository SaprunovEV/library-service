package by.sapra.libraryservice.web.v1.mappers;

import by.sapra.libraryservice.services.model.BookModel;
import by.sapra.libraryservice.testUtils.builders.service.BookModelBuilder;
import by.sapra.libraryservice.web.v1.models.BookResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static by.sapra.libraryservice.testUtils.builders.service.BookModelBuilder.aBook;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MappersTestConfig.class)
class BookResponseMapperTest {
    @Autowired
    BookResponseMapper mapper;

    @Test
    void shouldCorrectMapBookModelToBookResponse() throws Exception {
        BookModel expected = aBook().build();

        BookResponse actual = mapper.bookModelToResponse(expected);

        assertAll(() -> {
           assertNotNull(actual);
           assertEquals(expected.getId(), actual.getId());
           assertEquals(expected.getCategoryId(), actual.getCategoryId());
           assertEquals(expected.getTitle(), actual.getTitle());
           assertEquals(expected.getAuthor(), actual.getAuthor());
        });
    }
}