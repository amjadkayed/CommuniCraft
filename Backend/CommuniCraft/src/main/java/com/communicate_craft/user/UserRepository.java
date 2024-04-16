package com.communicate_craft.user;

import com.communicate_craft.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByPhoneNumber(String phoneNumber);

    List<User> findByRole(Role role);
}
