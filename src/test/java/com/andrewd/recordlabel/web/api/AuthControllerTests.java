package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.web.model.Credentials;
import com.andrewd.recordlabel.web.cookie.CookieGenerator;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.*;
import org.springframework.security.core.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.util.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class AuthControllerTests {

    @InjectMocks
    AuthController ctrl;

    @Mock
    HttpServletRequest request;

    @Mock
    CookieGenerator cookieGenerator;

    Credentials creds;

    @Before
    public void init() {
        creds = new Credentials();
        creds.username = "asd";
        creds.password = "pwd";
    }

    @Test
    public void authenticate_MustCallLoginWithTheSuppliedCredentials() throws ServletException {
        ctrl.authenticate(creds, request);

        Mockito.verify(request, times(1)).login(Matchers.eq(creds.username), Matchers.eq(creds.password));
    }

    @Test
    public void authenticate_MustReturnOKResponseOnSuccess() {
        ResponseEntity response = ctrl.authenticate(creds, request);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void authenticate_MustReturnUnauthorizedResponseOnAuthFailure() throws ServletException {
        Mockito.doThrow(new ServletException(new MockAuthExceptionException()))
                .when(request).login(Matchers.anyString(), Matchers.anyString());

        ResponseEntity response = ctrl.authenticate(creds, request);

        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void authenticate_MustReturnInternalServerErrorResponseOnOtherException() throws ServletException {
        Mockito.doThrow(new ServletException(new MissingFormatArgumentException("")))
                .when(request).login(Matchers.anyString(), Matchers.anyString());

        ResponseEntity response = ctrl.authenticate(creds, request);

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void authenticate_MustReturnReturnBadRequestWhenNoCredentialsSupplied() {
        ResponseEntity response = ctrl.authenticate(null, request);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void authenticate_MustReturnReturnBadRequestWhenMissingUsername() {
        creds = new Credentials();

        ResponseEntity response = ctrl.authenticate(creds, request);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void authenticate_MustReturnReturnBadRequestWhenUsernameIsEmpty() {
        creds = new Credentials();
        creds.username = "";

        ResponseEntity response = ctrl.authenticate(creds, request);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void authenticate_MustReturnReturnBadRequestWhenMissingPassword() {
        creds = new Credentials();
        creds.username = "asd";

        ResponseEntity response = ctrl.authenticate(creds, request);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void authenticate_MustReturnReturnBadRequestWhenPasswordIsEmpty() {
        creds = new Credentials();
        creds.username = "asd";
        creds.password = "";

        ResponseEntity response = ctrl.authenticate(creds, request);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    public void endSession_MustCallLogout() throws ServletException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        ctrl.endSession(request, response);

        Mockito.verify(request, times(1)).logout();
    }

    @Test
    public void endSession_MustCallCookieGeneatorSetCookieName() throws ServletException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        ctrl.endSession(request, response);

        Mockito.verify(cookieGenerator, times(1)).setCookieName(Matchers.eq(CookieGenerator.JSESSIONID));
    }

    @Test
    public void endSession_MustRemoveCookieFromResponse() throws ServletException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        ctrl.endSession(request, response);

        Mockito.verify(cookieGenerator, times(1)).removeCookie(Matchers.eq(response));
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