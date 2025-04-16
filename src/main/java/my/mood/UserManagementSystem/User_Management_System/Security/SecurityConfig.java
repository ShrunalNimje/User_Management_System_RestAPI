package my.mood.UserManagementSystem.User_Management_System.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	CustomUserDetailsService userDetailsService;
	
	@Autowired
	JWTFilter jwtFilter;
	
	public SecurityConfig(CustomUserDetailsService userDetailsService, JWTFilter jwtFilter) {
		this.userDetailsService = userDetailsService;
		this.jwtFilter = jwtFilter;
	}
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Disable CSRF (only if using stateless authentication like JWT)
        http.csrf(csrf -> csrf.disable());

        // Define role-based access control
        http.authorizeHttpRequests(auth -> auth
        		.requestMatchers("/register/").permitAll()
        		.requestMatchers(
        			    "/swagger-ui.html",
        			    "/swagger-ui/**",
        			    "/v3/api-docs/**"
        			).permitAll()
        		.requestMatchers("/login/").permitAll()
	            .requestMatchers("/admin/**").hasRole("ADMIN")
	            .requestMatchers("/user/**").hasAnyRole("USER")
	            .anyRequest().authenticated()
        );

        http.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        http.userDetailsService(userDetailsService);
        
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); 
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
