package by.sapra.libraryservice.services.mappers;

import by.sapra.libraryservice.models.BookEntity;
import by.sapra.libraryservice.services.model.BookModel;
import by.sapra.libraryservice.testUtils.builders.models.CategoryEntityTestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static by.sapra.libraryservice.testUtils.builders.models.BookEntityTestDataBuilder.aBookEntity;
import static by.sapra.libraryservice.testUtils.builders.models.CategoryEntityTestDataBuilder.aCategoryEntity;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceMappersTestConfig.class)
class BookServiceMapperTest {
    @Autowired
    BookServiceMapper mapper;

    @Test
    void shouldMapEntityToModel() throws Exception {
        CategoryEntityTestDataBuilder cBuilder = aCategoryEntity();
        BookEntity expected = aBookEntity().withCategory(cBuilder).build();
        expected.setId(1);
        expected.getCategoryEntity().setId(1);

        BookModel actual = mapper.entityToModel(expected);

        assertAll(() -> {
            assertNotNull(actual, "не null");
            assertEquals(expected.getId(), actual.getId(), "правильный id");
            assertEquals(expected.getAuthor(), actual.getAuthor(), "правильный автор");
            assertEquals(expected.getTitle(), actual.getTitle(), "правильный титульник");
            assertEquals(expected.getCategoryEntity().getId(), actual.getCategoryId(), "правильная категория");
        });
    }
}