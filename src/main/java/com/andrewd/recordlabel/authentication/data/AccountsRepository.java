package com.andrewd.recordlabel.authentication.data;

import org.springframework.data.repository.CrudRepository;

public interface AccountsRepository extends CrudRepository<Account, Integer> {
    Account findByUsername(String username);
}