package by.sapra.libraryservice.testUtils;

import by.sapra.libraryservice.testUtils.builders.TestDataBuilder;
import by.sapra.libraryservice.web.v1.models.UpsertBookRequest;

public class UpsertBookRequestBuilder implements TestDataBuilder<UpsertBookRequest>{
    private String title = "test_title";
    private String author = "test_author";
    private Integer categoryId = 1;

    private UpsertBookRequestBuilder() {}

    private UpsertBookRequestBuilder(String title, String author, Integer categoryId) {
        this.title = title;
        this.author = author;
        this.categoryId = categoryId;
    }

    public static UpsertBookRequestBuilder aUpsertBookRequest() {
        return new UpsertBookRequestBuilder();
    }

    public UpsertBookRequestBuilder withTitle(String title) {
        return this.title == title ? this : new UpsertBookRequestBuilder(title, author, categoryId);
    }

    public UpsertBookRequestBuilder withAuthor(String author) {
        return this.author == author ? this : new UpsertBookRequestBuilder(title, author, categoryId);
    }

    public UpsertBookRequestBuilder withCategoryId(Integer categoryId) {
        return this.categoryId == categoryId ? this : new UpsertBookRequestBuilder(title, author, categoryId);
    }

    @Override
    public UpsertBookRequest build() {
        UpsertBookRequest result = new UpsertBookRequest();
        result.setTitle(title);
        result.setAuthor(author);
        result.setCategoryId(categoryId);
        return result;
    }
}
