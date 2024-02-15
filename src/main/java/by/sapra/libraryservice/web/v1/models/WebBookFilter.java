package by.sapra.libraryservice.web.v1.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WebBookFilter {
    private String author;
    private String title;
}
