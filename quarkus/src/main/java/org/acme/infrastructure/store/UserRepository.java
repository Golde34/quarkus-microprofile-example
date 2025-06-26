package org.acme.infrastructure.store;

import java.util.List;

import org.acme.core.domain.QuarkusUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<QuarkusUser, Long> {

    List<QuarkusUser> findByName(String name);
    List<QuarkusUser> findByEmail(String email);
    void deleteById(Long id);
}
