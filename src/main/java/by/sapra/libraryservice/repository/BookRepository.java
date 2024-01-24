package by.sapra.libraryservice.repository;

import by.sapra.libraryservice.models.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {
}
