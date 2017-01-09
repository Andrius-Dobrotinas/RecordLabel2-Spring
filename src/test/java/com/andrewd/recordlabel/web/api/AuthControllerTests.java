package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.authentication.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.*;
import org.springframework.security.core.AuthenticationException;

import java.util.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class AuthControllerTests {

    @InjectMocks
    AuthController ctrl;

    @Mock
    AuthenticationServiceDefault authService;

    Account creds;

    @Before
    public void init() {
        creds = new Account();
        creds.username = "asd";
        creds.password = "pwd";
    }

    @Test
    public void authenticate_MustCallAuthenticateWithTheSuppliedCredentials() {
        ctrl.authenticate(creds);

        Mockito.verify(authService, times(1)).authenticate(Matchers.eq(creds));
    }

    @Test
    public void authenticate_MustReturnOKResponseOnSuccess() {
        ResponseEntity response = ctrl.authenticate(creds);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void authenticate_MustReturnUnauthorizedResponseOnAuthFailure() {
        Mockito.doThrow(MockAuthExceptionException.class)
                .when(authService).authenticate(Matchers.any());

        ResponseEntity response = ctrl.authenticate(creds);

        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void authenticate_ResponseMustIncludeOriginalExceptionMessageOnAuthFailure() {
        String message = "Gotcha!";

        Mockito.doThrow(new MockAuthExceptionException(message))
                .when(authService).authenticate(Matchers.any());

        ResponseEntity response = ctrl.authenticate(creds);

        Assert.assertEquals(message, response.getBody());
    }

    @Test
    public void authenticate_MustReturnReturnBadRequestWhenNoCredentialsSupplied() {
        ResponseEntity response = ctrl.authenticate(null);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void authenticate_MustReturnReturnBadRequestWhenMissingUsername() {
        creds = new Account();

        ResponseEntity response = ctrl.authenticate(creds);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void authenticate_MustReturnReturnBadRequestWhenUsernameIsEmpty() {
        creds = new Account();
        creds.username = "";

        ResponseEntity response = ctrl.authenticate(creds);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void authenticate_MustReturnReturnBadRequestWhenMissingPassword() {
        creds = new Account();
        creds.username = "asd";

        ResponseEntity response = ctrl.authenticate(creds);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void authenticate_MustReturnReturnBadRequestWhenPasswordIsEmpty() {
        creds = new Account();
        creds.username = "asd";
        creds.password = "";

        ResponseEntity response = ctrl.authenticate(creds);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    class MockAuthExceptionException extends AuthenticationException {
        MockAuthExceptionException() {
            super("");
        }
        MockAuthExceptionException(String msg) {
            super(msg);
        }
    }
}
