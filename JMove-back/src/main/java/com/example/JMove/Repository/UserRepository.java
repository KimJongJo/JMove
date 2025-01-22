package com.example.JMove.Repository;

import com.example.JMove.DAO.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsById(String id);
    User findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByIdAndEmail(String id, String email);

    User findByIdAndEmail(String id, String email);
}
