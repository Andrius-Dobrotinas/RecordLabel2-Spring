package com.andrewd.recordlabel.authentication;

import org.springframework.security.core.AuthenticationException;

public interface AuthenticationService {
    void authenticate(Account credentials) throws AuthenticationException;
}