package by.sapra.libraryservice.services.mappers;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = "by.sapra.libraryservice.services.mappers")
public class CategoryServiceMapperConfig {
    @Bean
    public CategoryServiceMapper mapper() {
        return new CategoryServiceMapperDecorator();
    }
}
