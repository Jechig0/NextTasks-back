package com.nextTasks.repository;

import com.nextTasks.model.Tags;
import com.nextTasks.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagsRepository extends JpaRepository<Tags, Long> {

    // Buscar tags por propietario
    List<Tags> findByOwner(User owner);

    // Buscar tags por ID del propietario
    List<Tags> findByOwnerId(Long ownerId);

    // Buscar tag por nombre
    Optional<Tags> findByName(String name);

}
