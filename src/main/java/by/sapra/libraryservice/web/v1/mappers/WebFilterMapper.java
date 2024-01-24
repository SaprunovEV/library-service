package by.sapra.libraryservice.web.v1.mappers;

import by.sapra.libraryservice.services.model.ServiceFilter;
import by.sapra.libraryservice.web.v1.models.WebBookFilter;

public interface WebFilterMapper {
    ServiceFilter webFilterToServiceFilter(WebBookFilter filter);
}
