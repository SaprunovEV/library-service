package by.sapra.libraryservice.services.impl;

import by.sapra.libraryservice.models.BookEntity;
import by.sapra.libraryservice.repository.BookRepository;
import by.sapra.libraryservice.repository.specifications.BookSpecification;
import by.sapra.libraryservice.services.BookService;
import by.sapra.libraryservice.services.mappers.BookServiceMapper;
import by.sapra.libraryservice.services.model.BookModel;
import by.sapra.libraryservice.services.model.ServiceFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DatabaseBookService implements BookService {
    private final BookRepository repository;
    private final BookServiceMapper mapper;

    @Override
    public BookModel filterBook(ServiceFilter filter) {
        Optional<BookEntity> optional = repository.findOne(BookSpecification.withFilter(filter));
        if (optional.isEmpty()) {
            return null;
        }

        return mapper.entityToModel(optional.get());
    }

    @Override
    public List<BookModel> getBookByCategory(String categoryName) {
        return mapper.entityListToBookModelList(repository.findByCategoryEntity_Name(categoryName));
    }

    @Override
    public BookModel createBook(BookModel book2save) {
        return null;
    }

    @Override
    public BookModel updateBook(Integer id, BookModel book2update) {
        return null;
    }

    @Override
    public void deleteBook(Integer id) {

    }
}
