package by.sapra.libraryservice.repository;

import by.sapra.libraryservice.models.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<BookEntity, Integer>, JpaSpecificationExecutor<BookEntity> {
}
