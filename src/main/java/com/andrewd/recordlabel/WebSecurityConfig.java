package com.andrewd.recordlabel;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/**/post", "/api/**/post/**", "/api/**/delete/**",
                        "/api/**/upload/**")
                .fullyAuthenticated().and()
                .csrf().disable();
    }
}