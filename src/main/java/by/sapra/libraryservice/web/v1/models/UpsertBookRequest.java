package by.sapra.libraryservice.web.v1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertBookRequest {
    private String author;
    private String title;
    private Integer categoryId;
}
