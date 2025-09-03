package com.nextTasks.repository;

import com.nextTasks.model.Task;
import com.nextTasks.model.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    // Metodos de ejemplo
    
    // Buscar tareas por tabla
    List<Task> findByColumn(Column column);

    // Buscar tareas por ID de tabla
    List<Task> findByColumnId(Long columnId);

    long countByCompletionDateNotNull();

    Optional<Task> findByColumnAndTitle(Column column, String title);

    Optional<Task> findByColumnIdAndTitle(Long id, String title);

    

}
