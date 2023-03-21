// src/main/java/com/matthewcasperson/auth0demo/configuration/AuthSecurityConfig.java

package com.matthewcasperson.auth0demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class AuthSecurityConfig{
    private final LogoutHandler logoutHandler;

    public AuthSecurityConfig(LogoutHandler logoutHandler) {
        this.logoutHandler = logoutHandler;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.ignoringAntMatchers("/h2-console/**"))
            .authorizeRequests(authorize -> authorize
                .antMatchers("/", "/signup", "/out", "/signuppage", "/loginpage", "/h2-console/**", "/*.svg", "/*.js", "/*.css").permitAll()
                .anyRequest().authenticated())
            .headers(headers -> headers.frameOptions().sameOrigin())
            .logout()
                .deleteCookies("JSESSIONID", "isIn", "isSignup", "signup_data", "existance")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .addLogoutHandler(logoutHandler)
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutSuccessUrl("/out")
                .permitAll()
            .and()
            .oauth2Login()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/home")
                .authorizationEndpoint();
        return http.build();
    }
}