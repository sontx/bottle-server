package com.blogspot.sontx.bottle.server.config;

import com.blogspot.sontx.bottle.server.auth.AuthenticationTokenProcessingFilter;
import com.blogspot.sontx.bottle.server.auth.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final AuthenticationTokenProcessingFilter authenticationTokenProcessingFilter;

    @Autowired
    public SecurityConfig(RestAuthenticationEntryPoint authenticationEntryPoint,
                          AuthenticationTokenProcessingFilter authenticationTokenProcessingFilter) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationTokenProcessingFilter = authenticationTokenProcessingFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/rest/auth").permitAll()
                .antMatchers("/rest/**")
                .fullyAuthenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(authenticationTokenProcessingFilter, AnonymousAuthenticationFilter.class);
    }

}
