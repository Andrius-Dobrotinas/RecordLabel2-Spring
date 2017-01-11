package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.web.cookie.CookieGenerator;
import com.andrewd.recordlabel.web.model.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

@RestController
@RequestMapping("/api/authentication/")
public class AuthController {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    CookieGenerator cookieGenerator;

    @RequestMapping(value = "authenticate", method = RequestMethod.POST)
    public ResponseEntity authenticate(@RequestBody Credentials credentials, HttpServletRequest request) {
        if (credentials == null
                || credentials.username == null || credentials.username.length() == 0
                || credentials.password == null || credentials.password.length() == 0) {
            return ResponseEntity.badRequest().build();
        }

        try {
            // TODO: implement password hashing
            request.login(credentials.username, credentials.password);
        }
        catch (ServletException e) {
            HttpStatus status;
            String message;
            if (e.getCause() instanceof AuthenticationException) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
            return ResponseEntity.status(status).build();
        }

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "endsession", method = RequestMethod.POST)
    public void endSession(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        request.logout();

        cookieGenerator.setCookieName(CookieGenerator.JSESSIONID);
        cookieGenerator.removeCookie(response);
    }
}