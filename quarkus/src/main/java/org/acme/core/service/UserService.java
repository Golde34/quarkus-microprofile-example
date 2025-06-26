package org.acme.core.service;

import java.util.List;
import java.util.Optional;

import org.acme.core.domain.QuarkusUser;
import org.acme.infrastructure.store.UserRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class UserService {

    @Inject
    UserRepository userRepository;
     
    public String getUserInfo() {
        return "QuarkusUser information";
    }

    public Iterable<QuarkusUser> findAll() {
        return userRepository.findAll(); 
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }

    public QuarkusUser create(String name, String email) {
        log.info("Creating user with name: {} and email: {}", name, email); 
        QuarkusUser user = userRepository.save(QuarkusUser.builder()
                .name(name)
                .email(email)
                .build());
        log.info("User created with ID: {}", user.getId());
        return user;
    }

    public Optional<QuarkusUser> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<QuarkusUser> findByEmail(String email) {
        List<QuarkusUser> users = userRepository.findByEmail(email);
        log.info("Found {} users with email: {}", users.get(0).getName(), email);
        return users;
    }
}
