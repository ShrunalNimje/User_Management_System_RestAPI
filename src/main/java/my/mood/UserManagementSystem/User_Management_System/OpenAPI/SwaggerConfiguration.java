package my.mood.UserManagementSystem.User_Management_System.OpenAPI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfiguration {

	@Bean
	public OpenAPI swaggerOpenAPI() {
		return new OpenAPI()
				.info(new Info()
					.title("User Management Sytem RestAPI")
					.description("Create, Update, Retrieve and Delete based on thier role")
					.version("1.0"))
				
				.addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
				
				.components(new Components()
						.addSecuritySchemes("Bearer Authentication", 
								new SecurityScheme()
								.type(SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT")));
				
	}
}
