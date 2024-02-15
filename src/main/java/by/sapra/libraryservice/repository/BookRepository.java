package by.sapra.libraryservice.repository;

import by.sapra.libraryservice.models.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Integer>, JpaSpecificationExecutor<BookEntity> {
    List<BookEntity> findByCategoryEntity_Name(String name);
}
