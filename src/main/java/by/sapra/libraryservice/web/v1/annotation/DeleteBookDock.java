package by.sapra.libraryservice.web.v1.annotation;

import by.sapra.libraryservice.web.v1.models.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Operation(
        summary = "Delete order by ID",
        description = "Delete order by Id. Return no content",
        tags = {"order", "id"}
)
@ApiResponses({
        @ApiResponse(
                responseCode = "204",
                content = @Content(schema = @Schema(implementation = Void.class))
        ),
        @ApiResponse(
                responseCode = "404",
                content = @Content(schema = @Schema(
                        implementation = ErrorResponse.class),
                        mediaType = "application/json"
                )
        )
})
public @interface DeleteBookDock {
}
