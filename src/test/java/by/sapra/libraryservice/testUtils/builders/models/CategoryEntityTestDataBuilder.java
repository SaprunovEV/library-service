package by.sapra.libraryservice.testUtils.builders.models;

import by.sapra.libraryservice.models.BookEntity;
import by.sapra.libraryservice.models.CategoryEntity;
import by.sapra.libraryservice.testUtils.builders.TestDataBuilder;

import java.util.ArrayList;
import java.util.List;

public class CategoryEntityTestDataBuilder implements TestDataBuilder<CategoryEntity> {
    private String name = "test_category";
    private List<TestDataBuilder<BookEntity>> books = new ArrayList<>();

    private CategoryEntityTestDataBuilder() {}

    private CategoryEntityTestDataBuilder(String name, List<TestDataBuilder<BookEntity>> books) {
        this.books = books;
    }

    public static CategoryEntityTestDataBuilder aCategoryEntity() {
        return new CategoryEntityTestDataBuilder();
    }

    public CategoryEntityTestDataBuilder withBooks(List<TestDataBuilder<BookEntity>> books) {
        return this.books == books ? this : new CategoryEntityTestDataBuilder(name, books);
    }

    public CategoryEntityTestDataBuilder withName(String name) {
        return this.name == name ? this : new CategoryEntityTestDataBuilder(name, books);
    }

    @Override
    public CategoryEntity build() {
        CategoryEntity result = new CategoryEntity();
        result.setName(name);
        result.setBooks(books.stream().map(TestDataBuilder::build).peek(bookEntity -> bookEntity.setCategoryEntity(result)).toList());
        return result;
    }
}
