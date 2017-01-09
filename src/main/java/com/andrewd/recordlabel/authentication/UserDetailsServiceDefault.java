package com.andrewd.recordlabel.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
class UserDetailsServiceDefault implements UserDetailsService {

    @Autowired
    AccountsRepository accountsRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.length() == 0) {
            throw new IllegalArgumentException("Username is empty");
        }

        Account account = accountsRepository.findByUsername(username);
        if(account != null) {
            return new User(account.username, account.password, true, true, true, true,
                    AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
        } else {
            throw new UsernameNotFoundException("Account with user name '" + username + "' not found");
        }
    }

}