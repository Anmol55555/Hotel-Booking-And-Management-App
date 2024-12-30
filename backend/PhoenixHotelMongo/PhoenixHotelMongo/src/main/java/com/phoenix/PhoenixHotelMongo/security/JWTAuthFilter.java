package com.phoenix.PhoenixHotelMongo.security;

import com.phoenix.PhoenixHotelMongo.service.CustomUserDetailService;
import com.phoenix.PhoenixHotelMongo.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/*
Request Lifecycle Summary
    Request Received:       Comes to Spring Security Filter Chain.
    JWTAuthFilter:          Validates the token and sets user details in the SecurityContextHolder and pass to other security filters.
    Authorization Filters:  Check permissions based on the user's roles/authorities.
    Controller:             Handles the request if authentication and authorization succeed.
    Response Sent:          Returned to the client.
*/

@Component
// Overall will be the 1st to intercept any request coming to backend
// It will authenticate the user token and then only will pass onto the next chain of authentication provided by default spring method authentication
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    // This is the first class which is going to intercept for any request to backend
    // This will be at the starting, and will extract the token, validates it and set the authenticated user details into SecurityContextHolder
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // The "Authorization" header contains the jwt token in form of string = "Bearer<jwt_token>"
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;

        // If there is no authHeader containing jwt Token, the JSTAuth will not block the request and pass the request to other security filter
        // These request can go to all the public routes even without a token, as mentioned in the SecurityConfig.java .permitAll()
        if (authHeader == null || authHeader.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }
        jwtToken = authHeader.substring(7);     // because the first 6 character of authHeader will be "BEARER" then the jwt token will start in the string
        userEmail = jwtUtils.extractUsername(jwtToken);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = customUserDetailService.loadUserByUsername(userEmail);

            if(jwtUtils.isValidToken(jwtToken, userDetails)){
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }

        // passing onto other security filter
        filterChain.doFilter(request, response);
    }
}
