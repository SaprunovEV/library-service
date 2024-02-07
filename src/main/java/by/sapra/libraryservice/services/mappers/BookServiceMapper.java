package by.sapra.libraryservice.services.mappers;

import by.sapra.libraryservice.models.BookEntity;
import by.sapra.libraryservice.services.model.BookModel;

import java.util.List;

public interface BookServiceMapper {
    BookModel entityToModel(BookEntity entity);

    List<BookModel> entityListToBookModelList(List<BookEntity> books);

    BookEntity modelToEntity(BookModel book2author);
}
