package by.sapra.libraryservice.web.v1.annotation;

import by.sapra.libraryservice.web.v1.models.BookResponse;
import by.sapra.libraryservice.web.v1.models.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Operation(
        summary = "Create new book.",
        description = "Create new book. Return created book.",
        tags = {"book", "V1"}
)
@ApiResponse(
        responseCode = "201",
        content = @Content(schema = @Schema(implementation = BookResponse.class))
)
@ApiResponse(
        responseCode = "404",
        description = "Category not found.",
        content = @Content(
                schema = @Schema(implementation = ErrorResponse.class),
                examples = {@ExampleObject(value = "{\n\"message\": \"Категория с Id 1 не найдена!\"\n}")},
                mediaType = "application/json"
        )
)
@ApiResponse(
        responseCode = "404",
        description = "Validation error.",
        content = @Content(
                schema = @Schema(implementation = ErrorResponse.class),
                examples = {@ExampleObject(value = "{\n\"message\": \"Id категории должно быть положительным!\"\n}")},
                mediaType = "application/json"
        )
)
public @interface CreateNewBookDock {
}
