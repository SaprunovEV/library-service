package by.sapra.libraryservice.services.mappers;

import by.sapra.libraryservice.models.CategoryEntity;
import by.sapra.libraryservice.repository.CategoryRepository;
import by.sapra.libraryservice.services.exceptions.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class CategoryServiceMapperDecorator implements CategoryServiceMapper {
    @Autowired
    private CategoryRepository repository;

    @Override
    public CategoryEntity categoryIdToCategoryEntity(Integer categoryId) {
        Optional<CategoryEntity> optional = repository.findById(categoryId);
        if (optional.isEmpty()) throw new CategoryNotFoundException("Категория с Id: " + categoryId + " не найдена!");
        return optional.get();
    }
}
