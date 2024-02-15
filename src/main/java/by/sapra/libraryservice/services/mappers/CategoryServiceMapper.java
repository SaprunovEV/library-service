package by.sapra.libraryservice.services.mappers;

import by.sapra.libraryservice.models.CategoryEntity;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;
@DecoratedWith(CategoryServiceMapperDecorator.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface CategoryServiceMapper {
    CategoryEntity categoryIdToCategoryEntity(Integer categoryId);
}
