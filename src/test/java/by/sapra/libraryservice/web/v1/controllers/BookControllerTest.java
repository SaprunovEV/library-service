package by.sapra.libraryservice.web.v1.controllers;

import by.sapra.libraryservice.testUtils.StringTestUtils;
import by.sapra.libraryservice.web.v1.models.WebBookFilter;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

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

    @Test
    void whenFindByFilter_thenReturnResponse(Integer id) throws Exception {
        WebBookFilter filter = new WebBookFilter();
        filter.setAuthor("test_title");
        filter.setTitle("test_author");

        ServiceFilter serviceFilter = new ServiceFilter();
        serviceFilter.setAuthor(filter.getAuthor());
        serviceFilter.setTitle(filter.getTitle());
        when(webMapper.webFilterToServiceFilter(filter)).thenReturn(serviceFilter);

        BookModel book = BookModelBuilder.aBook()
                                .withAuthor(filter.getTitle())
                                .withTitle(filter.getAuthor())
                                .build();
        when(service.filterBook(serviceFilter)).thenReturn(book);


        BookResponse response = BookResponseBuilder.aBookResponse()
                                .withAuthor(filter.getAuthor())
                                .withTitle(filter.getTitle())
                                .build();
        when(responseMapper.bookModelToResponse(book)).thenReturn(response);

        mvc.perform(
                        get(baseUrl)
                                .param("title", filter.getTitle())
                                .param("author", filter.getAuthor()))
                .andReturn().getResponse()
                .getContentAsString();

        verify(webMapper, times(1)).webFilterToServiceFilter(filter);
        verify(service, times(1)).filterBook(serviceFilter);
        verify(responseMapper, times(1)).bookModelToResponse(book);
    }
}