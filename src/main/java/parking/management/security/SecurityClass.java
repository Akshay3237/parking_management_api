package parking.management.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityClass{

    // add support for JDBC ... no more hardcoded users :-)

	@Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        // define query to retrieve a user by username
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select user_id, password, active from user where user_name=?");

        // define query to retrieve the authorities/roles by username
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select user_id, role from roles where user_id=?");

        return jdbcUserDetailsManager;
    }
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                configurer
                		.requestMatchers("/roles").permitAll()
                        .requestMatchers(HttpMethod.GET, "/admin/users").hasRole("ADMIN")
                        
                        .requestMatchers(HttpMethod.GET, "/admin/parkingzones").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/admin/parkingmanagers").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/admin/clients").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/admin/clients/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/admin/vehicles").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/admin/vehicles/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/admin/profit").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/admin/profit/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/admin/histories").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/admin/histories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/admin/transactions").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/admin/transactions/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/admin/createmanager").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/admin/createparkingzone").hasRole("ADMIN")
                        
                       
                        .requestMatchers(HttpMethod.GET,"/parkingmanager/users").hasRole("PARKINGMANAGER")
                        .requestMatchers(HttpMethod.GET,"/parkingmanager/availablespaces").hasRole("PARKINGMANAGER")
                        .requestMatchers(HttpMethod.GET,"/parkingmanager/parkingspaces").hasRole("PARKINGMANAGER")
                        .requestMatchers(HttpMethod.GET,"/parkingmanager/transactions").hasRole("PARKINGMANAGER")
                        .requestMatchers(HttpMethod.GET,"/parkingmanager/transactions/**").hasRole("PARKINGMANAGER")
                        .requestMatchers(HttpMethod.GET,"/parkingmanager/vehicles").hasRole("PARKINGMANAGER")
                        .requestMatchers(HttpMethod.GET,"/parkingmanager/profit").hasRole("PARKINGMANAGER")
                        .requestMatchers(HttpMethod.GET,"/parkingmanager/histories").hasRole("PARKINGMANAGER")
                        .requestMatchers(HttpMethod.POST,"/parkingmanager/users").hasRole("PARKINGMANAGER")
                        .requestMatchers(HttpMethod.POST,"/parkingmanager/parkingspaces").hasRole("PARKINGMANAGER")
                        .requestMatchers(HttpMethod.DELETE,"/parkingmanager/parkingspaces/**").hasRole("PARKINGMANAGER")
                        .requestMatchers(HttpMethod.PUT,"/parkingmanager/transactions/**").hasRole("PARKINGMANAGER")
                        
                        
                        .requestMatchers(HttpMethod.GET,"/client/transactions/**").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.GET,"/client/parkingzones/**").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.GET,"/client/parkingzones").hasRole("CLIENT")
        );

        // use HTTP Basic authentication
        http.httpBasic(Customizer.withDefaults());

        // disable Cross Site Request Forgery (CSRF)
        // in general, not required for stateless REST APIs that use POST, PUT, DELETE and/or PATCH
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}













