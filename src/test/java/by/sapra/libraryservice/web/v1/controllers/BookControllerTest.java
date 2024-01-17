package by.sapra.libraryservice.web.v1.controllers;

import by.sapra.libraryservice.services.BookService;
import by.sapra.libraryservice.services.model.BookModel;
import by.sapra.libraryservice.services.model.ServiceFilter;
import by.sapra.libraryservice.testUtils.builders.service.BookModelBuilder;
import by.sapra.libraryservice.testUtils.builders.web.v1.BookResponseBuilder;
import by.sapra.libraryservice.web.v1.mappers.BookResponseMapper;
import by.sapra.libraryservice.web.v1.mappers.WebFilterMapper;
import by.sapra.libraryservice.web.v1.models.BookResponse;
import by.sapra.libraryservice.web.v1.models.WebBookFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class)
@ActiveProfiles("web_test")
class BookControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private WebFilterMapper webMapper;

    @MockBean
    private BookService service;

    @MockBean
    private BookResponseMapper responseMapper;

    @Value("${app.base-url}")
    private String baseUrl;

    @Test
    void whenFindByFilter_thenReturnResponse() throws Exception {
        WebBookFilter filter = new WebBookFilter();
        filter.setAuthor("test_title");
        filter.setTitle("test_author");

        ServiceFilter serviceFilter = new ServiceFilter();
        serviceFilter.setAuthor(filter.getAuthor());
        serviceFilter.setTitle(filter.getTitle());
        when(webMapper.webFilterToServiceFilter(filter)).thenReturn(serviceFilter);

        BookModel book = createBookModel(filter);
        when(service.filterBook(serviceFilter)).thenReturn(book);


        BookResponse response = createBookResponse(filter);
        when(responseMapper.bookModelToResponse(book)).thenReturn(response);

        mvc.perform(
                        get(baseUrl)
                                .param("title", filter.getTitle())
                                .param("author", filter.getAuthor()))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        verify(webMapper, times(1)).webFilterToServiceFilter(filter);
        verify(service, times(1)).filterBook(serviceFilter);
        verify(responseMapper, times(1)).bookModelToResponse(book);
    }

    private static BookResponse createBookResponse(WebBookFilter filter) {
        return BookResponseBuilder.aBookResponse()
                .withAuthor(filter.getAuthor())
                .withTitle(filter.getTitle())
                .build();
    }

    private static BookModel createBookModel(WebBookFilter filter) {
        return BookModelBuilder.aBook()
                .withAuthor(filter.getAuthor())
                .withTitle(filter.getTitle())
                .build();
    }
}