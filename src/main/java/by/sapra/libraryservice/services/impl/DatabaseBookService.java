package by.sapra.libraryservice.services.impl;

import by.sapra.libraryservice.models.BookEntity;
import by.sapra.libraryservice.repository.BookRepository;
import by.sapra.libraryservice.repository.specifications.BookSpecification;
import by.sapra.libraryservice.services.BookService;
import by.sapra.libraryservice.services.exceptions.BookNotFoundException;
import by.sapra.libraryservice.services.mappers.BookServiceMapper;
import by.sapra.libraryservice.services.model.BookModel;
import by.sapra.libraryservice.services.model.ServiceFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
        BookEntity savedEntity = repository.save(mapper.modelToEntity(book2save));
        return mapper.entityToModel(savedEntity);
    }

    @Override
    public BookModel updateBook(Integer id, BookModel book2update) {
        Optional<BookEntity> bookOptional = repository.findById(id);
        if (bookOptional.isEmpty()) {
            throw new BookNotFoundException();
        }

        BookEntity entity2update = mapper.modelToEntity(book2update);

        BookEntity entity2save = bookOptional.get();

        if (!entity2save.getAuthor().equals(entity2update.getAuthor())) entity2save.setAuthor(entity2update.getAuthor());
        if (!entity2save.getTitle().equals(entity2update.getTitle())) entity2save.setTitle(entity2update.getTitle());

        if (!Objects.equals(entity2save.getCategoryEntity().getId(), entity2update.getCategoryEntity().getId()))
            entity2save.setCategoryEntity(entity2update.getCategoryEntity());


        return mapper.entityToModel(repository.save(entity2save));
    }

    @Override
    public void deleteBook(Integer id) {

    }
}
