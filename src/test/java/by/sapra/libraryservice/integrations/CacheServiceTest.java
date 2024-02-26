package by.sapra.libraryservice.integrations;

import by.sapra.libraryservice.config.properties.AppCacheProperties;
import by.sapra.libraryservice.services.BookService;
import by.sapra.libraryservice.services.impl.NotCachedBookServiceQualifier;
import by.sapra.libraryservice.services.model.BookModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static by.sapra.libraryservice.testUtils.builders.service.BookModelBuilder.aBook;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BookServiceCacheRedisConf.class)
public class CacheServiceTest extends AbstractRedisTest {

    @Autowired
    @NotCachedBookServiceQualifier
    private BookService mockService;

    @Autowired
    private BookService bookService;

    @BeforeEach
    void setUp() {
        Mockito.reset(mockService);
    }

    @Test
    void shouldCorrectConfigureTest() throws Exception {
        redisTemplate.opsForList().rightPush("key", aBook().build());

        Set<String> keys = redisTemplate.keys("*");

        Object key = redisTemplate.opsForList().rightPop("key");

        System.out.println(keys);
    }

    @Test
    void whenFindByCategoryName_thenPushToCache() throws Exception {
        assertTrue(Objects.requireNonNull(redisTemplate.keys("*")).isEmpty(), "в начале теста ключей быть не должно!");

        String testCategoryName = "category";

        List<BookModel> expected = List.of(
                BookModel.builder().id(1).build(),
                BookModel.builder().id(2).build(),
                BookModel.builder().id(3).build(),
                BookModel.builder().id(4).build()
        );
        when(mockService.getBookByCategory(testCategoryName))
                .thenReturn(expected);

        bookService.getBookByCategory(testCategoryName);

        assertFalse(Objects.requireNonNull(redisTemplate.keys("*")).isEmpty(), "посте выполнения метода, кеш не должен быть пустым");
        assertEquals(expected.size(),
                ((List<BookModel>)redisTemplate.opsForValue()
                        .get(AppCacheProperties.CacheNames.BOOKS_BY_CATEGORY_NAME + "::" + testCategoryName))
                        .size(),
                "после выполнения теста должна быть запись в кеш!");
    }

    @Test
    void whenFindByCategory_andCacheHaveTheKey_thenNotCallDelegateMethod() throws Exception {
        String testCategoryName = "category";

        redisTemplate.opsForValue().set(AppCacheProperties.CacheNames.BOOKS_BY_CATEGORY_NAME + "::" + testCategoryName, List.of(aBook().build()));

        List<BookModel> actual = bookService.getBookByCategory(testCategoryName);

        assertFalse(actual.isEmpty());
        assertEquals(1, actual.size());

        verify(mockService, times(0)).getBookByCategory(testCategoryName);
    }
}
