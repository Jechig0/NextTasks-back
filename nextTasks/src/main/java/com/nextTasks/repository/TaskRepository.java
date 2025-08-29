package com.nextTasks.repository;

import com.nextTasks.model.Task;
import com.nextTasks.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    // Metodos de ejemplo
    
    // Buscar tareas por tabla
    List<Task> findByTable(Board NTable);

    // Buscar tareas por ID de tabla
    List<Task> findByTableId(Long NTableId);

    Optional<Task> findByTableAndTitle(Board table, String title);

    long countByCompletionDateNotNull();

    

}
