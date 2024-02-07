package by.sapra.libraryservice.testUtils.builders.models;

import by.sapra.libraryservice.models.CategoryEntity;
import by.sapra.libraryservice.testUtils.builders.TestDataBuilder;

import java.util.ArrayList;

public class CategoryEntityTestDataBuilder implements TestDataBuilder<CategoryEntity> {
    private String name = "test_category";

    private CategoryEntityTestDataBuilder() {}

    private CategoryEntityTestDataBuilder(String name) {
        this.name = name;
    }

    public static CategoryEntityTestDataBuilder aCategoryEntity() {
        return new CategoryEntityTestDataBuilder();
    }

    public CategoryEntityTestDataBuilder withName(String name) {
        return this.name == name ? this : new CategoryEntityTestDataBuilder(name);
    }

    @Override
    public CategoryEntity build() {
        CategoryEntity result = new CategoryEntity();
        result.setName(name);
        result.setBooks(new ArrayList<>());
        return result;
    }
}
