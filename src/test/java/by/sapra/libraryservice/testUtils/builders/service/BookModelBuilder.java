package by.sapra.libraryservice.testUtils.builders.service;

import by.sapra.libraryservice.services.model.BookModel;
import by.sapra.libraryservice.testUtils.builders.TestDataBuilder;

public class BookModelBuilder implements TestDataBuilder<BookModel> {
    private Integer id = 1;
    private String title = "test_title";
    private String author = "test_author";

    private BookModelBuilder() {}

    private BookModelBuilder(Integer id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public static BookModelBuilder aBook() {
        return new BookModelBuilder();
    }

    public BookModelBuilder withAuthor(String author) {
        return this.author == author ? this : new BookModelBuilder(id, title, author);
    }

    public BookModelBuilder withTitle(String title) {
        return this.title == title ? this : new BookModelBuilder(id, author, title);
    }

    public BookModelBuilder withId(Integer id) {
        return this.id == id ? this : new BookModelBuilder(id, author, title);
    }

    @Override
    public BookModel build() {
        BookModel result = new BookModel();
        result.setAuthor(author);
        result.setTitle(title);
        return result;
    }
}
