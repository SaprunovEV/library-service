package by.sapra.libraryservice.testUtils.builders.web.v1;

import by.sapra.libraryservice.testUtils.builders.TestDataBuilder;
import by.sapra.libraryservice.web.v1.models.BookListResponse;

import java.util.ArrayList;
import java.util.List;

public class ListBookResponseBuilder implements TestDataBuilder<BookListResponse> {
    private List<BookResponseBuilder> books = new ArrayList<>();

    private ListBookResponseBuilder() {}

    private ListBookResponseBuilder(List<BookResponseBuilder> books) {
        this.books = books;
    }

    public static ListBookResponseBuilder aListBookResponse() {
        return new ListBookResponseBuilder();
    }

    public ListBookResponseBuilder withBooks(List<BookResponseBuilder> books) {
        return this.books == books ? this : new ListBookResponseBuilder(books);
    }


    @Override
    public BookListResponse build() {
        BookListResponse result = new BookListResponse();
        result.setBooks(books.stream().map(TestDataBuilder::build).toList());
        return result;
    }
}
