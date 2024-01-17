package by.sapra.libraryservice.integrations;

import by.sapra.libraryservice.testUtils.StringTestUtils;
import by.sapra.libraryservice.web.v1.models.WebBookFilter;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WebIntegrationTest {
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
}
