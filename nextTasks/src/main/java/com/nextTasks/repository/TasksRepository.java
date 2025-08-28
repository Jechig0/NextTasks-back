package com.nextTasks.repository;

import com.nextTasks.model.Tasks;
import com.nextTasks.model.Table;
import com.nextTasks.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Long> {

    // Buscar tareas por tabla
    List<Tasks> findByTable(Table table);

    // Buscar tareas por ID de tabla
    List<Tasks> findByTableId(Long tableId);

    // Buscar tareas por estado
    List<Tasks> findByStatus(String status);

    // Buscar tareas por prioridad
    List<Tasks> findByPriority(Integer priority);

    // Buscar tareas por fecha de vencimiento
    List<Tasks> findByDueDate(LocalDate dueDate);

    // Buscar tareas vencidas
    List<Tasks> findByDueDateBeforeAndStatusNot(LocalDate date, String status);

    // Buscar tareas completadas
    List<Tasks> findByStatusAndCompletionDateIsNotNull(String status);

    // Buscar tareas por tabla y estado
    List<Tasks> findByTableAndStatus(Table table, String status);

    // Buscar tareas por tabla y prioridad
    List<Tasks> findByTableAndPriority(Table table, Integer priority);

    // Buscar tareas que contienen un tag específico
    @Query("SELECT t FROM Tasks t JOIN t.tags tag WHERE tag = :tag")
    List<Tasks> findByTagsContaining(@Param("tag") Tags tag);

    // Buscar tareas por título (contiene texto)
    List<Tasks> findByTitleContainingIgnoreCase(String title);

    // Buscar tareas por descripción (contiene texto)
    List<Tasks> findByDescriptionContainingIgnoreCase(String description);

    // Buscar tareas creadas en un rango de fechas
    List<Tasks> findByCreationDateBetween(LocalDate startDate, LocalDate endDate);

    // Buscar tareas por tabla y rango de fechas de creación
    List<Tasks> findByTableAndCreationDateBetween(Table table, LocalDate startDate, LocalDate endDate);

    // Contar tareas por estado
    Long countByStatus(String status);

    // Contar tareas por tabla
    Long countByTable(Table table);

    // Contar tareas por tabla y estado
    Long countByTableAndStatus(Table table, String status);

}
