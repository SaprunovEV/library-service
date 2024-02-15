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
        summary = "Find books by category name.",
        description = "Find books by category name. If books not found return empty list.",
        tags = {"book", "V1"}
)
@ApiResponse(
        responseCode = "200",
        content = @Content(schema = @Schema(implementation = BookListResponse.class), mediaType = "application/json")
)
public @interface FindByCategoryNameDock {
}
