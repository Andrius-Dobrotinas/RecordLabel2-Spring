package com.andrewd.recordlabel.authentication;

import org.springframework.data.repository.CrudRepository;

public interface AccountsRepository extends CrudRepository<Account, Integer> {
    Account findByUsername(String username);
}