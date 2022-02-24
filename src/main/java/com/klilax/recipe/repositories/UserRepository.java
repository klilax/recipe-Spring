package com.klilax.recipe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.klilax.recipe.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    boolean existsByUsername(User user);
    boolean existsUserByEmail(String username);
//    Optional<User> findByEmail(String username);
    User findUserByEmail(String username);
}
