package by.sapra.libraryservice.web.v1.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertBookRequest {
    @NotBlank(message = "Поле автора не должно быть пустым!")
    private String author;
    @NotBlank(message = "Поле титула не должно быть пустым!")
    private String title;
    @Positive(message = "Id категории должно быть положительным!")
    private Integer categoryId;
}
