package by.sapra.libraryservice.services.mappers;

import by.sapra.libraryservice.models.BookEntity;
import by.sapra.libraryservice.models.CategoryEntity;
import by.sapra.libraryservice.services.model.BookModel;
import by.sapra.libraryservice.testUtils.builders.models.CategoryEntityTestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static by.sapra.libraryservice.testUtils.builders.models.BookEntityTestDataBuilder.aBookEntity;
import static by.sapra.libraryservice.testUtils.builders.models.CategoryEntityTestDataBuilder.aCategoryEntity;
import static by.sapra.libraryservice.testUtils.builders.service.BookModelBuilder.aBook;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceMappersTestConfig.class)
class BookServiceMapperTest {
    @Autowired
    BookServiceMapper mapper;

    @MockBean
    CategoryServiceMapper categoryMapper;

    @Test
    void shouldMapEntityToModel() throws Exception {
        CategoryEntityTestDataBuilder cBuilder = aCategoryEntity();
        BookEntity expected = aBookEntity().withCategory(cBuilder).build();
        expected.setId(1);
        expected.getCategoryEntity().setId(1);

        BookModel actual = mapper.entityToModel(expected);

        assertBookModel(expected, actual);
    }

    private static void assertBookModel(BookEntity expected, BookModel actual) {
        assertAll(() -> {
            assertNotNull(actual, "не null");
            assertEquals(expected.getId(), actual.getId(), "правильный id");
            assertEquals(expected.getAuthor(), actual.getAuthor(), "правильный автор");
            assertEquals(expected.getTitle(), actual.getTitle(), "правильный титульник");
            assertEquals(expected.getCategoryEntity().getId(), actual.getCategoryId(), "правильная категория");
        });
    }

    @Test
    void shouldMapListOfEntityToListOfModel() throws Exception {
        CategoryEntityTestDataBuilder cBuilder = aCategoryEntity();

        List<BookEntity> expected = List.of(
                aBookEntity().withCategory(cBuilder).build(),
                aBookEntity().withCategory(cBuilder).build(),
                aBookEntity().withCategory(cBuilder).build(),
                aBookEntity().withCategory(cBuilder).build(),
                aBookEntity().withCategory(cBuilder).build()
        );

        for (int i = 0; i < expected.size(); i++) {
            expected.get(i).setId(i);
        }

        expected.get(0).getCategoryEntity().setId(1);

        List<BookModel> actual = mapper.entityListToBookModelList(expected);

        assertAll(() -> {
            assertNotNull(actual);
            assertEquals(expected.size(), actual.size());
            for (int i = 0; i < expected.size(); i++) {
                assertBookModel(expected.get(i), actual.get(i));
            }
        });
    }

    @Test
    void shouldMapModelToEntity() throws Exception {
        BookModel expected = aBook().build();

        CategoryEntity category = aCategoryEntity().build();
        category.setId(expected.getCategoryId());

        when(categoryMapper.categoryIdToCategoryEntity(expected.getCategoryId()))
                .thenReturn(category);

        BookEntity actual = mapper.modelToEntity(expected);

        assertAll(() -> {
            assertNotNull(actual);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getAuthor(), actual.getAuthor());
            assertEquals(expected.getTitle(), actual.getTitle());
            assertEquals(category, actual.getCategoryEntity());
        });

        verify(categoryMapper, times(1)).categoryIdToCategoryEntity(expected.getCategoryId());
    }
}