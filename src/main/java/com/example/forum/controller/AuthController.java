package com.example.forum.controller;

import com.example.forum.config.security.TokenService;
import com.example.forum.controller.dto.TokenDto;
import com.example.forum.controller.form.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Profile(value = {"prod", "test"})
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> auth(@RequestBody @Valid LoginForm loginForm) {
        UsernamePasswordAuthenticationToken authObject = loginForm.authObject();

        try {
            Authentication authentication = authenticationManager.authenticate(authObject);
            String token = tokenService.newToken(authentication);

            return ResponseEntity.ok(new TokenDto(token, "Bearer"));
        } catch (AuthenticationException exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
