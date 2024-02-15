package by.sapra.libraryservice.services.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
