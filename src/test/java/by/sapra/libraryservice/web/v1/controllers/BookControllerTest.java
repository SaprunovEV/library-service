package by.sapra.libraryservice.web.v1.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = BookController.class)
@ActiveProfiles("web_test")
class BookControllerTest {
    @Autowired
    private MockMvc mvc;

    @Value("${app.base-url}")
    private String baseUrl;

    @Test
    void shouldDoSomething() throws Exception {
        System.out.println();
    }
}