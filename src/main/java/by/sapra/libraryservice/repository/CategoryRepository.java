package by.sapra.libraryservice.repository;

import by.sapra.libraryservice.models.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
}
