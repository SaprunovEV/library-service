package by.sapra.libraryservice.web.v1.mappers;

import by.sapra.libraryservice.services.model.BookModel;
import by.sapra.libraryservice.web.v1.models.BookListResponse;
import by.sapra.libraryservice.web.v1.models.BookResponse;

import java.util.List;

public interface BookResponseMapper {
    BookResponse bookModelToResponse(BookModel bookModel);

    BookListResponse bookModelListToListResponse(List<BookModel> modelList);
}
