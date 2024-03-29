package by.sapra.libraryservice.services;

import by.sapra.libraryservice.services.model.BookModel;
import by.sapra.libraryservice.services.model.ServiceFilter;

import java.util.List;

public interface BookService {
    List<BookModel> filterBook(ServiceFilter filter);

    List<BookModel> getBookByCategory(String categoryName);

    BookModel createBook(BookModel book2save);

    BookModel updateBook(Integer id, BookModel book2update);

    void deleteBook(Integer id);
}
