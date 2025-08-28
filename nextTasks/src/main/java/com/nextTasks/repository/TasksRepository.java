package com.nextTasks.repository;

import com.nextTasks.model.Tasks;
import com.nextTasks.model.NTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Long> {


    // Metodos de ejemplo
    
    // Buscar tareas por tabla
    List<Tasks> findByTable(NTable NTable);

    // Buscar tareas por ID de tabla
    List<Tasks> findByTableId(Long NTableId);

    

}
