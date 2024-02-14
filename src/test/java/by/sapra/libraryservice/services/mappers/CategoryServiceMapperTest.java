package by.sapra.libraryservice.services.mappers;

import by.sapra.libraryservice.config.AbstractDataTest;
import by.sapra.libraryservice.models.CategoryEntity;
import by.sapra.libraryservice.services.exceptions.CategoryNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static by.sapra.libraryservice.testUtils.builders.models.CategoryEntityTestDataBuilder.aCategoryEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = CategoryServiceMapperConfig.class)
class CategoryServiceMapperTest extends AbstractDataTest {
    @Autowired
    CategoryServiceMapper mapper;

    @Test
    void shouldMapCategoryIdToCategoryEntityFromDb() throws Exception {
        CategoryEntity expected = getFacade().save(aCategoryEntity());

        CategoryEntity actual = mapper.categoryIdToCategoryEntity(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowException_ifCategoryNotFound() throws Exception {
        assertThrows(CategoryNotFoundException.class, () -> mapper.categoryIdToCategoryEntity(1));
    }
}