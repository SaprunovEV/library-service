package by.sapra.libraryservice.testUtils.builders.models;

import by.sapra.libraryservice.models.BookEntity;
import by.sapra.libraryservice.testUtils.builders.TestDataBuilder;

public class BookEntityTestDataBuilder implements TestDataBuilder<BookEntity> {
    private String title = "test_title";
    private String author = "test_author";

    private BookEntityTestDataBuilder() {}

    private BookEntityTestDataBuilder(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public static BookEntityTestDataBuilder aBookEntity() {
        return new BookEntityTestDataBuilder();
    }

    public BookEntityTestDataBuilder withAuthor(String author) {
        return this.author == author ? this : new BookEntityTestDataBuilder(title, author);
    }

    public BookEntityTestDataBuilder withTitle(String title) {
        return this.title == title ? this : new BookEntityTestDataBuilder(author, title);
    }
    
    
    @Override
    public BookEntity build() {
        BookEntity result = new BookEntity();
        result.setAuthor(author);
        result.setTitle(title);
        return result;
    }
}
