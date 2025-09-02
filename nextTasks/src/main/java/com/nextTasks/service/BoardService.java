package com.nextTasks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextTasks.exception.BoardExistentException;
import com.nextTasks.exception.BoardNotFoundException;
import com.nextTasks.model.Board;
import com.nextTasks.model.User;
import com.nextTasks.repository.BoardRepository;
import com.nextTasks.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BoardService {
    
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Board> getBoardsByOwnerId(Long id){
        return boardRepository.findByOwnerId(id);
    }

    public Board getBoardById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    public Board createBoard(Board board){
        if (boardRepository.findById(board.getId()).isPresent()) {
            throw new BoardExistentException("Board already exists with id: " + board.getId());
        }

        if(board.getOwner() != null && board.getOwner().getId() != null){
            // Lazy loading - Cargar el owner completo desde la base de datos
           User owner = userRepository.findById(board.getOwner().getId())
                .orElseThrow(() -> new BoardNotFoundException("User not found with id: " + board.getOwner().getId()));
            board.setOwner(owner);
        }
        return boardRepository.save(board);
    }

    public Board updateBoard(Long id, Board newBoard) {
        Board existingBoard = boardRepository.findById(id)
            .orElseThrow(() -> new BoardNotFoundException("Board not found with id: " + id));

        existingBoard.setName(newBoard.getName());
        existingBoard.setDescription(newBoard.getDescription());
        // Si se desea actualizar el owner, se puede agregar lógica aquí

        return boardRepository.save(existingBoard);
    }

    public void deleteBoard(Long id) {
        if (!boardRepository.existsById(id)) {
            throw new BoardNotFoundException("Board not found with id: " + id);
        }
        boardRepository.deleteById(id);
    }

}
