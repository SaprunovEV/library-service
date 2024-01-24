package by.sapra.libraryservice.services.mappers;

import by.sapra.libraryservice.models.BookEntity;
import by.sapra.libraryservice.services.model.BookModel;

public interface BookServiceMapper {
    BookModel entityToModel(BookEntity entity);
}
