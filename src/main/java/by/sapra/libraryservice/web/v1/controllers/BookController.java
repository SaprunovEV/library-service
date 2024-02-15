package by.sapra.libraryservice.web.v1.controllers;

import by.sapra.libraryservice.services.BookService;
import by.sapra.libraryservice.web.v1.annotation.*;
import by.sapra.libraryservice.web.v1.mappers.BookResponseMapper;
import by.sapra.libraryservice.web.v1.mappers.WebFilterMapper;
import by.sapra.libraryservice.web.v1.models.CategoryName;
import by.sapra.libraryservice.web.v1.models.BookId;
import by.sapra.libraryservice.web.v1.models.UpsertBookRequest;
import by.sapra.libraryservice.web.v1.models.WebBookFilter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
@Tag(name = "Book V1", description = "Books API version V1")
public class BookController {
    private final BookService service;
    private final WebFilterMapper filterMapper;
    private final BookResponseMapper responseMapper;
    @GetMapping
    @FilterBooksDock
    public ResponseEntity<?> getBookByAuthorAndTitle(WebBookFilter filter) {
        return ResponseEntity.ok(
                responseMapper.bookModelListToListResponse(
                        service.filterBook(
                                filterMapper.webFilterToServiceFilter(filter)))
        );
    }

    @GetMapping("/{categoryName}")
    @FindByCategoryNameDock
    public ResponseEntity<?> getBookByCategoryName(@PathVariable CategoryName categoryName) {
        return ResponseEntity.ok(
                responseMapper
                        .bookModelListToListResponse(
                                service.getBookByCategory(categoryName.getCategoryName()))
        );
    }

    @PostMapping
    @CreateNewBookDock
    public ResponseEntity<?> createNewBook(@RequestBody @Valid UpsertBookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                responseMapper.bookModelToResponse(
                        service.createBook(
                                responseMapper.requestToBookModel(request)
                        )
                )
        );
    }

    @PutMapping("/{id}")
    @UpdateBookDock
    public ResponseEntity<?> updateBook(@Valid BookId id, @RequestBody @Valid UpsertBookRequest request) {
        return ResponseEntity.ok(
                responseMapper.bookModelToResponse(
                        service.updateBook(id.getId(), responseMapper.requestToBookModel(request))));
    }

    @DeleteMapping("/{id}")
    @DeleteBookDock
    public ResponseEntity<Void> deleteBook(@Valid BookId id) {
        service.deleteBook(id.getId());
        return ResponseEntity.noContent().build();
    }
}
