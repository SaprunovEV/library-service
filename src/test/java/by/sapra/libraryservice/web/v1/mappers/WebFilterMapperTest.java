package by.sapra.libraryservice.web.v1.mappers;

import by.sapra.libraryservice.services.model.ServiceFilter;
import by.sapra.libraryservice.web.v1.models.WebBookFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MappersTestConfig.class)
class WebFilterMapperTest {
    @Autowired
    WebFilterMapper mapper;

    @Test
    void shouldCorrectMapFilter() throws Exception {
        WebBookFilter expected = new WebBookFilter();

        String author = "author";
        String title = "title";

        expected.setAuthor(author);
        expected.setTitle(title);

        ServiceFilter actual = mapper.webFilterToServiceFilter(expected);

        assertAll(() -> {
            assertNotNull(actual);
            assertEquals(expected.getAuthor(), actual.getAuthor());
            assertEquals(expected.getTitle(), actual.getTitle());
        });
    }
}