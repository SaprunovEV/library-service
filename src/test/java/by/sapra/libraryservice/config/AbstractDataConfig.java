package by.sapra.libraryservice.config;

import by.sapra.libraryservice.testUtils.TestDbFacade;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class AbstractDataConfig {
    @Bean
    public TestDbFacade facade() {
        return new TestDbFacade();
    }
}
