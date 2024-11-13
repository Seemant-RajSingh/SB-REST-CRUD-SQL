package com.srs.springAuth.filter;

import com.srs.springAuth.service.JwtService;
import com.srs.springAuth.service.UserDetailsServiceImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImp userDetailsService;


    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsServiceImp userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }

        System.out.println("in filter 1 - start");

        // authHeader is there and starts with "Bearer "
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        System.out.println("in filter 2");

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("in filter 3");

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            System.out.println("in filter 4");

            if(jwtService.isValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                System.out.println("in filter 5");

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                System.out.println("authToken: " + authToken);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        System.out.println("ending");
        filterChain.doFilter(request, response);


    }
}
