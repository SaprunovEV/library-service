package by.sapra.libraryservice.services.impl;

import by.sapra.libraryservice.config.properties.AppCacheProperties;
import by.sapra.libraryservice.services.BookService;
import by.sapra.libraryservice.services.model.BookModel;
import by.sapra.libraryservice.services.model.ServiceFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@CacheConfig(cacheManager = "redisCacheManager")
@RequiredArgsConstructor
public class CacheBookServiceDecorator implements BookService {
    @NotCachedBookServiceQualifier
    private final BookService delegate;

    @Override
    @Cacheable(value = AppCacheProperties.CacheNames.BOOKS_BY_AUTHOR_AND_TITLE, key = "#filter.author + #filter.title")
    public List<BookModel> filterBook(ServiceFilter filter) {
        return delegate.filterBook(filter);
    }

    @Override
    @Cacheable(AppCacheProperties.CacheNames.BOOKS_BY_CATEGORY_NAME)
    public List<BookModel> getBookByCategory(String categoryName) {
        return delegate.getBookByCategory(categoryName);
    }

    @Override
    public BookModel createBook(BookModel book2save) {
        return delegate.createBook(book2save);
    }

    @Override
    public BookModel updateBook(Integer id, BookModel book2update) {
        return delegate.updateBook(id, book2update);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = AppCacheProperties.CacheNames.BOOKS_BY_AUTHOR_AND_TITLE, allEntries = true),
            @CacheEvict(value = AppCacheProperties.CacheNames.BOOKS_BY_CATEGORY_NAME, allEntries = true)
    })
    public void deleteBook(Integer id) {
        delegate.deleteBook(id);
    }
}
