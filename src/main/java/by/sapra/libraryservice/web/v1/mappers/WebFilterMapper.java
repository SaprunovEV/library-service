package by.sapra.libraryservice.web.v1.mappers;

import by.sapra.libraryservice.services.model.ServiceFilter;
import by.sapra.libraryservice.web.v1.models.WebBookFilter;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface WebFilterMapper {
    ServiceFilter webFilterToServiceFilter(WebBookFilter filter);
}
