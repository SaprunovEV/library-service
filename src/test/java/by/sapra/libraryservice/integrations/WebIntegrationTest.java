package by.sapra.libraryservice.integrations;

import by.sapra.libraryservice.config.AbstractDataTest;
import by.sapra.libraryservice.models.BookEntity;
import by.sapra.libraryservice.models.CategoryEntity;
import by.sapra.libraryservice.testUtils.StringTestUtils;
import by.sapra.libraryservice.testUtils.UpsertBookRequestBuilder;
import by.sapra.libraryservice.testUtils.builders.TestDataBuilder;
import by.sapra.libraryservice.web.v1.models.UpsertBookRequest;
import by.sapra.libraryservice.web.v1.models.WebBookFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.testcontainers.RedisContainer;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static by.sapra.libraryservice.testUtils.builders.models.BookEntityTestDataBuilder.aBookEntity;
import static by.sapra.libraryservice.testUtils.builders.models.CategoryEntityTestDataBuilder.aCategoryEntity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TypeExcludeFilters(DataJpaTypeExcludeFilter.class)
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@ImportAutoConfiguration
@AutoConfigureMockMvc
@ContextConfiguration(classes = CacheRedisConfig.class)
@Testcontainers
public class WebIntegrationTest extends AbstractDataTest {

    @Container
    protected static RedisContainer redisContainer =
            new RedisContainer(DockerImageName.parse("redis:7.0.12"))
                    .withReuse(true)
                    .withExposedPorts(6379);

    @DynamicPropertySource
    public static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("app.redis.enable", () -> "true");
        registry.add("app.cache.type", () -> "redis");
        registry.add("app.cache.cache-names", () -> List.of("bookByCategoryName", "booksByAuthorAndTitle"));
        registry.add("app.cache.caches.bookByCategoryName.expire", () -> "10m");
        registry.add("app.cache.caches.booksByAuthorAndTitle.expire", () -> "10m");

        registry.add("spring.data.redis.host", redisContainer::getHost);
        registry.add("spring.data.redis.port", () -> redisContainer.getMappedPort(6379).toString());
    }

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;

    @Test
    void whenFindByFilter_ThenReturnFilteredData() throws Exception {
        String author = "test_author";
        String title = "test_title";

        getFacade().save(
                aBookEntity()
                        .withTitle(title)
                        .withAuthor(author)
                        .withCategory(getFacade().persistedOnce(aCategoryEntity())));

        WebBookFilter filter = new WebBookFilter();
        filter.setTitle(title);
        filter.setAuthor(author);

        String actual = mvc.perform(get("/api/v1/book")
                        .param("author", filter.getAuthor())
                        .param("title", filter.getTitle()))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/responses/v1/find_by_filter_1_response.json");

        JsonAssert.assertJsonEquals(expected, actual, JsonAssert.whenIgnoringPaths("books[0].id", "books[0].categoryId"));
    }

    @Test
    void whenFindByCategory_thenReturnBooksByCategory() throws Exception {
        String testName = "test_category";

        TestDataBuilder<CategoryEntity> cBuilder = getFacade().persistedOnce(aCategoryEntity().withName(testName));

        getFacade().save(
                aBookEntity()
                        .withCategory(cBuilder)
                        .withTitle("test_title_1")
                        .withAuthor("test_author_1")
        );

        getFacade().save(
                aBookEntity()
                        .withAuthor("test_author_2")
                        .withTitle("test_title_2")
                        .withCategory(cBuilder)
        );

        String actual = mvc.perform(get("/api/v1/book" + "/{name}", testName))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/responses/v1/find_by_category_response.json");

        JsonAssert.assertJsonEquals(expected, actual, JsonAssert.whenIgnoringPaths("books[0].id", "books[0].categoryId", "books[1].id", "books[1].categoryId"));
    }

    @Test
    void createNewBook_thenReturnNewBook() throws Exception {
        CategoryEntity category = getFacade().save(aCategoryEntity());

        UpsertBookRequest request = UpsertBookRequestBuilder
                .aUpsertBookRequest().withCategoryId(category.getId()).build();

        String actual = mvc.perform(
                        post("/api/v1/book")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/responses/v1/create_new_book_response.json");

        JsonAssert.assertJsonEquals(expected, actual, JsonAssert.whenIgnoringPaths("id", "categoryId"));
    }

    @Test
    void whenUpdateBook_thenReturnUpdatedBook() throws Exception {
        TestDataBuilder<CategoryEntity> cBuilder = getFacade().persistedOnce(aCategoryEntity());
        BookEntity book2update = getFacade().save(aBookEntity().withCategory(cBuilder));

        int id = book2update.getId();

        String updateAuthor = "test_author_2";
        String updateTitle = "test_title_2";
        UpsertBookRequest request = UpsertBookRequestBuilder
                .aUpsertBookRequest()
                .withAuthor(updateAuthor)
                .withTitle(updateTitle)
                .withCategoryId(cBuilder.build().getId())
                .build();

        String actual = mvc.perform(
                        put("/api/v1/book" + "/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/responses/v1/update_book_response.json");

        JsonAssert.assertJsonEquals(expected, actual, JsonAssert.whenIgnoringPaths("id", "categoryId"));
    }
}
