package by.sapra.libraryservice.web.v1.controllers;

import by.sapra.libraryservice.services.BookService;
import by.sapra.libraryservice.services.exceptions.BookNotFoundException;
import by.sapra.libraryservice.services.exceptions.CategoryNotFoundException;
import by.sapra.libraryservice.services.model.BookModel;
import by.sapra.libraryservice.services.model.ServiceFilter;
import by.sapra.libraryservice.testUtils.StringTestUtils;
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
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

        assertNotNullAndNotEmptyResponse(actual);

        verify(service, times(1)).getBookByCategory(testName);
        verify(responseMapper, times(1)).bookModelListToListResponse(modelList);
    }

    @Test
    void whenCreateBook_thenCallServiceAndMapper_andReturnCreatedStatus_andReturnNotNullAndNotEmptyResponse() throws Exception {
        UpsertBookRequest request = UpsertBookRequestBuilder
                .aUpsertBookRequest().build();

        BookModel book2save = BookModelBuilder.aBook().build();
        when(responseMapper.requestToBookModel(request)).thenReturn(book2save);

        BookModel book = BookModelBuilder.aBook().withId(1).build();
        when(service.createBook(book2save)).thenReturn(book);

        BookResponse response = BookResponseBuilder.aBookResponse().build();
        when(responseMapper.bookModelToResponse(book)).thenReturn(response);

        String actual = mvc.perform(
                        post(baseUrl)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        assertNotNullAndNotEmptyResponse(actual);

        verify(responseMapper, times(1)).requestToBookModel(request);
        verify(service, times(1)).createBook(book2save);
        verify(responseMapper, times(1)).bookModelToResponse(book);
    }

    @Test
    void whenUpdateBook_thenCallMapperAndService() throws Exception {
        int id = 1;

        UpsertBookRequest request = UpsertBookRequestBuilder
                .aUpsertBookRequest().build();

        BookModel book2update = BookModelBuilder.aBook().withId(null).build();
        when(responseMapper.requestToBookModel(request)).thenReturn(book2update);

        BookModel book = BookModelBuilder.aBook().withId(id).build();
        when(service.updateBook(id, book2update)).thenReturn(book);

        BookResponse response = BookResponseBuilder.aBookResponse().build();
        when(responseMapper.bookModelToResponse(book)).thenReturn(response);

        String actual = mvc.perform(
                        put(baseUrl + "/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertNotNullAndNotEmptyResponse(actual);

        verify(responseMapper, times(1)).requestToBookModel(request);
        verify(service, times(1)).updateBook(id, book2update);
        verify(responseMapper, times(1)).bookModelToResponse(book);
    }

    @Test
    void whenDeleteBook_thenReturnNoContent_andCallDeleteMethod() throws Exception {
        int id = 1;

        mvc.perform(delete(baseUrl + "/{id}", id))
                .andExpect(status().isNoContent());

        verify(service, times(1)).deleteBook(id);
    }

    @Test
    void whenCategoryNotFound_thenReturnError() throws Exception {
        String testName = "test_category";

        when(service.getBookByCategory(testName)).thenThrow(new CategoryNotFoundException("Тестовое сообщение об ошибке поиска категории!"));

        MockHttpServletResponse response = mvc.perform(get(baseUrl + "/{name}", testName))
                .andExpect(status().isNotFound())
                .andReturn().getResponse();

        setEncoding(response);

        String actual = response.getContentAsString();

        String expected = StringTestUtils.readStringFromResources("responses/v1/category_not_found_error_response.json");

        JsonAssert.assertJsonEquals(expected, actual);
    }

    @Test
    void whenBookNotFound_thenReturnError() throws Exception {
        WebBookFilter filter = new WebBookFilter();
        filter.setAuthor("test_title");
        filter.setTitle("test_author");

        when(webMapper.webFilterToServiceFilter(filter))
                .thenThrow(new BookNotFoundException("Тестовое сообщение о не найденной книге"));

        MockHttpServletResponse response = mvc.perform(
                        get(baseUrl)
                                .param("title", filter.getTitle())
                                .param("author", filter.getAuthor()))
                .andExpect(status().isNotFound())
                .andReturn().getResponse();

        setEncoding(response);

        String actual = response.getContentAsString();

        String expected = StringTestUtils.readStringFromResources("responses/v1/book_not_found_error_repsonse.json");

        JsonAssert.assertJsonEquals(expected, actual);
    }

    @Test
    void whenCreateBookWithNotCorrectCategoryId_thenReturnError() throws Exception {
        UpsertBookRequest request = UpsertBookRequestBuilder
                .aUpsertBookRequest().withCategoryId(-2).build();

        MockHttpServletResponse response = mvc.perform(
                        post(baseUrl)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();

        setEncoding(response);

        String actual = response.getContentAsString();

        String expected = StringTestUtils.readStringFromResources("responses/v1/categoryId_validation_error_response.json");

        JsonAssert.assertJsonEquals(expected, actual);
    }

    private static void setEncoding(MockHttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
    }

    private static void assertNotNullAndNotEmptyResponse(String actual) {
        assertAll(() -> {
            assertNotNull(actual, "не null");
            assertFalse(actual.isEmpty(), "не пусто");
        });
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