package com.nextTasks.repository;

import com.nextTasks.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Buscar usuario por username
    Optional<User> findByUsername(String username);

    // Buscar usuario por email
    Optional<User> findByEmail(String email);

    // Buscar usuario por username o email
    Optional<User> findByUsernameOrEmail(String username, String email);

    // Buscar usuarios por nombre completo (contiene texto)
    List<User> findByFullNameContainingIgnoreCase(String fullName);

    // Verificar si existe un usuario con ese username
    boolean existsByUsername(String username);

    // Verificar si existe un usuario con ese email
    boolean existsByEmail(String email);

    // Buscar usuarios por username que contenga texto
    List<User> findByUsernameContainingIgnoreCase(String username);

    // Buscar usuarios ordenados por nombre completo
    List<User> findAllByOrderByFullNameAsc();

    // Buscar usuarios ordenados por username
    List<User> findAllByOrderByUsernameAsc();

}
