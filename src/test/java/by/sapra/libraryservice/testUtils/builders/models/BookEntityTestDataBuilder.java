package by.sapra.libraryservice.testUtils.builders.models;

import by.sapra.libraryservice.models.BookEntity;
import by.sapra.libraryservice.models.CategoryEntity;
import by.sapra.libraryservice.testUtils.builders.TestDataBuilder;

public class BookEntityTestDataBuilder implements TestDataBuilder<BookEntity> {
    private String title = "test_title";
    private String author = "test_author";

    private TestDataBuilder<CategoryEntity> category = CategoryEntity::new;

    private BookEntityTestDataBuilder() {}

    private BookEntityTestDataBuilder(String title, String author, TestDataBuilder<CategoryEntity> category) {
        this.title = title;
        this.author = author;
        this.category = category;
    }

    public static BookEntityTestDataBuilder aBookEntity() {
        return new BookEntityTestDataBuilder();
    }

    public BookEntityTestDataBuilder withAuthor(String author) {
        return this.author == author ? this : new BookEntityTestDataBuilder(title, author, category);
    }

    public BookEntityTestDataBuilder withTitle(String title) {
        return this.title == title ? this : new BookEntityTestDataBuilder(author, title, category);
    }

    public BookEntityTestDataBuilder withCategory(TestDataBuilder<CategoryEntity> category) {
        return this.category == category ? this : new BookEntityTestDataBuilder(author, title, category);
    }
    
    
    @Override
    public BookEntity build() {
        BookEntity result = new BookEntity();
        CategoryEntity build = category.build();
        result.setCategoryEntity(build);
        build.getBooks().add(result);
        result.setAuthor(author);
        result.setTitle(title);
        return result;
    }
}
