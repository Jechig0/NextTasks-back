package com.nextTasks.repository;

import com.nextTasks.model.Table;
import com.nextTasks.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {

    // Buscar tablas por propietario
    List<Table> findByOwner(User owner);

    // Buscar tablas por ID del propietario
    List<Table> findByOwnerId(Long ownerId);

    // Buscar tablas por nombre (contiene texto)
    List<Table> findByNameContainingIgnoreCase(String name);

    // Buscar tablas por descripción (contiene texto)
    List<Table> findByDescriptionContainingIgnoreCase(String description);

    // Buscar tablas por código de color
    List<Table> findByColorCode(String colorCode);

    // Buscar tablas por propietario y nombre (contiene texto)
    List<Table> findByOwnerAndNameContainingIgnoreCase(User owner, String name);

    // Buscar tablas por propietario ordenadas por nombre
    List<Table> findByOwnerOrderByNameAsc(User owner);

    // Buscar tablas por propietario ordenadas por nombre descendente
    List<Table> findByOwnerOrderByNameDesc(User owner);

    // Contar tablas por propietario
    Long countByOwner(User owner);

    // Contar tablas por ID del propietario
    Long countByOwnerId(Long ownerId);

    // Buscar todas las tablas ordenadas por nombre
    List<Table> findAllByOrderByNameAsc();

    // Verificar si existe una tabla con ese nombre para un propietario específico
    boolean existsByOwnerAndName(User owner, String name);

    // Buscar tablas por propietario con query personalizada (ejemplo adicional)
    @Query("SELECT t FROM Table t WHERE t.owner = :owner AND t.name LIKE %:name%")
    List<Table> findTablesByOwnerAndNameCustom(@Param("owner") User owner, @Param("name") String name);

}
