package by.sapra.libraryservice.web.v1.mappers;

import by.sapra.libraryservice.services.model.BookModel;
import by.sapra.libraryservice.web.v1.models.BookResponse;

public interface BookResponseMapper {
    BookResponse bookModelToResponse(BookModel bookModel);
}
