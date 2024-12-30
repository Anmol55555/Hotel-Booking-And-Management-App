package com.phoenix.PhoenixHotelMongo.security;


import com.phoenix.PhoenixHotelMongo.service.CustomUserDetailService;
import com.phoenix.PhoenixHotelMongo.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JWTAuthFilter jwtAuthFilter;


    @Bean
    // This will add no. of security filters to be added in the security filter chhain once a request is made to backend
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.csrf(AbstractHttpConfigurer::disable)      // csrf is disabled coz our application is stateless coz of jwt
                .cors(Customizer.withDefaults())                // enables cors(cross origin) with default setting since frontend and backend will be hosted in differnet domain
                .authorizeHttpRequests(request-> request
                        .requestMatchers("/auth/**", "/rooms/**", "/bookings/**").permitAll()   // Request to this endpoints does not require jwt authentication and these are public routes and can be accessed by all with or without thoken
                        .anyRequest().authenticated())      // Any other request which does not match the above end points needs authentication and cannot go through without authentication
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// Stateless means the server will not create any HTTP session or manage sessions since we are using JWT (i.e. the server will not store login status of the user)
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);  // Our custom jstAuthFilter will be added 1st to intercept the request before Spring security default username-password authentication
        return httpSecurity.build();        // Build the security filter chain according to above configuration
    }

    @Bean
    // Will authenticate all request other than that given request patterns ("/auth/**", "/rooms/**", "/bookings/**")
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
