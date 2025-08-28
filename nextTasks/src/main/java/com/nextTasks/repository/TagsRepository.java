package com.nextTasks.repository;

import com.nextTasks.model.Tags;
import com.nextTasks.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    // Buscar tags por nombre (contiene texto)
    List<Tags> findByNameContainingIgnoreCase(String name);

    // Buscar tags por código de color
    List<Tags> findByColorCode(String colorCode);

    // Buscar tags por propietario y nombre (contiene texto)
    List<Tags> findByOwnerAndNameContainingIgnoreCase(User owner, String name);

    // Buscar tags por propietario y código de color
    List<Tags> findByOwnerAndColorCode(User owner, String colorCode);

    // Buscar tags por propietario ordenadas por nombre
    List<Tags> findByOwnerOrderByNameAsc(User owner);

    // Buscar tags por propietario ordenadas por nombre descendente
    List<Tags> findByOwnerOrderByNameDesc(User owner);

    // Verificar si existe un tag con ese nombre para un propietario específico
    boolean existsByOwnerAndName(User owner, String name);

    // Contar tags por propietario
    Long countByOwner(User owner);

    // Contar tags por ID del propietario
    Long countByOwnerId(Long ownerId);

    // Buscar todas las tags ordenadas por nombre
    List<Tags> findAllByOrderByNameAsc();

    // Buscar tags que no pertenecen a un propietario específico (tags públicos si los hay)
    List<Tags> findByOwnerNot(User owner);

    // Buscar tags por nombre exacto y propietario
    Optional<Tags> findByNameAndOwner(String name, User owner);

    // Query personalizada para buscar tags más utilizados
    @Query("SELECT t FROM Tags t JOIN t.tasks task GROUP BY t ORDER BY COUNT(task) DESC")
    List<Tags> findMostUsedTags();

    // Query personalizada para buscar tags por propietario que se usan en tareas
    @Query("SELECT DISTINCT t FROM Tags t JOIN t.tasks task WHERE t.owner = :owner")
    List<Tags> findUsedTagsByOwner(@Param("owner") User owner);

}
