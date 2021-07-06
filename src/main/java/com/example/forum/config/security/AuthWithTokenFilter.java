package com.example.forum.config.security;

import com.example.forum.model.User;
import com.example.forum.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthWithTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public AuthWithTokenFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(httpServletRequest);

        boolean validToken = tokenService.isValidToken(token);

        if (validToken) {
            authenticateClient(token);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void authenticateClient(String token) {
        Long userId = tokenService.getUserId(token);

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.get(), null, user.get().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private String getToken(HttpServletRequest httpServletRequest) {
        final String tokenType = "Bearer ";

        String token = httpServletRequest.getHeader("Authorization");

        if (token == null || token.isEmpty() || ! token.startsWith(tokenType)) {
            return null;
        }

        return token.replace(tokenType, "");
    }
}
