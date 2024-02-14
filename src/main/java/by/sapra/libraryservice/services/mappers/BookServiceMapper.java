package by.sapra.libraryservice.services.mappers;

import by.sapra.libraryservice.models.BookEntity;
import by.sapra.libraryservice.services.model.BookModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE, uses = {CategoryServiceMapper.class})
public interface BookServiceMapper {
    @Mapping(source = "categoryEntity.id", target = "categoryId")
    BookModel entityToModel(BookEntity entity);

    List<BookModel> entityListToBookModelList(List<BookEntity> books);

    @Mapping(source = "categoryId", target = "categoryEntity")
    BookEntity modelToEntity(BookModel book2author);
}
