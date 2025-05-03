package br.com.starter.infrastructure.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.starter.domain.user.CustomUserDetailsService;
import io.jsonwebtoken.Claims;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtValidator jwtValidator;
    private final CustomUserDetailsService userDetailsService;
    static final String PREFIX = "Bearer ";


    public JwtAuthenticationFilter(JwtValidator jwtValidator, CustomUserDetailsService userDetailsService) {
        this.jwtValidator = jwtValidator;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain
    ) throws ServletException, IOException {
    
        final String authorizationHeader = request.getHeader("Authorization");
    
        String username = null;
        String jwt = null;
    
        if (authorizationHeader != null && authorizationHeader.startsWith(PREFIX)) {
            jwt = authorizationHeader.substring(7); // Remove "Bearer "
            Claims claims = jwtValidator.validateTokenAndGetClaims(jwt);
            username = claims.getSubject(); 
        }
    
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Claims claims = jwtValidator.validateTokenAndGetClaims(jwt);
            String role = claims.get("ROLE", String.class);
    
            // Configurando as autoridades
            String authority = role.startsWith("ROLE_") ? role : "ROLE_" + role;
    
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(authority));
    
            // Configurando o contexto de autenticação
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    
        chain.doFilter(request, response); // Próximo filtro
    }
    

}
