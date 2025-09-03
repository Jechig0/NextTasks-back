package com.nextTasks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nextTasks.model.Column;


public interface ColumnRepository extends JpaRepository<Column, Long>{
    
    List<Column> findByBoardId(Long boardId);

    Column findByIdAndBoardId(Long id, Long boardId);
}
