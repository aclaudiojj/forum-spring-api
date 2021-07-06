package com.example.forum.config.security;

import com.example.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@Profile(value = {"prod", "test"})
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    // configuração de autorização
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST,"/auth").permitAll()
                .antMatchers(HttpMethod.GET,"/h2-console/**").permitAll()
                .antMatchers(HttpMethod.POST,"/h2-console/**").permitAll()
                .antMatchers(HttpMethod.GET,"/actuator/**").permitAll()
                .antMatchers(HttpMethod.GET,"/topics").permitAll()
                .antMatchers(HttpMethod.GET, "/topics/*").permitAll()
                .antMatchers(HttpMethod.DELETE, "/topics/*").hasRole("MODERATOR")
                .anyRequest().authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new AuthWithTokenFilter(tokenService, userRepository), UsernamePasswordAuthenticationFilter.class);
    }

    // configuração de autenticação (login, por exemplo)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(new BCryptPasswordEncoder());
    }

    // configuração de conteúdo estático (ignorar validações em arquivos js, css, etc)
    @Override
    public void configure(WebSecurity web) throws Exception {

    }
}