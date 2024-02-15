package by.sapra.libraryservice.testUtils.builders.web.v1;

import by.sapra.libraryservice.testUtils.builders.TestDataBuilder;
import by.sapra.libraryservice.web.v1.models.BookResponse;

public class BookResponseBuilder implements TestDataBuilder<BookResponse> {
    private Integer id = 1;
    private String author = "test_author";
    private String title = "test_title";
    private Integer category = 1;

    private BookResponseBuilder(Integer id, String author, String title, Integer category) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.category = category;
    }

    private BookResponseBuilder() {}

    public static BookResponseBuilder aBookResponse() {
        return new BookResponseBuilder();
    }

    public BookResponseBuilder withAuthor(String author) {
        return this.author == author ? this : new BookResponseBuilder(id, author, title, category);
    }

    public BookResponseBuilder withTitle(String title) {
        return this.title == title ? this : new BookResponseBuilder(id, author, title, category);
    }

    public BookResponseBuilder withCategory(Integer category) {
        return this.category == category ? this : new BookResponseBuilder(id, author, title, category);
    }

    public BookResponseBuilder withId(Integer id) {
        return this.id == id ? this : new BookResponseBuilder(id, author, title, category);
    }

    @Override
    public BookResponse build() {
        BookResponse result = new BookResponse();
        result.setId(id);
        result.setAuthor(author);
        result.setTitle(title);
        result.setCategoryId(category);
        return result;
    }
}
