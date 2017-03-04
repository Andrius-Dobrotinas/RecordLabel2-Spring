package com.andrewd.recordlabel.authentication;

import com.andrewd.recordlabel.authentication.data.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.*;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceDefaultTests {

    @InjectMocks
    UserDetailsServiceDefault svc;

    @Mock
    AccountsRepository accounts;

    static Account account;

    @BeforeClass
    public static void init() {
        account = new Account();
        account.username = "andrew";
        account.password = "pwd";
    }

    @Test
    public void mustHitTheAccountsRepositoryWithTheSuppliedName () {
        Mockito.when(accounts.findByUsername(Matchers.anyString()))
                .thenReturn(account);

        svc.loadUserByUsername(account.username);

        Mockito.verify(accounts, times(1)).findByUsername(Matchers.eq(account.username));
    }

    @Test
    public void userDataObjectMustContainDataFromAccount() {
        Mockito.when(accounts.findByUsername(Matchers.anyString()))
                .thenReturn(account);

        UserDetails result = svc.loadUserByUsername(account.username);

        Assert.assertEquals("User names must match", account.username, result.getUsername());
        Assert.assertEquals("User passwords must match", account.password, result.getPassword());
    }

    @Test
    public void userDataObjectMustContainOtherPredefinedValues() {
        Mockito.when(accounts.findByUsername(Matchers.anyString()))
                .thenReturn(account);

        UserDetails result = svc.loadUserByUsername(account.username);

        Assert.assertTrue("User must be enabled", result.isEnabled());
        Assert.assertTrue("Account must be not expired", result.isAccountNonExpired());
        Assert.assertTrue("Credentials must be not expired", result.isCredentialsNonExpired());
        Assert.assertTrue("Account must be not locked", result.isAccountNonLocked());
    }

    @Test
    public void userDataObjectMustContainOneRoleWhichIsGod() {
        Mockito.when(accounts.findByUsername(Matchers.anyString()))
                .thenReturn(account);

        UserDetails result = svc.loadUserByUsername(account.username);

        Assert.assertEquals("There must only be 1 authority", 1, result.getAuthorities().size());
        Assert.assertEquals("There must only be 1 authority", "ROLE_ADMIN", result.getAuthorities().toArray()[0].toString());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void mustThrowExceptionWhenAccountNotFound() {
        Mockito.when(accounts.findByUsername(Matchers.anyString()))
                .thenReturn(null);

        svc.loadUserByUsername(account.username);
    }
}