package by.sapra.libraryservice.services.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookModel implements Serializable {
    private Integer id;
    private String title;
    private String author;
    private Integer categoryId;
}
