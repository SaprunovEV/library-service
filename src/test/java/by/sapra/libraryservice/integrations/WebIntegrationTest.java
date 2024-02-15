package by.sapra.libraryservice.integrations;

import by.sapra.libraryservice.config.AbstractDataTest;
import by.sapra.libraryservice.testUtils.StringTestUtils;
import by.sapra.libraryservice.testUtils.UpsertBookRequestBuilder;
import by.sapra.libraryservice.web.v1.models.UpsertBookRequest;
import by.sapra.libraryservice.web.v1.models.WebBookFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

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
public class WebIntegrationTest extends AbstractDataTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;

    @Test
    void whenFindByFilter_ThenReturnFilteredData() throws Exception {
        WebBookFilter filter = new WebBookFilter();
        filter.setTitle("test_title");
        filter.setAuthor("test_author");

        String actual = mvc.perform(get("/api/v1/book")
                        .param("author", filter.getAuthor())
                        .param("title", filter.getTitle()))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/responses/v1/find_by_filter_1_response.json");

        JsonAssert.assertJsonEquals(expected, actual);
    }

    @Test
    void whenFindByCategory_thenReturnBooksByCategory() throws Exception {
        String testName = "test_category";

        String actual = mvc.perform(get("/api/v1/book" + "/{name}", testName))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/responses/v1/find_by_category_response.json");

        JsonAssert.assertJsonEquals(expected, actual);
    }

    @Test
    void createNewBook_thenReturnNewBook() throws Exception {
        UpsertBookRequest request = UpsertBookRequestBuilder
                .aUpsertBookRequest().build();

        String actual = mvc.perform(
                        post("/api/v1/book")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/responses/v1/create_new_book_response.json");

        JsonAssert.assertJsonEquals(expected, actual);
    }

    @Test
    void whenUpdateBook_thenReturnUpdatedBook() throws Exception {
        int id = 1;

        UpsertBookRequest request = UpsertBookRequestBuilder
                .aUpsertBookRequest()
                .withAuthor("test_author_2")
                .withTitle("test_title_2")
                .build();

        String actual = mvc.perform(
                        put("/api/v1/book" + "/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/responses/v1/update_book_response.json");

        JsonAssert.assertJsonEquals(expected, actual);
    }
}
