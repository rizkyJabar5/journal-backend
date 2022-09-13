package com.journal.florist.app.documentation;

import freemarker.template.utility.StringUtil;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    private final SwaggerProperties properties;

    @Bean
    public OpenAPI customOpenApi() {
        final String authenticationType = "Bearer Authentication";
        final String title = String.format("%s API", StringUtil.capitalize(properties.getTitle()));

        SecurityScheme securityScheme = new SecurityScheme()
                .name(authenticationType)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        Components components = new Components()
                .addSecuritySchemes(authenticationType, securityScheme);

        Info info = new Info()
                .title(title)
                .version(properties.getVersion())
                .description(properties.getDescription());

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(authenticationType))
                .components(components)
                .info(info);
    }
}
