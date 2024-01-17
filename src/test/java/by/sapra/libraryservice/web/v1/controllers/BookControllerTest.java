package by.sapra.libraryservice.web.v1.controllers;

import by.sapra.libraryservice.web.v1.models.WebBookFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class)
@ActiveProfiles("web_test")
class BookControllerTest {
    @Autowired
    private MockMvc mvc;

    @Value("${app.base-url}")
    private String baseUrl;

    @Test
    void whenFindBookByTitleAndAuthor_thenReturnOk() throws Exception {
        WebBookFilter filter = new WebBookFilter();
        filter.setAuthor("test_title");
        filter.setTitle("test_author");

        mvc.perform(
                get(baseUrl)
                        .param("title", filter.getTitle())
                        .param("author", filter.getAuthor())
                )
                .andExpect(status().isOk());
    }
}