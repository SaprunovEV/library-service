package by.sapra.libraryservice.services;

import by.sapra.libraryservice.services.model.BookModel;
import by.sapra.libraryservice.services.model.ServiceFilter;

public interface BookService {
    BookModel filterBook(ServiceFilter filter);
}
