package by.sapra.libraryservice.web.v1.controllers;

import by.sapra.libraryservice.services.BookService;
import by.sapra.libraryservice.web.v1.mappers.BookResponseMapper;
import by.sapra.libraryservice.web.v1.mappers.WebFilterMapper;
import by.sapra.libraryservice.web.v1.models.WebBookFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
