package by.sapra.libraryservice.config;

import by.sapra.libraryservice.testUtils.TestDbFacade;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.function.Supplier;

@ContextConfiguration(classes = AbstractDataConfig.class)
public class AbstractDataTest {
    @Autowired
    private TestDbFacade facade;

    public TestDbFacade getFacade() {
        return facade;
    }

    protected static PostgreSQLContainer<?> postgreSQLContainer;

    static {
        DockerImageName postgres = DockerImageName.parse("postgres:14.5");
        postgreSQLContainer = new PostgreSQLContainer<>(postgres);

        postgreSQLContainer.withReuse(true);
        postgreSQLContainer.withDatabaseName("library");
        postgreSQLContainer.withInitScript("static/schema.sql");
    }

    @DynamicPropertySource
    public static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.jpa.generate-ddl", () -> true);
        registry.add("spring.datasource.driver-class-name",() -> postgreSQLContainer.getDriverClassName());
    }

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }
}
