package by.sapra.libraryservice.web.v1.controllers;

import by.sapra.libraryservice.services.BookService;
import by.sapra.libraryservice.web.v1.mappers.BookResponseMapper;
import by.sapra.libraryservice.web.v1.mappers.WebFilterMapper;
import by.sapra.libraryservice.web.v1.models.CategoryName;
import by.sapra.libraryservice.web.v1.models.IdToUpdate;
import by.sapra.libraryservice.web.v1.models.UpsertBookRequest;
import by.sapra.libraryservice.web.v1.models.WebBookFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService service;
    private final WebFilterMapper filterMapper;
    private final BookResponseMapper responseMapper;
    @GetMapping
    public ResponseEntity<?> getBookByAuthorAndTitle(WebBookFilter filter) {
        return ResponseEntity.ok(
                responseMapper.bookModelToResponse(
                        service.filterBook(
                                filterMapper.webFilterToServiceFilter(filter)))
        );
    }

    @GetMapping("/{categoryName}")
    public ResponseEntity<?> getBookByCategoryName(@PathVariable CategoryName categoryName) {
        return ResponseEntity.ok(
                responseMapper
                        .bookModelListToListResponse(
                                service.getBookByCategory(categoryName.getCategoryName()))
        );
    }

    @PostMapping
    public ResponseEntity<?> createNewBook(@RequestBody UpsertBookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                responseMapper.bookModelToResponse(
                        service.createBook(
                                responseMapper.requestToBookModel(request)
                        )
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(IdToUpdate id, @RequestBody UpsertBookRequest request) {
        return ResponseEntity.ok(
                responseMapper.bookModelToResponse(
                        service.updateBook(id.getId(), responseMapper.requestToBookModel(request))));
    }
}
