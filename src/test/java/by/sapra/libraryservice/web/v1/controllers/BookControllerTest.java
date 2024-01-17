package by.sapra.libraryservice.web.v1.controllers;

import by.sapra.libraryservice.services.BookService;
import by.sapra.libraryservice.services.model.BookModel;
import by.sapra.libraryservice.services.model.ServiceFilter;
import by.sapra.libraryservice.testUtils.UpsertBookRequestBuilder;
import by.sapra.libraryservice.testUtils.builders.service.BookModelBuilder;
import by.sapra.libraryservice.testUtils.builders.web.v1.BookResponseBuilder;
import by.sapra.libraryservice.testUtils.builders.web.v1.ListBookResponseBuilder;
import by.sapra.libraryservice.web.v1.mappers.BookResponseMapper;
import by.sapra.libraryservice.web.v1.mappers.WebFilterMapper;
import by.sapra.libraryservice.web.v1.models.BookListResponse;
import by.sapra.libraryservice.web.v1.models.BookResponse;
import by.sapra.libraryservice.web.v1.models.UpsertBookRequest;
import by.sapra.libraryservice.web.v1.models.WebBookFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class)
@ActiveProfiles("web_test")
class BookControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    void whenFindByCategoryName_thenCallServiceAndMapper() throws Exception {
        String testName = "test_category";

        List<BookModel> modelList = List.of(
                BookModelBuilder.aBook().withId(1).build(),
                BookModelBuilder.aBook().withId(2).build(),
                BookModelBuilder.aBook().withId(3).build(),
                BookModelBuilder.aBook().withId(4).build()
        );
        when(service.getBookByCategory(testName)).thenReturn(modelList);

        BookListResponse listResponse = ListBookResponseBuilder.aListBookResponse().build();
        when(responseMapper.bookModelListToListResponse(modelList))
                .thenReturn(listResponse);

        String actual = mvc.perform(get(baseUrl + "/{name}", testName))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        assertAll(() -> {
            assertNotNull(actual, "не null");
            assertFalse(actual.isEmpty(), "не пусто");
        });

        verify(service, times(1)).getBookByCategory(testName);
        verify(responseMapper, times(1)).bookModelListToListResponse(modelList);
    }

    @Test
    void whenCreateBook_thenReturnCreateStatus() throws Exception {
        UpsertBookRequest request = UpsertBookRequestBuilder
                .aUpsertBookRequest().build();

        mvc.perform(
                post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
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