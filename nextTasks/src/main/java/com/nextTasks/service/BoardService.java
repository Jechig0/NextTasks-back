package com.nextTasks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextTasks.exception.BoardNotFoundException;
import com.nextTasks.model.Board;
import com.nextTasks.model.Column;
import com.nextTasks.model.Task;
import com.nextTasks.model.User;
import com.nextTasks.repository.BoardRepository;
import com.nextTasks.repository.ColumnRepository;
import com.nextTasks.repository.TaskRepository;
import com.nextTasks.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BoardService {
    
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ColumnRepository columnRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Board> getBoardsByOwnerId(Long id){
        return boardRepository.findByOwnerIdAndActive(id, true);
    }

    public Board getBoardById(Long id) {
        return boardRepository.findByIdAndActive(id, true).orElseThrow(() -> new BoardNotFoundException("Board not found with id: " + id));
    }

    public Board createBoard(Board board){
        

        if(board.getOwner() != null && board.getOwner().getId() != null){
            // Lazy loading - Cargar el owner completo desde la base de datos
           User owner = userRepository.findById(board.getOwner().getId())
                .orElseThrow(() -> new BoardNotFoundException("User not found with id: " + board.getOwner().getId()));
            board.setOwner(owner);
        }
        return boardRepository.save(board);
    }

    public Board updateBoard(Long id, Board newBoard) {
        Board existingBoard = getBoardById(id);

        existingBoard.setName(newBoard.getName());
        existingBoard.setDescription(newBoard.getDescription());
        // Si se desea actualizar el owner, se puede agregar lógica aquí

        return boardRepository.save(existingBoard);
    }

    public void deleteBoard(Long id) {
        Board existingBoard = getBoardById(id);
        existingBoard.setActive(false);
        boardRepository.save(existingBoard);

        List<Column> columns = columnRepository.findByBoardId(id);

        for(Column column : columns){
            List<Task> tasks = taskRepository.findByColumnId(column.getId());
            taskRepository.deleteAll(tasks);
        }

        columnRepository.deleteAll(columns);
    }

}
