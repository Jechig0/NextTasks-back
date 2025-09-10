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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nextTasks.service.BoardService;
import com.nextTasks.DTO.ErrorResponseDTO;
import com.nextTasks.exception.BoardExistentException;
import com.nextTasks.exception.BoardNotFoundException;
import com.nextTasks.model.Board;

@RestController
@RequestMapping("/boards")
public class BoardController {
    
    @Autowired
    private BoardService boardService;

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<?> getBoardsByOwnerId(@PathVariable Long ownerId){
        try{
            List<Board> boards = boardService.getBoardsByOwnerId(ownerId);
            return ResponseEntity.ok(boards);
        } catch (Exception e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Error fetching boards by owner", e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBoardById(@PathVariable Long id){
        try{
            Board board = boardService.getBoardById(id);
            return ResponseEntity.ok(board);

        } catch (BoardNotFoundException e){
            ErrorResponseDTO error = new ErrorResponseDTO("Board not found", e.getMessage());
            return ResponseEntity.status(404).body(error);
        } catch (Exception e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Error fetching board", e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @PostMapping("/new")
    public ResponseEntity<?> createBoard(@RequestBody Board board){
        try{
            Board newBoard = boardService.createBoard(board);
            return ResponseEntity.status(201).body(newBoard);
        } catch(BoardExistentException e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Board already exists", e.getMessage());
            return ResponseEntity.status(409).body(error);
        } catch (Exception e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Error creating board", e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBoard(@PathVariable Long id, @RequestBody Board board){
        try{
            Board updatedBoard = boardService.updateBoard(id, board);
            return ResponseEntity.ok(updatedBoard);
        } catch(BoardNotFoundException e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Board not found", e.getMessage());
            return ResponseEntity.status(404).body(error);
        } catch (Exception e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Error updating board", e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @PutMapping("/inactive/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long id){
        try{
            boardService.deleteBoard(id);
            return ResponseEntity.noContent().build();
        } catch(BoardNotFoundException e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Board not found", e.getMessage());
            return ResponseEntity.status(404).body(error);
        } catch (Exception e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Error deleting board", e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
