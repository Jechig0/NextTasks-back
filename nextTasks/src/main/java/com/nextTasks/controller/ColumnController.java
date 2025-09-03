package com.nextTasks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nextTasks.DTO.ErrorResponseDTO;
import com.nextTasks.exception.BoardNotFoundException;
import com.nextTasks.exception.ColumnNotFoundException;
import com.nextTasks.model.Column;
import com.nextTasks.service.ColumnService;

@RestController
public class ColumnController {
    
    @Autowired
    private ColumnService columnService;

    @GetMapping("/boards/{boardId}/columns")
    public ResponseEntity<?> getColumnsByBoardId(@PathVariable Long boardId){
        try{
            List<Column> columns = columnService.getColumnsByBoardId(boardId);
            return ResponseEntity.ok(columns);
        } catch (Exception e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Error fetching columns by board", e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @GetMapping("/boards/{boardId}/columns/{id}")
    public ResponseEntity<?> getColumnByIdAndBoardId(@PathVariable Long boardId, @PathVariable Long id){
        try{
            Column column = columnService.getColumnByIdAndBoardId(id, boardId);
            return ResponseEntity.ok(column);

        } catch (ColumnNotFoundException e){
            ErrorResponseDTO error = new ErrorResponseDTO("Board not found", e.getMessage());
            return ResponseEntity.status(404).body(error);
        } catch (Exception e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Error fetching column", e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @PostMapping("/boards/{boardId}/columns/new")
    public ResponseEntity<?> createColumn(@PathVariable Long boardId, @RequestBody Column column){
        try{
            Column newColumn = columnService.createColumn(boardId, column);
            return ResponseEntity.status(201).body(newColumn);
        } catch(BoardNotFoundException e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Board not found", e.getMessage());
            return ResponseEntity.status(404).body(error);
        } catch(Exception e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Error creating column", e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @PutMapping("/boards/{boardId}/columns/{id}/update")
    public ResponseEntity<?> updateColumn(@PathVariable Long boardId, @PathVariable Long id, @RequestBody Column column){
        try{
            Column updatedColumn = columnService.updateColumn(id, boardId, column);
            return ResponseEntity.ok(updatedColumn);
        } catch(ColumnNotFoundException e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Board not found", e.getMessage());
            return ResponseEntity.status(404).body(error);
        } catch(Exception e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Error updating column", e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @DeleteMapping("/boards/{boardId}/columns/{id}/delete")
    public ResponseEntity<?> deleteColumn(@PathVariable Long boardId, @PathVariable Long id){
        try{
            columnService.deleteColumn(id, boardId);
            return ResponseEntity.noContent().build();
        } catch(ColumnNotFoundException e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Board not found", e.getMessage());
            return ResponseEntity.status(404).body(error);
        } catch(Exception e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Error deleting column", e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

}
