package ru.tinkoff.edu.java.bot.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenApi() {

        return new OpenAPI().info(new Info().title("Bot API")
            .contact(new Contact().name("Alexey Kuzin")
                .url("https://github.com/kuzinalex")));
    }

}
