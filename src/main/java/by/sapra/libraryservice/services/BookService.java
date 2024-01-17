package by.sapra.libraryservice.services;

import by.sapra.libraryservice.services.model.BookModel;
import by.sapra.libraryservice.services.model.ServiceFilter;

import java.util.List;

public interface BookService {
    BookModel filterBook(ServiceFilter filter);

    List<BookModel> getBookByCategory(String categoryName);
}
