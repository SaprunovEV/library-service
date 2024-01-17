package by.sapra.libraryservice.testUtils.builders.web.v1;

import by.sapra.libraryservice.testUtils.builders.TestDataBuilder;
import by.sapra.libraryservice.web.v1.models.BookResponse;

public class BookResponseBuilder implements TestDataBuilder<BookResponse> {
    private String author = "test_author";
    private String title = "test_title";
    private String category = "test_category";

    private BookResponseBuilder(String author, String title, String category) {
        this.author = author;
        this.title = title;
        this.category = category;
    }

    private BookResponseBuilder() {}

    public static BookResponseBuilder aBookResponse() {
        return new BookResponseBuilder();
    }

    public BookResponseBuilder withAuthor(String author) {
        return this.author == author ? this : new BookResponseBuilder(author, title, category);
    }

    public BookResponseBuilder withTitle(String title) {
        return this.title == title ? this : new BookResponseBuilder(author, title, category);
    }

    public BookResponseBuilder withCategory(String category) {
        return this.category == category ? this : new BookResponseBuilder(author, title, category);
    }

    @Override
    public BookResponse build() {
        BookResponse result = new BookResponse();
        result.setAuthor(author);
        result.setTitle(title);
        result.setCategory(category);
        return result;
    }
}
