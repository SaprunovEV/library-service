package by.sapra.libraryservice.web.v1.mappers;

import by.sapra.libraryservice.services.model.BookModel;
import by.sapra.libraryservice.testUtils.builders.service.BookModelBuilder;
import by.sapra.libraryservice.web.v1.models.BookListResponse;
import by.sapra.libraryservice.web.v1.models.BookResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

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

        assertBookResponse(expected, actual);
    }
    @Test
    void shouldMapListOfBookModelToBookListResponse() throws Exception {
        List<BookModel> expected = List.of(
                aBook().withId(1).build(),
                aBook().withId(2).build(),
                aBook().withId(3).build()
        );

        BookListResponse actual = mapper.bookModelListToListResponse(expected);

        assertAll(() -> {
            assertNotNull(actual);
            assertNotNull(actual.getBooks());
            assertEquals(expected.size(), actual.getBooks().size());
            for (int i = 0; i < expected.size(); i++) {
                assertBookResponse(expected.get(i), actual.getBooks().get(i));
            }
        });
    }

    private static void assertBookResponse(BookModel expected, BookResponse actual) {
        assertAll(() -> {
            assertEquals(expected.getAuthor(), actual.getAuthor());
            assertNotNull(actual);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getCategoryId(), actual.getCategoryId());
            assertEquals(expected.getTitle(), actual.getTitle());
        });
    }
}