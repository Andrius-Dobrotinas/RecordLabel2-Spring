package com.andrewd.recordlabel.authentication;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/api/authentication/authenticate",
                "/api/**/post", "/api/**/post/**", "/api/**/delete/**")
                .fullyAuthenticated().and()
                .httpBasic().and()
                .csrf().disable();
    }
}