package com.andrewd.recordlabel.authentication;

import com.andrewd.recordlabel.web.api.AuthController;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.*;
import java.util.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceDefaultTests {

    @InjectMocks
    AuthenticationServiceDefault svc;

    @Mock
    UserDetailsService userDetailsSvc;

    @Mock
    AuthenticationManager authManager;

    @Test
    public void authenticate_MustLoadUserByNameFromUserServiceWithUsingTheSuppliedUserName() {
        Account creds = new Account();
        creds.username = "asd";
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        UserDetails userDetails = new User("asd", "pwd", authorities);

        Mockito.when(userDetailsSvc.loadUserByUsername(Matchers.anyString()))
                .thenReturn(userDetails);

        svc.authenticate(creds);

        Mockito.verify(userDetailsSvc, times(1)).loadUserByUsername(Matchers.eq(creds.username));
    }

    // Not testing the rest of the functionality for now
}