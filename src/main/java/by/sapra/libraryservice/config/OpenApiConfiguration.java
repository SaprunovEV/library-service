package by.sapra.libraryservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {
    @Value(value = "${server.port}")
    private Integer port;
    @Value(value = "${server.host}")
    private String host;

    @Bean
    public OpenAPI openApiDescription() {
        Server localhost = new Server();
        localhost.setUrl(host + ":" + port);
        localhost.setDescription("local env");

        Contact contact = new Contact();
        contact.setName("Евгений Сапрунов");
        contact.setEmail("sapra10062018@gmail.com");

        License mitLicense = new License().name("GNU AGPLv3").url("https://choosealicense/licenses/agpl-3.0");

        Info info = new Info()
                .description("API for library.")
                .license(mitLicense)
                .title("API for library service")
                .contact(contact)
                .version("1.0");

        return new OpenAPI()
                .info(info)
                .servers(List.of(localhost));
    }
}
