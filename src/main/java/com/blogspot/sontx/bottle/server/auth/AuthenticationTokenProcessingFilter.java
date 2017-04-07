package com.blogspot.sontx.bottle.server.auth;

import com.blogspot.sontx.bottle.server.model.bean.AuthData;
import com.blogspot.sontx.bottle.server.model.service.AuthService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j
public class AuthenticationTokenProcessingFilter extends GenericFilterBean {
    private final AuthService authService;

    @Autowired
    public AuthenticationTokenProcessingFilter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String authorizationString = ((HttpServletRequest) request).getHeader("Authorization");

        if (!StringUtils.isEmpty(authorizationString) && authorizationString.startsWith("Bearer ")) {
            String strToken = authorizationString.substring(7);
            AuthData authData = authService.authenticateWithToken(strToken);
            if (authData != null) {
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

                Authentication authentication = new UsernamePasswordAuthenticationToken(authData, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } else {
            log.debug("no token found");
        }
        filterChain.doFilter(request, response);
    }
}
