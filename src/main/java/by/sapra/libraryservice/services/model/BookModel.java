package by.sapra.libraryservice.services.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookModel {
    private Integer id;
    private String title;
    private String author;
    private Integer categoryId;
}
