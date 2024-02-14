package by.sapra.libraryservice.services;

import by.sapra.libraryservice.config.AbstractDataTest;
import by.sapra.libraryservice.models.BookEntity;
import by.sapra.libraryservice.models.CategoryEntity;
import by.sapra.libraryservice.services.exceptions.BookNotFoundException;
import by.sapra.libraryservice.services.mappers.BookServiceMapper;
import by.sapra.libraryservice.services.model.BookModel;
import by.sapra.libraryservice.services.model.ServiceFilter;
import by.sapra.libraryservice.testUtils.builders.TestDataBuilder;
import by.sapra.libraryservice.testUtils.builders.models.BookEntityTestDataBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static by.sapra.libraryservice.testUtils.builders.models.BookEntityTestDataBuilder.aBookEntity;
import static by.sapra.libraryservice.testUtils.builders.models.CategoryEntityTestDataBuilder.aCategoryEntity;
import static by.sapra.libraryservice.testUtils.builders.service.BookModelBuilder.aBook;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = BookServiceConf.class)
class BookServiceTest extends AbstractDataTest {
    @Autowired
    private BookService service;

    @MockBean
    private BookServiceMapper mapper;

    @Test
    void whenFilterBook_thenReturnModel() throws Exception {
        TestDataBuilder<CategoryEntity> cB = getFacade().persistedOnce(aCategoryEntity());
        BookEntity entity = getFacade().save(aBookEntity().withCategory(cB));

        ServiceFilter filter = new ServiceFilter();
        filter.setTitle(entity.getTitle());
        filter.setAuthor(entity.getAuthor());

        BookModel model = aBook()
                .withId(entity.getId())
                .withAuthor(entity.getAuthor())
                .withTitle(entity.getTitle())
                .build();
        when(mapper.entityToModel(eq(entity)))
                .thenReturn(model);

        BookModel actual = service.filterBook(filter);

        assertAll(() -> {
            assertNotNull(actual);
            assertEquals(model, actual);
        });

        verify(mapper, times(1)).entityToModel(eq(entity));
    }

    @Test
    void whenFilterBookNotFound_thenReturnModel() throws Exception {
        TestDataBuilder<CategoryEntity> cB = getFacade().persistedOnce(aCategoryEntity());
        BookEntity entity = aBookEntity().withCategory(cB).build();

        ServiceFilter filter = new ServiceFilter();
        filter.setTitle(entity.getTitle());
        filter.setAuthor(entity.getAuthor());

        BookModel actual = service.filterBook(filter);

        assertAll(() -> {
            assertNull(actual);
        });

        verify(mapper, times(0)).entityToModel(eq(entity));
    }

    @Test
    void whenGetBookByCategory_thenReturnCollectionOfBooks() throws Exception {

        TestDataBuilder<CategoryEntity> persistedCategory = getFacade().persistedOnce(aCategoryEntity());
        TestDataBuilder<CategoryEntity> anotherCategory = getFacade().persistedOnce(aCategoryEntity().withName("not"));

        List<BookEntityTestDataBuilder> bookEntityTestDataBuilders = List.of(
                aBookEntity().withTitle("test_1").withCategory(persistedCategory),
                aBookEntity().withTitle("test_2").withCategory(persistedCategory),
                aBookEntity().withTitle("test_3").withCategory(persistedCategory),
                aBookEntity().withTitle("test_4").withCategory(persistedCategory),
                aBookEntity().withTitle("test_0").withCategory(anotherCategory)
        );
        bookEntityTestDataBuilders.forEach(b -> getFacade().save(b));

        CategoryEntity entity = getFacade().find(persistedCategory.build().getId(), CategoryEntity.class);

        List<BookModel> expected = entity.getBooks().stream()
                .map(b -> aBook().withTitle(b.getTitle()).withId(b.getId()).withAuthor(b.getAuthor()).build()).toList();
        when(mapper.entityListToBookModelList(new ArrayList<>(entity.getBooks())))
                .thenReturn(expected);

        List<BookModel> actual = service.getBookByCategory(entity.getName());

        assertAll(() -> {
            assertNotNull(actual, "не ноль");
            assertEquals(entity.getBooks().size(), actual.size(), "одинаковое количество");
            expected.forEach(book -> assertTrue(actual.contains(book)));
        });

        verify(mapper, times(1)).entityListToBookModelList(new ArrayList<>(entity.getBooks()));
    }

    @Test
    void  whenGetBookByCategoryNotFound_thenReturnEmptyCollectionOfBooks() throws Exception {
        ArrayList<BookEntity> books = new ArrayList<>();
        when(mapper.entityListToBookModelList(books))
                .thenReturn(new ArrayList<>());

        List<BookModel> actual = service.getBookByCategory("test");

        assertAll(() -> {
            assertNotNull(actual, "не ноль");
            assertTrue(actual.isEmpty());
        });

        verify(mapper, times(1)).entityListToBookModelList(books);
    }

    @Test
    void whenCreateBook_thenNewBookWillBeSaved() throws Exception {
        TestDataBuilder<CategoryEntity> cBuilder = getFacade().persistedOnce(aCategoryEntity());
        String author = "author";
        String title = "title";
        BookModel book2author = aBook().withAuthor(author).withTitle(title).withCategoryId(cBuilder.build().getId()).build();

        BookEntity entity2save = aBookEntity().withAuthor(author).withTitle(title).withCategory(cBuilder).build();

        when(mapper.modelToEntity(book2author))
                .thenReturn(entity2save);

        BookModel build = aBook().withTitle(title).withAuthor(author).build();
        when(mapper.entityToModel(any())).thenReturn(build);

        BookModel actual = service.createBook(book2author);

        BookEntity expectedEntity = getFacade().find(entity2save.getId(), BookEntity.class);

        assertAll(() -> {
            assertNotNull(actual);
            assertNotNull(expectedEntity);
            assertEquals(expectedEntity.getAuthor(), author);
            assertEquals(expectedEntity.getTitle(), title);
            assertEquals(expectedEntity.getCategoryEntity().getId(), cBuilder.build().getId());
        });

        verify(mapper, times(1)).modelToEntity(book2author);
        verify(mapper, times(1)).entityToModel(expectedEntity);
    }

    @Test
    void whenUpdateNonExistentBook_theThrowException() throws Exception {
        assertThrows(BookNotFoundException.class, () -> {service.updateBook(123, aBook().build());});
    }

    @Test
    void whenUpdateEntity_thenUpdateEntityToDatabase() throws Exception {
        TestDataBuilder<CategoryEntity> cBuilder = getFacade().persistedOnce(aCategoryEntity());
        String author = "author";
        String title = "title";

        BookEntity entity2update = getFacade().save(aBookEntity().withCategory(cBuilder));

        Integer id = entity2update.getId();

        BookModel model2update = aBook().withAuthor(author).withTitle(title).withCategoryId(cBuilder.build().getId()).build();

        BookEntity entityFromMapper = aBookEntity().withCategory(cBuilder).withTitle(title).withAuthor(author).build();
        when(mapper.modelToEntity(model2update))
                .thenReturn(entityFromMapper);

        BookModel build = aBook()
                .withId(id)
                .withTitle(title)
                .withAuthor(author)
                .withCategoryId(
                        cBuilder.build()
                                .getId())
                .build();
        when(mapper.entityToModel(any())).thenReturn(build);

        BookModel actual = service.updateBook(id, model2update);

        BookEntity expectedEntity = getFacade().find(id, BookEntity.class);

        assertAll(() -> {
            assertNotNull(actual);
            assertNotNull(expectedEntity);
            assertEquals(id, actual.getId());
            assertEquals(author, expectedEntity.getAuthor());
            assertEquals(title, expectedEntity.getTitle());
            assertEquals(expectedEntity.getCategoryEntity().getId(), cBuilder.build().getId());
        });

        verify(mapper, times(1)).modelToEntity(model2update);
        verify(mapper, times(1)).entityToModel(expectedEntity);
    }
}