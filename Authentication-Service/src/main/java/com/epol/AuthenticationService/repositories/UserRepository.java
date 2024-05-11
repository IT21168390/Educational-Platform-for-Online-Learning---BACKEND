package com.epol.AuthenticationService.repositories;

import com.epol.AuthenticationService.models.User;
import com.epol.AuthenticationService.models.UserRoles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    UserDetails findByEmail(String email);

    User findFirstByEmail(String email);

    boolean existsByEmail(String email);
    User findByUserRole(UserRoles role);
    List<User> findAllByUserRole(UserRoles role);
}
