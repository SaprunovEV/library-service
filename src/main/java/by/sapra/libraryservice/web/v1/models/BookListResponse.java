package by.sapra.libraryservice.web.v1.models;

import lombok.Data;

import java.util.List;

@Data
public class BookListResponse {
    List<BookResponse> books;
}
