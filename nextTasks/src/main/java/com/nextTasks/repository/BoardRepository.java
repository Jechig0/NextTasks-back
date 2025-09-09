package com.nextTasks.repository;

import com.nextTasks.model.Board;
import com.nextTasks.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    // Buscar tablas por propietario
    List<Board> findByOwner(User owner);

    // Buscar tablas por ID del propietario
    List<Board> findByOwnerId(Long ownerId);

    // Buscar tablas por nombre (contiene texto)
    List<Board> findByNameContainingIgnoreCase(String name);

    List<Board> findByOwnerIdAndActive(Long id, Boolean active);

    Optional<Board> findByIdAndActive(Long id, Boolean active);


}
