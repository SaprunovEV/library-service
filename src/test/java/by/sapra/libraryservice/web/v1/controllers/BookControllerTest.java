package by.sapra.libraryservice.web.v1.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = BookController.class)
class BookControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void shouldDoSomething() throws Exception {

    }
}