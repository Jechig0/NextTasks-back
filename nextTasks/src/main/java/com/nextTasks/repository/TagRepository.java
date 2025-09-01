package com.nextTasks.repository;

import com.nextTasks.model.Tag;
import com.nextTasks.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    // Buscar tags por propietario
    List<Tag> findByOwner(User owner);

    // Buscar tags por ID del propietario
    List<Tag> findByOwnerId(Long ownerId);

    // Buscar tag por nombre
    Optional<Tag> findByName(String name);

}
