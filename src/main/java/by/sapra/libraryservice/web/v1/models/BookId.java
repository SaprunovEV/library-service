package by.sapra.libraryservice.web.v1.models;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookId {
    @Positive(message = "Id книги должен быть положительным!")
    private Integer id;
}
