package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.authentication.Account;
import com.andrewd.recordlabel.authentication.AuthenticationServiceDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication/")
public class AuthController {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    AuthenticationServiceDefault authService;

    @RequestMapping(value = "authenticate", method = RequestMethod.POST)
    public ResponseEntity authenticate(@RequestBody Account credentials) {
        if (credentials == null
                || credentials.username == null || credentials.username.length() == 0
                || credentials.password == null || credentials.password.length() == 0) {
            return ResponseEntity.badRequest().body("Credentials not supplied");
        }

        try {
            authService.authenticate(credentials);
        }
        catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }
}