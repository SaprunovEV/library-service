package by.sapra.libraryservice.web.v1.controllers;

import by.sapra.libraryservice.models.CategoryEntity;
import by.sapra.libraryservice.repository.CategoryRepository;
import by.sapra.libraryservice.web.v1.models.UploadCategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryRepository repository;

    @PostMapping
    public Integer saveCategory(@RequestBody UploadCategoryRequest request) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(request.getName());

        return repository.save(categoryEntity).getId();
    }
}
