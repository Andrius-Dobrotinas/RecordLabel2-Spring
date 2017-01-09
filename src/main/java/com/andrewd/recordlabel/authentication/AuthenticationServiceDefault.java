package com.andrewd.recordlabel.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceDefault implements AuthenticationService {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authManager;

    @Override
    public void authenticate(Account credentials) throws AuthenticationException
    {
        if (credentials == null){
            throw new IllegalArgumentException("Credentials is null");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(credentials.username);

        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails,
                credentials.password, userDetails.getAuthorities());

        Authentication authentication = authManager.authenticate(authToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}