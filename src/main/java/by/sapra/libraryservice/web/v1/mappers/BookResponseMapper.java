package by.sapra.libraryservice.web.v1.mappers;

import by.sapra.libraryservice.services.model.BookModel;
import by.sapra.libraryservice.web.v1.models.BookListResponse;
import by.sapra.libraryservice.web.v1.models.BookResponse;
import by.sapra.libraryservice.web.v1.models.UpsertBookRequest;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface BookResponseMapper {
    BookResponse bookModelToResponse(BookModel bookModel);

    default BookListResponse bookModelListToListResponse(List<BookModel> modelList) {
        return null;
    }

    BookModel requestToBookModel(UpsertBookRequest request);
}
