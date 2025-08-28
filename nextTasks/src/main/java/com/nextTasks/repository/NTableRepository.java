package com.nextTasks.repository;

import com.nextTasks.model.NTable;
import com.nextTasks.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NTableRepository extends JpaRepository<NTable, Long> {

    // Buscar tablas por propietario
    List<NTable> findByOwner(User owner);

    // Buscar tablas por ID del propietario
    List<NTable> findByOwnerId(Long ownerId);

    // Buscar tablas por nombre (contiene texto)
    List<NTable> findByNameContainingIgnoreCase(String name);


}
