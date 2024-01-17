package by.sapra.libraryservice.web.v1.controllers;

import by.sapra.libraryservice.web.v1.models.WebBookFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {
    @GetMapping
    public void getBookByAuthorAndTitle(WebBookFilter filter) {
        System.out.println();
    }
}
