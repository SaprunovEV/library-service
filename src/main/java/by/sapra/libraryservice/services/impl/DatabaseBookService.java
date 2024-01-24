package by.sapra.libraryservice.services.impl;

import by.sapra.libraryservice.services.BookService;
import by.sapra.libraryservice.services.model.BookModel;
import by.sapra.libraryservice.services.model.ServiceFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseBookService implements BookService {
    @Override
    public BookModel filterBook(ServiceFilter filter) {
        return null;
    }

    @Override
    public List<BookModel> getBookByCategory(String categoryName) {
        return null;
    }

    @Override
    public BookModel createBook(BookModel book2save) {
        return null;
    }

    @Override
    public BookModel updateBook(Integer id, BookModel book2update) {
        return null;
    }

    @Override
    public void deleteBook(Integer id) {

    }
}
