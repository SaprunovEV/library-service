package by.sapra.libraryservice.web.v1.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WebBookFilter {
    @NotBlank(message = "Поле автора не должно быть пустым!")
    private String author;
    @NotBlank(message = "Поле титула не должно быть пустым!")
    private String title;
}
