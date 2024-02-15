package by.sapra.libraryservice.web.v1.annotation;

import by.sapra.libraryservice.web.v1.models.BookListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Operation(
        summary = "Filter books by author and title",
        description = "Filter books by author and title. If author and title are empty return all books.",
        tags = {"book", "V1"}
)
@ApiResponse(
        responseCode = "200",
        content = @Content(schema = @Schema(implementation = BookListResponse.class), mediaType = "application/json")
)
public @interface FilterBooksDock {
}
